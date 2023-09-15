package com.timurka.moneyTransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class Amount {
    @Min(0)
    private Integer value;
    private String currency;
}
