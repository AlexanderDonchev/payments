package com.payments.solution.service.validator.error;

public class EmailError extends Error{

    @Override
    public String getMessage() {

        return "Email format not valid";
    }
}
