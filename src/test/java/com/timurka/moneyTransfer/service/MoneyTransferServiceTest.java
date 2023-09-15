package com.timurka.moneyTransfer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.timurka.moneyTransfer.exception.InputDataException;
import com.timurka.moneyTransfer.model.request.ConfirmOperationRequest;
import com.timurka.moneyTransfer.model.response.ConfirmOperationResponse;
import com.timurka.moneyTransfer.model.response.TransferResponse;
import com.timurka.moneyTransfer.repository.MoneyTransferRepositoryTest;

import static com.timurka.moneyTransfer.data.TestData.*;

class MoneyTransferServiceTest {


    private MoneyTransferService service;

    public MoneyTransferServiceTest() {
        this.service = new MoneyTransferService(new MoneyTransferRepositoryTest());
    }


    @Test
    void transferShouldReturnWrongCardNumber() {
        InputDataException exc = Assertions.assertThrows(InputDataException.class, () -> {
            service.transfer(TRANSFER_REQUEST_WITH_NON_EXIST_CARD_NUMBER);
        });
        Assertions.assertEquals("Incorrect data entered: Wrong cardFrom number", exc.getMessage());
    }


    @Test
    void transferShouldReturnOperationIdAndCode() {
        TransferResponse expected = new TransferResponse("1", "0000");
        TransferResponse actual = service.transfer(TRANSFER_REQUEST_1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void confirmOperation() {
        String msgExpected = String.format("Transfer %d, commission %d. " +
                        "Before: cardFromValue %d, cardToValue %d. " +
                        "After: cardFromValue %d, cardToValue %d.",
                TRANSFER_VALUE_1,
                COMMISSION_1,
                VALUE_1,
                VALUE_2,
                CARD_1.getAmount().getValue() - TRANSFER_VALUE_1,
                CARD_2.getAmount().getValue() + TRANSFER_VALUE_1 - COMMISSION_1);
        ConfirmOperationResponse expected = new ConfirmOperationResponse(msgExpected);
        ConfirmOperationResponse actual = service.confirmOperation(new ConfirmOperationRequest("1", "0000"));
        Assertions.assertEquals(expected, actual);
    }

}
