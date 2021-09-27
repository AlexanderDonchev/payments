package com.payments.solution.api;

import com.payments.solution.api.model.request.EditMerchantRequest;
import com.payments.solution.api.model.response.GetMerchantsResponse;
import com.payments.solution.service.MerchantService;
import javax.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchants")
@RolesAllowed("ADMIN")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public ResponseEntity<GetMerchantsResponse> getMerchants(@RequestParam int page, @RequestParam int size) {

        GetMerchantsResponse merchants = merchantService.getMerchants(page, size);

        return ResponseEntity.ok(merchants);
    }

    @PutMapping("/{merchantId}")
    public ResponseEntity<Void> editMerchant(@PathVariable long merchantId,
            @RequestBody EditMerchantRequest editMerchantRequest) {

        merchantService.editMerchant(merchantId, editMerchantRequest);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{merchantId}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable long merchantId) {

        merchantService.deleteMerchant(merchantId);
        return null;
    }
}
