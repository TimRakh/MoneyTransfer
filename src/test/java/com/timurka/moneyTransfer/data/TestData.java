package com.timurka.moneyTransfer.data;

import com.timurka.moneyTransfer.model.Amount;
import com.timurka.moneyTransfer.model.Card;
import com.timurka.moneyTransfer.model.request.ConfirmOperationRequest;
import com.timurka.moneyTransfer.model.request.TransferRequest;

public class TestData {
    final static String CURRENCY = "RUB";

    public final static String CARD_NUMBER_1 = "1234815162342321";
    final static String VALID_TILL_1 = "12/23";
    final static String CVV_1 = "123";
    public final static Integer VALUE_1 = 100_000;
    public final static Card CARD_1 = new Card(CARD_NUMBER_1, VALID_TILL_1, CVV_1, new Amount(VALUE_1, CURRENCY));

    public final static String CARD_NUMBER_2 = "4276400091114882";
    final static String VALID_TILL_2 = "34/30";
    final static String CVV_2 = "456";
    public final static Integer VALUE_2 = 740_000;
    public final static Card CARD_2 = new Card(CARD_NUMBER_2, VALID_TILL_2, CVV_2, new Amount(VALUE_2, CURRENCY));


    public final static Integer TRANSFER_VALUE_1 = 100;
    public final static Integer COMMISSION_1 = (int) (TRANSFER_VALUE_1 * 0.01);
    public final static TransferRequest TRANSFER_REQUEST_1 = new TransferRequest(
            CARD_NUMBER_1, VALID_TILL_1, CVV_1, CARD_NUMBER_2,
            new Amount(TRANSFER_VALUE_1, CURRENCY)
    );
    public final static TransferRequest TRANSFER_REQUEST_WITH_NON_EXIST_CARD_NUMBER = new TransferRequest(
            "1234123412341234", VALID_TILL_2, CVV_2, CARD_NUMBER_2,
            new Amount(TRANSFER_VALUE_1, CURRENCY)
    );
    public final static ConfirmOperationRequest CONFIRM_OPERATION_REQUEST_1 = new ConfirmOperationRequest(
            "1", "0000"
    );


}
