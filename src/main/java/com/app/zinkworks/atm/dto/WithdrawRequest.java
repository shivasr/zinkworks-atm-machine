package com.app.zinkworks.atm.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawRequest {

    @NonNull
    private String amount;

    @NonNull
    private String accountNumber;

    @NonNull
    private String pin;
}
