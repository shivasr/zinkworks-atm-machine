package com.app.zinkworks.accountservice.controller;

import com.app.zinkworks.accountservice.exceptions.AccountsServiceException;
import com.app.zinkworks.accountservice.service.AccountsService;
import com.app.zinkworks.accountservice.web.models.Account;
import com.app.zinkworks.accountservice.web.models.AccountBalance;
import com.app.zinkworks.accountservice.web.models.TransactionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("/api/v1")
@RestController
public class AccountController {

    private final AccountsService accountsService;

    public AccountController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping("/balance")
    public @ResponseBody
    ResponseEntity<AccountBalance> withdrawMoney(@RequestBody Account account) {

        AccountBalance balance = null;
        try {
             if(!accountsService.validatePin(account.getAccountNumber(), account.getPin()))
                 throw new AccountsServiceException("01", "Invalid pin.");

            balance = accountsService.getAccountBalance(account.getAccountNumber());
        } catch (AccountsServiceException e) {
            return new ResponseEntity<>(AccountBalance.builder()
                    .success(false)
                    .message(e.getMessage())
                    .returnCode(e.getReturnCode())
                    .build(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(balance);
    }

    @PostMapping("/withdraw/{amount}")
    public @ResponseBody
    ResponseEntity<TransactionDetails> withdrawMoney(@RequestBody Account account,
                                                     @PathVariable("amount") String amount) {

        Long amountValue = Long.valueOf(amount);
        TransactionDetails transactionDetails = null;
        try {
            transactionDetails = accountsService.withdrawAmount(account.getAccountNumber(),
                                                                                        BigDecimal.valueOf(amountValue));
        } catch (AccountsServiceException e) {
            return new ResponseEntity<>(TransactionDetails.builder()
                    .success(false)
                    .message(e.getMessage())
                    .returnCode(e.getReturnCode())
                    .build(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(transactionDetails);
    }
}
