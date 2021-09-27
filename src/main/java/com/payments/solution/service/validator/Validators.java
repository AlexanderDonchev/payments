package com.payments.solution.service.validator;

import com.payments.solution.model.dto.transaction.TransactionData;
import com.payments.solution.service.validator.error.PositiveAmountError;
import com.payments.solution.service.validator.error.EmailError;
import com.payments.solution.service.validator.error.UuidError;
import com.payments.solution.service.validator.error.ZeroAmountError;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class Validators {

    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static FieldValidator positiveAmountValidator = (TransactionData data) -> {
        BigDecimal amount = data.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 1) {

            return Optional.of(new PositiveAmountError());
        } else {
            return Optional.empty();
        }
    };

    public static FieldValidator zeroAmountValidator = (TransactionData data) -> {
        BigDecimal amount = data.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {

            return Optional.of(new ZeroAmountError());
        } else {
            return Optional.empty();
        }
    };

    public static FieldValidator uuidValidator = (TransactionData data) -> {

        if (!StringUtils.hasLength(data.getUuid())) {
            return Optional.of(new UuidError());
        } else {
            return Optional.empty();
        }
    };

    public static FieldValidator emailValidator = (TransactionData data) -> {

        if (!PATTERN.matcher(data.getCustomerEmail()).matches()) {
            return Optional.of(new EmailError());
        } else {
            return Optional.empty();
        }
    };
}
