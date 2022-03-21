package com.app.zinkworks.atm.dto;

import lombok.*;

import java.util.List;

/**
 * Response to the withdraw request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawResponse {
    private String message;

    private List<Currency> notes;
}
