package com.example.moonkey.dto;

import com.example.moonkey.domain.Orders;
import com.example.moonkey.domain.Package;
import com.example.moonkey.domain.Party;
import com.example.moonkey.domain.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageDto {

    @NotNull
    private long packageId;

    @NotNull
    private long orderId; // FK

    @NotNull
    private long partyId; // FK

    @NotNull
    private String product;

    @NotNull
    private String address;

    @NotNull
    private int amount;


    public static PackageDto from(Package aPackage){
        return PackageDto.builder()
                .packageId(aPackage.getPackageId())
                .product(aPackage.getProduct())
                .address(aPackage.getAddress())
                .amount(aPackage.getAmount())
                .orderId(aPackage.getOrderId().getOrderId())
                .partyId(aPackage.getPartyId().getPartyId())
                .build();
    }
}
