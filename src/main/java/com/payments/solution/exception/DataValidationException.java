package com.payments.solution.exception;

import com.payments.solution.service.validator.error.Error;
import java.util.List;

public class DataValidationException extends RuntimeException {

    private List<Error> errors;

    public DataValidationException(List<Error> errors) {
        this.errors = errors;
    }

    public List<Error> getErrors() {

        return errors;
    }
}
