package com.app.zinkworks.atm.services;

import com.app.zinkworks.atm.dto.Currency;
import com.app.zinkworks.atm.exceptions.ATMApplicationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Teller Service Interfacecss
 */
@Service
public interface TellerService {
    public List<Currency> findCurrencies(int amount) throws ATMApplicationException;

    public boolean commitCurreencyDispense(int amount, List<Currency> currencies) throws ATMApplicationException;

    List<Currency> listAllCash();
}
