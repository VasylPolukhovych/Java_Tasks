package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.NegativeAmountException;
import com.db.awmd.challenge.exception.NegativeBalanceException;
import com.db.awmd.challenge.repository.AccountsRepository;
import com.db.awmd.challenge.service.AccountsService;

import java.math.BigDecimal;
import java.util.UUID;

import com.db.awmd.challenge.service.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsServiceTest {

    private final String NOTIFICATION_TYPE_TO = "to";

    private final String NOTIFICATION_TYPE_FROM = "from";

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private AccountsRepository accountsRepository;

    private NotificationService notificationServiceMock = mock(NotificationService.class);

    @Test
    public void addAccount() throws Exception {
        Account account = new Account("Id-123");
        account.setBalance(new BigDecimal(1000));
        this.accountsService.createAccount(account);

        assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(account);
    }

    @Test
    public void addAccount_failsOnDuplicateId() throws Exception {
        String uniqueId = "Id-" + System.currentTimeMillis();
        Account account = new Account(uniqueId);
        this.accountsService.createAccount(account);

        try {
            this.accountsService.createAccount(account);
            fail("Should have failed when adding duplicate account");
        } catch (DuplicateAccountIdException ex) {
            assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
        }

    }

    private String createTestAccount(AccountsService accountsService, BigDecimal initBalance) {
        String newAccountId = "Id-" + UUID.randomUUID();
        Account newAccount = new Account(newAccountId, initBalance);
        accountsService.createAccount(newAccount);
        return newAccountId;
    }

    private void checkNotification(int count, String amount, String firstAccountId, String secondAccountId, String notificationType) {
        verify(this.notificationServiceMock, times(count)).notifyAboutTransfer(this.accountsService.getAccount(firstAccountId),
                "Amount = "
                        + amount
                        + " has been transferred " + notificationType + " account with Id = "
                        + secondAccountId);
    }

    @Test
    public void transferMoney() throws Exception {
        AccountsService accntsService = new AccountsService(this.accountsRepository, this.notificationServiceMock);
        String amount = "500.57";
        String accountFromId = createTestAccount(accntsService, new BigDecimal("1000.57"));
        String accountToId = createTestAccount(accntsService, new BigDecimal("1.33"));
        accntsService.transferMoney(accountFromId, accountToId, new BigDecimal(amount));
        checkNotification(1, amount, accountFromId, accountToId, NOTIFICATION_TYPE_TO);
        checkNotification(1, amount, accountToId, accountFromId, NOTIFICATION_TYPE_FROM);
        assertThat(accntsService.getAccount(accountFromId).getBalance()).isEqualTo(new BigDecimal("500.00"));
        assertThat(accntsService.getAccount(accountToId).getBalance()).isEqualTo(new BigDecimal("501.90"));
    }

    @Test
    public void transferMoneyNegativeBalance() throws Exception {
        AccountsService accntsService = new AccountsService(this.accountsRepository, this.notificationServiceMock);
        String amount = "1500.57";
        String accountFromId = createTestAccount(accntsService, new BigDecimal("1000.57"));
        String accountToId = createTestAccount(accntsService, new BigDecimal("1.33"));
        try {
            accntsService.transferMoney(accountFromId, accountToId, new BigDecimal(amount));
            fail("Should have failed when balance become negative");
        } catch (NegativeBalanceException ex) {
            assertThat(ex.getMessage()).isEqualTo("Not enough money on the Account " + accountFromId +
                    " to transfer to the amount = " + amount);
        }
        verify(this.notificationServiceMock, Mockito.never()).notifyAboutTransfer(Mockito.any(), Mockito.any());
        assertThat(accntsService.getAccount(accountFromId).getBalance()).isEqualTo(new BigDecimal("1000.57"));
        assertThat(accntsService.getAccount(accountToId).getBalance()).isEqualTo(new BigDecimal("1.33"));
    }
}
