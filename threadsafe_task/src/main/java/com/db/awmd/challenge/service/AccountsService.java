package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.AccountIdIsNotExists;
import com.db.awmd.challenge.exception.NegativeAmountException;
import com.db.awmd.challenge.exception.NegativeBalanceException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountsService {

    private final String NOTIFICATION_TYPE_TO = "to";

    private final String NOTIFICATION_TYPE_FROM = "from";

    @Getter
    private final AccountsRepository accountsRepository;

    private final NotificationService notificationService;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository, NotificationService notificationService) {
        this.accountsRepository = accountsRepository;
        this.notificationService = notificationService;
    }

    public void createAccount(Account account) {
        this.accountsRepository.createAccount(account);
    }

    public Account getAccount(String accountId) {
        return this.accountsRepository.getAccount(accountId);
    }

    private void sendNotification(String amount, Account firstAccount, String secondAccountId, String notificationType) {
        this.notificationService.notifyAboutTransfer(firstAccount,
                "Amount = "
                        + amount.toString()
                        + " has been transferred " + notificationType + " account with Id = "
                        + secondAccountId);
    }

    public void transferMoney(String accountFromId, String accountToId, BigDecimal amount)
            throws NegativeAmountException, NegativeBalanceException, AccountIdIsNotExists {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) != 1) {
            throw new NegativeAmountException("Amount =" + amount + ". It must be a positive value");
        }
        Account accountFrom = getAccount(accountFromId);
        if (accountFrom == null) {
            throw new AccountIdIsNotExists(
                    "Account id " + accountFromId + " is not exists!");
        }
        Account accountTo = getAccount(accountToId);
        if (accountTo == null) {
            throw new AccountIdIsNotExists(
                    "Account id " + accountToId + " is not exists!");
        }

        Account account1;
        Account account2;
        if (accountFromId.compareTo(accountToId) < 0) {
            account1 = accountFrom;
            account2 = accountTo;
        } else {
            account1 = accountTo;
            account2 = accountFrom;
        }
        synchronized (account1) {
            synchronized (account2) {
                if (accountFrom.getBalance().compareTo(amount) == -1) {
                    throw new NegativeBalanceException("Not enough money on the Account " + accountFromId +
                            " to transfer to the amount = " + amount.toString());
                }
                accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
                accountTo.setBalance(accountTo.getBalance().add(amount));
            }
        }
        sendNotification(amount.toString(), accountFrom, accountToId, NOTIFICATION_TYPE_TO);
        sendNotification(amount.toString(), accountTo, accountFromId, NOTIFICATION_TYPE_FROM);
    }
}
