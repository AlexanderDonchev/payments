package com.payments.solution.service;

import com.payments.solution.api.model.request.EditMerchantRequest;
import com.payments.solution.api.model.response.GetMerchantsResponse;
import com.payments.solution.exception.BadRequestException;
import com.payments.solution.exception.NotFoundException;
import com.payments.solution.model.dto.MerchantDto;
import com.payments.solution.model.entity.Merchant;
import com.payments.solution.repository.MerchantRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public GetMerchantsResponse getMerchants(int page, int size) {

        if (page <0 || size <0) {

            throw new BadRequestException();
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Object[]> merchants = merchantRepository.findAllOrderById(pageable);

        return GetMerchantsResponse.builder()
                .merchants(merchants.get().map(this::toMerchantDto).collect(Collectors.toUnmodifiableList()))
                .page(merchants.getNumber())
                .size(merchants.getSize())
                .totalPages(merchants.getTotalPages())
                .build();
    }

    public void editMerchant(long merchantId, EditMerchantRequest editMerchantRequest) {

        Optional<Merchant> merchantOpt = merchantRepository.findById(merchantId);

        if (merchantOpt.isEmpty()) {
            throw new NotFoundException();
        } else {

            Merchant merchant = merchantOpt.get();

            merchant.setDescription(editMerchantRequest.getDescription());
            merchant.setEmail(editMerchantRequest.getEmail());
            merchant.setName(editMerchantRequest.getName());
            merchant.setStatus(editMerchantRequest.getStatus());

            merchantRepository.save(merchant);
        }
    }

    public void deleteMerchant(long merchantId) {

        Optional<Merchant> merchantOpt = merchantRepository.findById(merchantId);

        if (merchantOpt.isEmpty()) {
            throw new NotFoundException();
        }

        Merchant merchant = merchantOpt.get();

        if (!merchant.getTransactions().isEmpty()) {

            throw new BadRequestException();
        }

        merchantRepository.deleteById(merchantId);
    }

    private MerchantDto toMerchantDto(Object[] record) {
        return MerchantDto.builder()
                .id(((BigInteger) record[0]).longValue())
                .name((String) record[1])
                .description((String) record[2])
                .email((String) record[3])
                .status((String) record[4])
                .totalTransactionSum((BigDecimal) record[5])
                .build();
    }
}
