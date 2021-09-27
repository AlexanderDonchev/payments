package com.payments.solution.service.validator;

import java.util.List;

public class ReverseTransactionValidator extends TransactionValidator{

    private static final List<FieldValidator> VALIDATORS = List.of(Validators.positiveAmountValidator,
            Validators.uuidValidator,
            Validators.emailValidator);

    @Override
    public List<FieldValidator> getValidators() {
        return null;
    }
}
