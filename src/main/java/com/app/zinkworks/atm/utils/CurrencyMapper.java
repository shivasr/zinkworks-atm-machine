package com.app.zinkworks.atm.utils;

import com.app.zinkworks.atm.domain.CurrencyEntity;
import com.app.zinkworks.atm.dto.Currency;

/**
 *
 */
public class CurrencyMapper {
    public static Currency toCurrency(CurrencyEntity currencyEntity) {
        return Currency.builder()
                .number(currencyEntity.getNumber())
                .currency(currencyEntity.getCurrency())
                .build();
    }
}
