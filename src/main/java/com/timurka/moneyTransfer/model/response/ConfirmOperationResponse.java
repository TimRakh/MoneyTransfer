package com.timurka.moneyTransfer.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmOperationResponse {
    private String operationId;
}
