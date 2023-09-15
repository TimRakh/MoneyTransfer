package com.timurka.moneyTransfer.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ConfirmOperationRequest {
    @NotNull
    private String operationId;
    @NotNull
    private String code;
}
