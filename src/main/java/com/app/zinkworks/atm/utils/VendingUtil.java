package com.app.zinkworks.atm.utils;

import com.app.zinkworks.atm.dto.Currency;
import com.app.zinkworks.atm.exceptions.ATMApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Util class to calculate the number of currency notes to be dispensed.
 */
public class VendingUtil {
    /**
     * List of currency notes.
     */
    private int[] notes = new int[] {
      100, 50, 20, 10, 5
    };

    /**
     * Calculate the number of notes to be dispensed based on the available currency notes for the given amount.
     * @param amount Amount for which currency notes has to be dispensed
     * @param mapOfCurrencyToAvailableCount Map of currency note to <codde>Currency</codde> bean containing the count
     *                                      of available notes in the ATM.
     * @return List of <code>Currency</code> beans containing currency note and number of notes to be dispensed
     * @throws ATMApplicationException if there are no sufficient notes to match the amount requested.
     */
    public List<Currency> calculateExactNotes(int amount, Map<Integer, Currency> mapOfCurrencyToAvailableCount)
            throws ATMApplicationException {
        // List of notes
        List<Currency> currencies = new ArrayList<>();

        // Initialise with total amount to be dispensed
        int remaining = amount;

        for (int i = 0; i < notes.length  && remaining != 0; i++) { // Loop through all currency notes
            // Calculate number of notes required for the current note
            // (say, how many 100 Euro notes are required for 250 Euros)
            int requiredNotes = remaining / notes[i];

            // If currency note are available in the ARM and there are sufficient notes to fulfill the required amount
            if(mapOfCurrencyToAvailableCount.containsKey(notes[i]) &&
                    mapOfCurrencyToAvailableCount.get(notes[i]).getNumber() >= 0) {
                Currency currency = mapOfCurrencyToAvailableCount.get(notes[i]);
                int count = currency.getNumber();

                int assignedNotes = (count >= requiredNotes)? requiredNotes : count;

                // Update the availability in the ATM
                currency.setNumber(count - assignedNotes);
                mapOfCurrencyToAvailableCount.put(notes[i], currency);
                currencies.add(Currency.builder()
                        .number(assignedNotes)
                        .currency(notes[i])
                        .build());

                // remaining amount after dispensing
                remaining -= assignedNotes * notes[i];
            }
        }

        if(remaining != 0) // If there is amount remaining to be dispensed.
            throw new ATMApplicationException("ATM does not have sufficient currency notes.");

        return currencies;
    }
}
