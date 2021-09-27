package com.payments.solution.service.validator.error;

public class ZeroAmountError extends Error{

    @Override
    public String getMessage() {
        return "Amount not null or zero";
    }
}
