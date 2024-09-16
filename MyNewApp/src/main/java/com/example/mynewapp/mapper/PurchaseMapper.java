package com.example.mynewapp.mapper;

import com.example.mynewapp.entity.Purchase;
import com.example.mynewapp.dto.PurchaseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    Purchase purchaseDtoToPurchase(PurchaseDto purchaseDto);

    PurchaseDto purchaseToPurchaseDto(Purchase purchase);
}
