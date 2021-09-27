package com.payments.solution.service.validator;

import java.util.List;

public class ChargeTransactionValidator extends TransactionValidator{

    private final static List<FieldValidator> VALIDATORS = List.of(Validators.positiveAmountValidator,
            Validators.uuidValidator,
            Validators.emailValidator);

    @Override
    public List<FieldValidator> getValidators() {
        return VALIDATORS;
    }
}
