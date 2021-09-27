package com.payments.solution.service.validator;

import com.payments.solution.model.dto.transaction.TransactionData;
import com.payments.solution.service.validator.error.Error;
import java.util.Optional;

@FunctionalInterface
public interface FieldValidator {

    Optional<Error> validate(TransactionData data);
}
