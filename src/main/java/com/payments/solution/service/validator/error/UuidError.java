package com.payments.solution.service.validator.error;

public class UuidError extends Error{

    @Override
    public String getMessage() {
        return "Missing transaction UUID";
    }
}
