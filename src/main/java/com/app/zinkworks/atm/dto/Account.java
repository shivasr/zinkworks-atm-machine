package com.app.zinkworks.atm.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @NonNull
    private String accountNumber;

    @NonNull
    private String pin;
}
