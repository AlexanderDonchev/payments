package com.payments.solution.api.model.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String statusCode;

    private String message;

    private List<String> errors;
}
