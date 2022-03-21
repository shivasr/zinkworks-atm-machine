package com.app.zinkworks.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetails {
    private String accountNumber;

    private String prevOverdraft;

    private String newOverdraft;

    private String prevBalance;

    private String newBalance;

    private String message;

    private String withdrawAmount;

    private boolean success = true;
}
