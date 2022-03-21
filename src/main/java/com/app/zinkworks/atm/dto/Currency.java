package com.app.zinkworks.atm.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Currency {
    @NonNull
    private int currency;

    @NonNull
    private int number;

}
