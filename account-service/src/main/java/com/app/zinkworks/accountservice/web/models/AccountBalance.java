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
public class AccountBalance {
    private String accountNumber;

    private String balance;

    private String overdraft;

    private String message;

    private String returnCode;

    @Builder.Default
    boolean success = true;
}
