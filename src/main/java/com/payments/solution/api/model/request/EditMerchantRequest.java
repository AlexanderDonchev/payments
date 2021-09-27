package com.payments.solution.api.model.request;

import lombok.Data;

@Data
public class EditMerchantRequest {

    private String name;

    private String description;

    private String email;

    private String status;
}
