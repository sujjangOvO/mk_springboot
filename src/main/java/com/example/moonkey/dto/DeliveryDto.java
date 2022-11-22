package com.example.moonkey.dto;

import com.example.moonkey.domain.*;
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

    @Column(nullable = true)
    private String requests;

    @NotNull
    private int pay;

    public static DeliveryDto from(Delivery delivery){
        return DeliveryDto.builder().
                deliveryId(delivery.getDeliveryId()).
                uid(delivery.getUid().getUid()).
                orderId(delivery.getOrderId().getOrderId()).
                storeId(delivery.getStoreId().getStoreId()).
                distance(delivery.getDistance()).
                address(delivery.getAddress()).
                callCheck(delivery.isCallCheck()).
                requests(delivery.getRequests()).
                pay(delivery.getPay()).
                build();
    }
}
