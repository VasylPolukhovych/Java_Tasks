package com.db.awmd.challenge.web;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.AccountIdIsNotExists;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.NegativeAmountException;
import com.db.awmd.challenge.exception.NegativeBalanceException;
import com.db.awmd.challenge.service.AccountsService;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.NoClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/accounts")
@Slf4j
public class AccountsController {

    private final AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
        log.info("Creating account {}", account);

        try {
            this.accountsService.createAccount(account);
        } catch (DuplicateAccountIdException daie) {
            return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/{accountId}")
    public Account getAccount(@PathVariable String accountId) {
        log.info("Retrieving account for id {}", accountId);
        return this.accountsService.getAccount(accountId);
    }

    @PostMapping(path = "/transfer")
    public ResponseEntity<Object> transferMoney(@RequestParam(value = "accountFromId") String accountFromId,
                                                @RequestParam(value = "accountToId") String accountToId,
                                                @RequestParam(value = "amount") String amount) {
        log.info("Transferring  " + amount.toString() + " from Account Id {} to Account Id {}.", accountFromId, accountToId);

        try {

            this.accountsService.transferMoney(accountFromId, accountToId, new BigDecimal(amount));
        } catch (NegativeAmountException negativeAmountException) {
            return new ResponseEntity<>(negativeAmountException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AccountIdIsNotExists accountIdIsNotExists) {
            return new ResponseEntity<>(accountIdIsNotExists.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NegativeBalanceException negativeBalanceException) {
            return new ResponseEntity<>(negativeBalanceException.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
