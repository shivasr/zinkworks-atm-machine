package com.app.zinkworks.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountBalance {
    private String accountNumber;

    private String balance;

    private String overdraft;

    String message;

    @Builder.Default
    boolean success = true;
}
