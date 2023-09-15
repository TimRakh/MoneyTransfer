package com.timurka.moneyTransfer.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maren.moneytransfer.model.Amount;
import ru.maren.moneytransfer.model.Card;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static ru.maren.moneytransfer.web.constant.PatternConstant.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    @NotNull(message = "Card number can't be null")
    @Pattern(regexp = CARD_NUMBER_PATTERN,
            message = "Wrong format of card number")
    private String cardFromNumber;

    @NotNull(message = "Valid thru can't be null")
    @Pattern(regexp = VALID_THRU_PATTERN,
            message = "Wrong format of valid thru")
    private String cardFromValidTill;

    @NotNull(message = "cvv code can't be null")
    @Pattern(regexp = CVV_PATTERN,
            message = "Wrong format of cvv")
    private String cardFromCVV;

    @NotNull(message = "Card number can't be null")
    @Pattern(regexp = CARD_NUMBER_PATTERN,
            message = "Wrong format of card number")
    private String cardToNumber;

    @NotNull
    private Amount amount;

    public Card getCard() {
        return new Card(cardFromNumber, cardFromValidTill, cardFromCVV);
    }
}
