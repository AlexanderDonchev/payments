package com.payments.solution.service.validator.error;

public class PositiveAmountError extends Error {

    @Override
    public String getMessage() {
        return "Zero transaction amount";
    }
}
