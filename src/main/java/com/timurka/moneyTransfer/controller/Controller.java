package com.timurka.moneyTransfer.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.timurka.moneyTransfer.model.request.ConfirmOperationRequest;
import com.timurka.moneyTransfer.model.request.TransferRequest;
import com.timurka.moneyTransfer.model.response.ConfirmOperationResponse;
import com.timurka.moneyTransfer.model.response.TransferResponse;

@RestController
@AllArgsConstructor
@CrossOrigin
public class Controller {
    private final MoneyTransferService moneyTransferService;

    @PostMapping("/transfer")
    public TransferResponse postTransfer(@RequestBody TransferRequest transferRequest) {
        return moneyTransferService.transfer(transferRequest);
    }

    @PostMapping("/confirmOperation")
    public ConfirmOperationResponse postConfirmOperation(@RequestBody ConfirmOperationRequest confirmOperationRequest) {
        return moneyTransferService.confirmOperation(confirmOperationRequest);
    }
}
