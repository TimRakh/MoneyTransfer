package com.timurka.moneyTransfer.repository;

import org.springframework.stereotype.Repository;
import com.timurka.moneyTransfer.model.Amount;
import com.timurka.moneyTransfer.model.Card;
import com.timurka.moneyTransfer.model.request.TransferRequest;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MoneyTransferRepository {
    Random random = new Random();
    private final AtomicLong operationId = new AtomicLong();
    private final Map<String, Card> cardMap = new ConcurrentHashMap<>();
    private final Map<String, String> codes = new ConcurrentHashMap<>();
    private final Map<String, TransferRequest> readyToTransfer = new ConcurrentHashMap<>();

    {
        String cardNumber1 = "1234815162342321";
        String cardNumber2 = "4276400091114882";

        cardMap.put(cardNumber1, new Card(cardNumber1, "12/23", "123",
                new Amount(100_000, "RUB")));
        cardMap.put(cardNumber2, new Card(cardNumber2, "04/30", "456",
                new Amount(740_000, "RUB")));
    }

    public void putTransfers(String operationId, TransferRequest transferRequest) {
        readyToTransfer.put(operationId, transferRequest);
    }

    public TransferRequest removeTransfer(String operationId) {
        return readyToTransfer.remove(operationId);
    }

    public void putCodes(String operationId, String code) {
        codes.put(operationId, code);
    }

    public String removeCodes(String operationId) {
        return codes.remove(operationId);
    }

    public Card getCard(String cardNumber) {
        return cardMap.get(cardNumber);
    }


    public long incrementAndGetOperationId() {
        return operationId.incrementAndGet();
    }

    public int getRandomCode() {
        int code = 1000 + random.nextInt(8999); // Random 4 digit code
        return code;
    }
}
