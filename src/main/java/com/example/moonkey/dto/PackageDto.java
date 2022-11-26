package com.example.moonkey.dto;

import com.example.moonkey.domain.*;
import com.example.moonkey.domain.Package;
import com.example.moonkey.exception.NotFoundOrderException;
import com.example.moonkey.repository.OrderRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageDto {
    @Autowired
    @JsonIgnore
    private OrderRepository orderRepository;

    @NotNull
    private long packageId;


    private List<Long> orderId; // FK

    @NotNull
    private long partyId; // FK


    private List<String> product;

    @NotNull
    private String address;

    @JsonIgnore
    private int amount;

    public List<Orders> getOrders(List<Long> list){
        List<Orders> memberList = new ArrayList<>();
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            long num = (long) iter.next();
            memberList.add(orderRepository.findOneByOrderId(num).orElseThrow(()->new NotFoundOrderException("Order not found")));
        }
        return memberList;
    }


    public static PackageDto from(Package aPackage){
        return PackageDto.builder()
                .packageId(aPackage.getPackageId())
                .product(aPackage.getProduct())
                .address(aPackage.getAddress())
                .amount(aPackage.getAmount())
                .orderId(aPackage.getOrderIds())
                .partyId(aPackage.getPartyId().getPartyId())
                .build();
    }
}
