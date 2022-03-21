package com.app.zinkworks.atm.services;

import com.app.zinkworks.atm.domain.CurrencyEntity;
import com.app.zinkworks.atm.dto.Currency;
import com.app.zinkworks.atm.exceptions.ATMApplicationException;
import com.app.zinkworks.atm.repositories.CurrencyRepository;
import com.app.zinkworks.atm.utils.CurrencyMapper;
import com.app.zinkworks.atm.utils.VendingUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Teller Service Implementation of the teller service to calculate the number of currencies based on the availibility
 * in the ATM.
 */
@Service
public class TellerServiceImpl implements TellerService {

    private final CurrencyRepository currencyRepository;

    public TellerServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * Calculate the number of currency notes to be dispensed
     * @param amount Amount for which currency note
     * @return List of <code>Currency</code> beans containing currency note and number of notes to be dispensed
     * @throws ATMApplicationException if there are no sufficient notes to match the amount requested.
     */
    @Override
    public List<Currency> findCurrencies(int amount) throws ATMApplicationException {

        VendingUtil vendingUtil = new VendingUtil();

        List<CurrencyEntity> allRecords = currencyRepository.findAll();
        Map<Integer, Currency> mapOfNotesToCurrency
                = allRecords.stream()
                .map(currencyEntity -> CurrencyMapper.toCurrency(currencyEntity))
                    .collect(Collectors.toMap(key -> key.getCurrency(), value -> value));

        List<Currency> currencies = vendingUtil.calculateExactNotes(amount, mapOfNotesToCurrency);

        allRecords.forEach(currencyEntity -> {
            Currency currency = mapOfNotesToCurrency.get(currencyEntity.getCurrency());
            currencyEntity.setNumber(currency.getNumber());
            currencyRepository.save(currencyEntity);
        });

        return currencies;
    }

    @Override
    public boolean commitCurreencyDispense(int amount, List<Currency> currencies) throws ATMApplicationException {
        List<CurrencyEntity> allRecords = currencyRepository.findAll();
        Map<Integer, Currency> mapOfNotesToCurrency
                = allRecords.stream()
                .map(currencyEntity -> CurrencyMapper.toCurrency(currencyEntity))
                .collect(Collectors.toMap(key -> key.getCurrency(), value -> value));

        allRecords.forEach(currencyEntity -> {
            Currency currency = mapOfNotesToCurrency.get(currencyEntity.getCurrency());
            currencyEntity.setNumber(currency.getNumber());
            currencyRepository.save(currencyEntity);
        });
        return false;
    }

    @Override
    public List<Currency> listAllCash() {
        return currencyRepository.findAll().stream()
                .map(currencyEntity -> CurrencyMapper.toCurrency(currencyEntity))
                .collect(Collectors.toList());
    }
}
