package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.service.AccountsService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration

public class AccountsControllerTest {

    private final String SUCCESS = "Success";
    private final String IS_ERROR = "Error -> ";
    private MockMvc mockMvc;

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void prepareMockMvc() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

        // Reset the existing accounts before each test.
        accountsService.getAccountsRepository().clearAccounts();
    }

    @Test
    public void createAccount() throws Exception {
        this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"Id-123\",\"balance\":1000}")).andExpect(status().isCreated());

        Account account = accountsService.getAccount("Id-123");
        assertThat(account.getAccountId()).isEqualTo("Id-123");
        assertThat(account.getBalance()).isEqualByComparingTo("1000");
    }

    @Test
    public void createDuplicateAccount() throws Exception {

        this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"Id-123\",\"balance\":1000}")).andExpect(status().isCreated());

        this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"Id-123\",\"balance\":1000}")).andExpect(status().isBadRequest());

    }

    @Test
    public void createAccountNoAccountId() throws Exception {
        this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
                .content("{\"balance\":1000}")).andExpect(status().isBadRequest());
    }

    @Test
    public void createAccountNoBalance() throws Exception {
        this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"Id-123\"}")).andExpect(status().isBadRequest());
    }

    @Test
    public void createAccountNoBody() throws Exception {
        this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAccountNegativeBalance() throws Exception {
        this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"Id-123\",\"balance\":-1000}")).andExpect(status().isBadRequest());
    }

    @Test
    public void createAccountEmptyAccountId() throws Exception {
        this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"\",\"balance\":1000}")).andExpect(status().isBadRequest());
    }

    @Test
    public void getAccount() throws Exception {
        String uniqueAccountId = "Id-" + System.currentTimeMillis();
        Account account = new Account(uniqueAccountId, new BigDecimal("123.45"));
        this.accountsService.createAccount(account);
        this.mockMvc.perform(get("/v1/accounts/" + uniqueAccountId))
                .andExpect(status().isOk())
                .andExpect(
                        content().string("{\"accountId\":\"" + uniqueAccountId + "\",\"balance\":123.45}"));
    }

    private LinkedMultiValueMap<String, String> buildRequestParams(String accountFromId, String accountToId, String amount) {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("accountFromId", accountFromId);
        requestParams.add("accountToId", accountToId);
        requestParams.add("amount", amount);
        return requestParams;
    }

    private String createTestAccount(BigDecimal initBalance) {
        String newAccountId = "Id-" + UUID.randomUUID();
        Account newAccount = new Account(newAccountId, initBalance);
        this.accountsService.createAccount(newAccount);
        return newAccountId;
    }

    private void addTaskToSet(Set<Callable<String>> callables, String accountFromId, String accountToId, String amount) {
        callables.add(() -> {
            try {
                this.mockMvc.
                        perform(post("/v1/accounts/transfer").
                                params(buildRequestParams(accountFromId, accountToId, amount))).
                        andExpect(status().isCreated());
                return SUCCESS;
            } catch (Exception e) {
                return IS_ERROR + e.getMessage();
            }
        });
    }

    @Test
    public void transferMoney() throws Exception {
        String amount = "500.57";
        String accountFromId = createTestAccount(new BigDecimal("1000.57"));
        String accountToId = createTestAccount(new BigDecimal("1.33"));
        this.mockMvc.perform(post("/v1/accounts/transfer").
                params(buildRequestParams(accountFromId, accountToId, amount))).
                andExpect(status().isCreated());
        assertThat(this.accountsService.getAccount(accountFromId).getBalance()).isEqualTo(new BigDecimal("500.00"));
        assertThat(this.accountsService.getAccount(accountToId).getBalance()).isEqualTo(new BigDecimal("501.90"));
    }

    @Test
    public void multipleTransferMoney() throws Exception {
        final String account1Id = createTestAccount(new BigDecimal("100.00"));
        final String account2Id = createTestAccount(new BigDecimal("20.00"));
        final String account3Id = createTestAccount(new BigDecimal("10.00"));
        final String account4Id = createTestAccount(new BigDecimal("100.00"));
        final String account5Id = createTestAccount(new BigDecimal("10.00"));
        final String amount = "10";
        ExecutorService executor = Executors.newFixedThreadPool(15);
        Set<Callable<String>> callables = new HashSet<>();

        for (int i = 1; i < 3; i++) {
            addTaskToSet(callables, account1Id, account2Id, amount);
        }
        for (int i = 1; i < 3; i++) {
            addTaskToSet(callables, account2Id, account3Id, amount);
        }
        addTaskToSet(callables, account3Id, account1Id, amount);
        for (int i = 0; i < 10; i++) {
            addTaskToSet(callables, account4Id, account5Id, amount);
        }

        List<Future<String>> results = executor.invokeAll(callables);

        for (Future<String> result : results) {
            assertThat(result.get()).isEqualTo(SUCCESS);
        }
        executor.shutdown();
        assertThat(this.accountsService.getAccount(account1Id).getBalance()).isEqualTo(new BigDecimal("90.00"));
        assertThat(this.accountsService.getAccount(account2Id).getBalance()).isEqualTo(new BigDecimal("20.00"));
        assertThat(this.accountsService.getAccount(account3Id).getBalance()).isEqualTo(new BigDecimal("20.00"));
        assertThat(this.accountsService.getAccount(account4Id).getBalance()).isEqualTo(new BigDecimal("0.00"));
        assertThat(this.accountsService.getAccount(account5Id).getBalance()).isEqualTo(new BigDecimal("110.00"));
    }

    @Test
    public void checkDeadlock() throws Exception {
        final String account1Id = createTestAccount(new BigDecimal("10000.00"));
        final String account2Id = createTestAccount(new BigDecimal("10000.00"));
        final String amount = "1";
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        Set<Callable<String>> callables = new HashSet<>();

        for (int i = 0; i < 5000; i++) {
            addTaskToSet(callables, account1Id, account2Id, amount);
        }
        for (int i = 0; i < 5000; i++) {
            addTaskToSet(callables, account2Id, account1Id, amount);
        }
        List<Future<String>> results = executor.invokeAll(callables);

        for (Future<String> result : results) {
            assertThat(result.get()).isEqualTo(SUCCESS);
        }
        executor.shutdown();
        assertThat(this.accountsService.getAccount(account1Id).getBalance()).isEqualTo(this.accountsService.getAccount(account2Id).getBalance());
    }

    @Test
    public void transferMoneyToNotExistsAccount() throws Exception {
        String amount = "500.57";
        String accountFromId = createTestAccount(new BigDecimal("1000.57"));
        String accountToId = "Id-NotExists";
        this.mockMvc.perform(post("/v1/accounts/transfer").params(buildRequestParams(accountFromId, accountToId, amount))).
                andExpect(status().isBadRequest()).andExpect(
                content().string("Account id " + accountToId + " is not exists!"));
    }

    @Test
    public void transferMoneyFromNotExistsAccount() throws Exception {
        String accountFromId = "Id-NotExists";
        String accountToId = createTestAccount(new BigDecimal("1000.57"));
        String amount = "500.57";
        this.mockMvc.perform(post("/v1/accounts/transfer").params(buildRequestParams(accountFromId, accountToId, amount))).
                andExpect(status().isBadRequest()).andExpect(
                content().string("Account id " + accountFromId + " is not exists!"));
        assertThat(this.accountsService.getAccount(accountToId).getBalance()).isEqualTo(new BigDecimal("1000.57"));
    }

    @Test
    public void transferMoneyNegativeBalance() throws Exception {
        String accountFromId = createTestAccount(new BigDecimal("1000.57"));
        String accountToId = createTestAccount(new BigDecimal("1.33"));
        String amount = "1500.57";
        this.mockMvc.perform(post("/v1/accounts/transfer").params(buildRequestParams(accountFromId, accountToId, amount))).
                andExpect(status().isBadRequest())
                .andExpect(content().string("Not enough money on the Account " + accountFromId +
                        " to transfer to the amount = " + amount));
        assertThat(accountsService.getAccount(accountFromId).getBalance()).isEqualTo(new BigDecimal("1000.57"));
        assertThat(accountsService.getAccount(accountToId).getBalance()).isEqualTo(new BigDecimal("1.33"));
    }
}