package com.example.moonkey.dto;

import com.example.moonkey.domain.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    @NotNull
    private long deliveryId;

    @NotNull
    private long uid; // 배달원

    @NotNull
    private long orderId;

    @NotNull
    private long storeId;

    @NotNull
    private int distance;

    @NotNull
    private String address;

    @NotNull
    private boolean callCheck;

    @NotNull
    private boolean deliveryCheck;

    @Column(nullable = true)
    private String requests;

    @NotNull
    private int pay;

    @Column(nullable = true)
    @JsonIgnore
    private long totalPay;

    public static DeliveryDto from(Delivery delivery){
        return DeliveryDto.builder().
                deliveryId(delivery.getDeliveryId()).
                uid(delivery.getUid().getUid()).
                orderId(delivery.getOrderId().getOrderId()).
                storeId(delivery.getStoreId().getStoreId()).
                distance(delivery.getDistance()).
                address(delivery.getAddress()).
                callCheck(delivery.isCallCheck()).
                deliveryCheck(delivery.isDeliveryCheck()).
                requests(delivery.getRequests()).
                pay(delivery.getPay()).
                totalPay(delivery.getTotalPay()).
                build();
    }
}
