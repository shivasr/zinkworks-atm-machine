package com.app.zinkworks.atm.utils;

import com.app.zinkworks.atm.dto.Currency;
import com.app.zinkworks.atm.exceptions.ATMApplicationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the vendingUtil.
 */
public class VendingUtilTest {

    private static VendingUtil vendingUtil;
    private static Map<Integer, Currency> mapOfNotesToCurrency;

    @BeforeAll
    public static void setup() {
        vendingUtil = new VendingUtil();

        mapOfNotesToCurrency = new HashMap<>();

        mapOfNotesToCurrency.put(100, Currency.builder()
                .number(10)
                .currency(100)
                .build());

        mapOfNotesToCurrency.put(10, Currency.builder()
                .number(13)
                .currency(10)
                .build());
    }

    @Test
    public void calculateNotes_Case1_default() throws ATMApplicationException {

        List<Currency> actualCurrencies = vendingUtil.calculateExactNotes(250, mapOfNotesToCurrency);
        List<Currency> expectedCurrencies = Arrays.asList(new Currency[] {
                Currency.builder()
                        .currency(100)
                        .number(2)
                        .build(),
                Currency.builder()
                        .currency(10)
                        .number(5)
                        .build()
        });
        assertTrue(actualCurrencies.containsAll(expectedCurrencies));
    }

    @Test
    public void calculateNotes_Case2_FiftiesAreNotAvailable() throws ATMApplicationException {

        List<Currency> actualCurrencies = vendingUtil.calculateExactNotes(250, mapOfNotesToCurrency);

        List<Currency> expectedCurrencies = Arrays.asList(new Currency[] {
           Currency.builder()
                   .currency(100)
                   .number(2)
                   .build(),
            Currency.builder()
                    .currency(10)
                    .number(5)
                    .build()
        });
        assertTrue(actualCurrencies.containsAll(expectedCurrencies));
    }

    @Test
    public void calculateNotes_Case3_RequestAmountInTens() throws ATMApplicationException {

        List<Currency> actualCurrencies = vendingUtil.calculateExactNotes(30, mapOfNotesToCurrency);

        List<Currency> expectedCurrencies = Arrays.asList(new Currency[] {

                Currency.builder()
                        .currency(10)
                        .number(3)
                        .build()
        });
        assertTrue(actualCurrencies.containsAll(expectedCurrencies));
    }

    @Test
    public void calculateNotes_Case4_ATMDoesNotSufficientNotes() throws ATMApplicationException {

        Exception exception = assertThrows(ATMApplicationException.class, () -> {
            vendingUtil.calculateExactNotes(250, mapOfNotesToCurrency);
        });

        String expectedMessage = "ATM does not have sufficient currency notes.";
        assertTrue(exception.getMessage().contains(expectedMessage));

    }
}