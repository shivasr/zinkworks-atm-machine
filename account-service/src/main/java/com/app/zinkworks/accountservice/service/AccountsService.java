package com.app.zinkworks.accountservice.service;

import com.app.zinkworks.accountservice.exceptions.AccountsServiceException;
import com.app.zinkworks.accountservice.web.models.AccountBalance;
import com.app.zinkworks.accountservice.web.models.TransactionDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface AccountsService {

    boolean validatePin(String accountNumber, String pin) throws AccountsServiceException;

    AccountBalance getAccountBalance(String accountNumber) throws AccountsServiceException; ;

    TransactionDetails withdrawAmount(String accountNumber, BigDecimal amount)
                                                                                        throws AccountsServiceException;
}
