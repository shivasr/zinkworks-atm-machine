package com.app.zinkworks.atm.services;

import com.app.zinkworks.atm.dto.Account;
import com.app.zinkworks.atm.dto.AccountBalance;
import com.app.zinkworks.atm.dto.TransactionDetails;
import com.app.zinkworks.atm.exceptions.ATMApplicationException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class AccountsServiceImpl implements AccountsService {

    public static final String ACCOUNTS_SERVICE_URI = "http://localhost:3100";
    final
    RestTemplate restTemplate;

    public AccountsServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public AccountBalance getAccountBalance(String accountNumber, String pin) throws ATMApplicationException {
        return null;
    }

    @Override
    public TransactionDetails withdrawAmount(String accountNumber, String pin, BigDecimal amount) throws ATMApplicationException {
        HttpEntity<Account> request = new HttpEntity<>(Account.builder()
                .accountNumber(accountNumber)
                .pin(pin)
                .build()) ;
        ResponseEntity<TransactionDetails> responseEntity = null;

        try {
            responseEntity = restTemplate.postForEntity(ACCOUNTS_SERVICE_URI + "/api/v1/withdraw/" + amount.intValue(), request, TransactionDetails.class);

            TransactionDetails transactionDetails = responseEntity.getBody();
            if(!transactionDetails.isSuccess()) {
                throw new ATMApplicationException(transactionDetails.getMessage());
            }
        } catch (RestClientException rcex) {
            throw new ATMApplicationException("Unidentified issue found. Error: " + rcex.getMessage());
        }

        return responseEntity.getBody();
    }

    @Override
    public AccountBalance checkBalance(String accountNumber, String pin) throws ATMApplicationException {
        HttpEntity<Account> request = new HttpEntity<>(Account.builder()
                .accountNumber(accountNumber)
                .pin(pin)
                .build()) ;

        ResponseEntity<AccountBalance> responseEntity = null;

        try {
            responseEntity = restTemplate.postForEntity(ACCOUNTS_SERVICE_URI + "/api/v1/balance/",
                                                                                    request, AccountBalance.class);
        } catch (RestClientException RCEx) {
            throw new ATMApplicationException(RCEx.getMessage());
        }

        return responseEntity.getBody();
    }
}
