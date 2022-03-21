package com.app.zinkworks.atm.services;

import com.app.zinkworks.atm.dto.AccountBalance;
import com.app.zinkworks.atm.dto.TransactionDetails;
import com.app.zinkworks.atm.exceptions.ATMApplicationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 *
 */
@Service
public interface AccountsService {

    /**
     *
     * @param accountNumber
     * @param pin
     * @return
     * @throws ATMApplicationException
     */
    AccountBalance getAccountBalance(String accountNumber, String pin) throws ATMApplicationException; ;

    /**
     *
     * @param accountNumber
     * @param pin
     * @param amount
     * @return
     * @throws ATMApplicationException
     */
    TransactionDetails withdrawAmount(String accountNumber, String pin, BigDecimal amount)
            throws ATMApplicationException;

    AccountBalance checkBalance(String accountNumber, String pin) throws ATMApplicationException;
}
