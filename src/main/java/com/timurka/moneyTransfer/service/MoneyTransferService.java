package com.timurka.moneyTransfer.service;

import com.timurka.moneyTransfer.repository.MoneyTransferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.timurka.moneyTransfer.exception.InputDataException;
import com.timurka.moneyTransfer.model.Amount;
import com.timurka.moneyTransfer.model.Card;
import com.timurka.moneyTransfer.model.request.ConfirmOperationRequest;
import com.timurka.moneyTransfer.model.request.TransferRequest;
import com.timurka.moneyTransfer.model.response.ConfirmOperationResponse;
import com.timurka.moneyTransfer.model.response.TransferResponse;

import javax.validation.Valid;

@Slf4j
@Service
@Validated
@AllArgsConstructor
public class MoneyTransferService {
    private final MoneyTransferRepository repository;

    public TransferResponse transfer(@Valid TransferRequest transferRequest) {
        transferValidation(transferRequest);
        String operationId = Long.toString(repository.incrementAndGetOperationId());
        /*int code = repository.getRandomCode();*/
        repository.putTransfers(operationId, transferRequest);
        repository.putCodes(operationId, "0000");

        return new TransferResponse(operationId, "0000");
    }


    public ConfirmOperationResponse confirmOperation(@Valid ConfirmOperationRequest confirmOperationRequest) {
        String operationId = confirmOperationRequest.getOperationId();
        TransferRequest transferRequest = repository.removeTransfer(operationId);

        confirmOperationValidation(transferRequest, confirmOperationRequest);

        Card cardFrom = repository.getCard((transferRequest.getCardFromNumber()));
        Card cardTo = repository.getCard(transferRequest.getCardToNumber());

        int valueFrom = cardFrom.getAmount().getValue();
        int valueTo = cardTo.getAmount().getValue();
        int transferValue = transferRequest.getAmount().getValue() / 100;
        int commission = (int) (transferValue * 0.01);

        cardFrom.getAmount().setValue(valueFrom - transferValue);
        cardTo.getAmount().setValue(valueTo + transferValue - commission);

        String msg = String.format("Transfer %d, commission %d. " +
                        "Before: cardFromValue %d, cardToValue %d. " +
                        "After: cardFromValue %d, cardToValue %d.",
                transferValue,
                commission,
                valueFrom,
                valueTo,
                cardFrom.getAmount().getValue(),
                cardTo.getAmount().getValue());
        log.info(msg);
        return new ConfirmOperationResponse(operationId);

    }


    private void confirmOperationValidation(TransferRequest transferRequest, ConfirmOperationRequest confirmOperationRequest) {
        String operationId = confirmOperationRequest.getOperationId();
        if (transferRequest == null) {
            throw new InputDataException("Incorrect data entered: Wrong operationID");
        }

        String codeFromRequest = confirmOperationRequest.getCode();
        String codeExpected = repository.removeCodes(operationId);
        if (!codeExpected.equals(codeFromRequest)) {
            throw new InputDataException("Incorrect data entered: Wrong code");
        }
    }

    private void transferValidation(TransferRequest transferRequest) {
        Card cardFrom = repository.getCard(transferRequest.getCardFromNumber());
        Card cardTo = repository.getCard(transferRequest.getCardToNumber());

        if (cardFrom == null) {
            throw new InputDataException("Incorrect data entered: Wrong cardFrom number");
        }
        if (cardTo == null) {
            throw new InputDataException("Incorrect data entered: Wrong cardTo number");
        }
        boolean checkedInputData = checkInputData(transferRequest);
        if (!checkedInputData) {
            throw new InputDataException("Incorrect data entered");
        }
        Amount amountCardFrom = cardFrom.getAmount();

        Amount transferAmount = transferRequest.getAmount();
        int transferValue = transferAmount.getValue() / 100;
        if (amountCardFrom.getValue() < transferValue) {
            throw new InputDataException("Insufficient funds");
        }
    }


    public boolean checkInputData(TransferRequest transferRequest) {
        Card cardFrom = repository.getCard(transferRequest.getCardFromNumber());
        Card cardFromRequest = transferRequest.getCard();

        cardFromRequest.setAmount(cardFrom.getAmount());
        return cardFromRequest.equals(cardFrom);
    }
}
