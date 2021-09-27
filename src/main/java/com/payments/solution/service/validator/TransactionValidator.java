package com.payments.solution.service.validator;

import com.payments.solution.model.dto.transaction.TransactionData;
import com.payments.solution.service.validator.error.Error;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class TransactionValidator {

    protected abstract List<FieldValidator> getValidators();

    public List<Error> validate(TransactionData data) {
        return getValidators().stream()
                .map(v -> v.validate(data))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toUnmodifiableList());
    }
}
