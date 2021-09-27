package com.payments.solution.api.advice;

import com.payments.solution.api.model.response.ErrorResponse;
import com.payments.solution.exception.AccessForbiddenException;
import com.payments.solution.exception.BadRequestException;
import com.payments.solution.exception.DataValidationException;
import com.payments.solution.service.validator.error.Error;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class PaymentControllerAdvice {

    @ExceptionHandler({BadRequestException.class})
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse badRequest(BadRequestException exception) {

        return ErrorResponse.builder()
                .statusCode("400")
                .message("bad.request")
                .build();
    }

    @ExceptionHandler({DataValidationException.class})
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse forbidden(DataValidationException exception) {

        return ErrorResponse.builder()
                .statusCode("400")
                .message("bad.request")
                .errors(exception.getErrors().stream()
                        .map(Error::getMessage)
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    @ExceptionHandler({AccessForbiddenException.class})
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse forbidden(AccessForbiddenException exception) {

        return ErrorResponse.builder()
                .statusCode("403")
                .message("forbidden")
                .build();
    }


}
