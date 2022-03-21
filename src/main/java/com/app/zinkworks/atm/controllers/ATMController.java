package com.app.zinkworks.atm.controllers;

import com.app.zinkworks.atm.dto.*;
import com.app.zinkworks.atm.exceptions.ATMApplicationException;
import com.app.zinkworks.atm.services.TellerService;
import com.app.zinkworks.atm.services.AccountsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller to render ATM services functionality
 */
@RequestMapping("/api/v1")
@RestController
public class ATMController {

    /**
     * Teller service to fetch currency notes
     */
    private final TellerService tellerService;


    private final AccountsService accountsService;

    /**
     * Constructor
     * @param tellerService
     * @param accountsService
     */
    public ATMController(TellerService tellerService, AccountsService accountsService) {
        this.tellerService = tellerService;
        this.accountsService = accountsService;
    }

    @GetMapping(value = "/atm/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Currency>> listAvailableCash(){

        return ResponseEntity.ok(tellerService.listAllCash());
    }

    /**
     * Withdraw money from the machine. This shall contact the account service to validate
     * @param withdrawalRequest
     * @return
     */
    @PostMapping(value="/withdraw", consumes="application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<WithdrawResponse>
                                withdrawMoney(@RequestBody WithdrawRequest withdrawalRequest) {
        List<Currency> currencies = null;

        try {
            Long amountLong = Long.valueOf(withdrawalRequest.getAmount());


            TransactionDetails details = accountsService.withdrawAmount(withdrawalRequest.getAccountNumber(),
                                                                                withdrawalRequest.getPin(),
                                                                                        BigDecimal.valueOf(amountLong));

            currencies = tellerService.findCurrencies(Integer.valueOf(details.getWithdrawAmount()));
        } catch (ATMApplicationException e) {
            WithdrawResponse withdrawResponse = WithdrawResponse.builder()
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(withdrawResponse, HttpStatus.FORBIDDEN);
        }

        WithdrawResponse withdrawResponse = WithdrawResponse.builder()
                .message("Transaction successful.")
                .notes(currencies)
                .build();
        return ResponseEntity.ok(withdrawResponse);
    }


    /**
     * Check for the balance.
     * @param account
     * @return
     */
    @PostMapping(value="/balance", consumes="application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<AccountBalance>
    checkBalance(@RequestBody Account account) {
        AccountBalance details = null;

        try {
            details = accountsService.checkBalance(account.getAccountNumber(),
                    account.getPin());
        } catch (ATMApplicationException e) {
            AccountBalance error = AccountBalance.builder()
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(details);
    }

}
