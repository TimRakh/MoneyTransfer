package com.timurka.moneyTransfer.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.maren.moneytransfer.model.Violation;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationErrorResponse {
    private final List<Violation> violations;
}