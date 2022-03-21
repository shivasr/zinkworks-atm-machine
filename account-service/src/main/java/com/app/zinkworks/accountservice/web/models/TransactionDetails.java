package com.app.zinkworks.accountservice.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    private String returnCode;

    private String message;

    private String withdrawAmount;

    private boolean success = true;
}
