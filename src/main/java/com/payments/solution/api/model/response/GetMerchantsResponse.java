package com.payments.solution.api.model.response;

import com.payments.solution.model.dto.MerchantDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMerchantsResponse {

    private List<MerchantDto> merchants;

    private int page;

    private int size;

    private int totalPages;
}
