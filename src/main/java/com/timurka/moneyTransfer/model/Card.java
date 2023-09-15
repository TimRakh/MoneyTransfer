package com.timurka.moneyTransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static ru.maren.moneytransfer.web.constant.PatternConstant.*;

@Data
@AllArgsConstructor
public class Card {
    public Card(String cardNumber, String validTill, String cvv) {
        this.cardNumber = cardNumber;
        this.validTill = validTill;
        this.cvv = cvv;
    }

    @NotNull(message = "Card number can't be null")
    @Pattern(regexp = CARD_NUMBER_PATTERN,
            message = "Wrong format of card number")
    private String cardNumber;

    @NotNull(message = "Valid thru can't be null")
    @Pattern(regexp = VALID_THRU_PATTERN,
            message = "Wrong format of valid thru")
    private String validTill;

    @NotNull(message = "cvv code can't be null")
    @Pattern(regexp = CVV_PATTERN,
            message = "Wrong format of cvv")
    private String cvv;

    @NotNull
    private Amount amount;

}}
