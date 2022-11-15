package com.example.moonkey.dto;

import com.example.moonkey.domain.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    @NotNull
    private int deliveryId;

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

    public static DeliveryDto from(Delivery delivery){
        return DeliveryDto.builder().
                deliveryId(delivery.getDeliveryId()).
                uid(delivery.getUid().getUid()).
                orderId(delivery.getOrderId().getOrderId()).
                storeId(delivery.getStoreId().getStoreId()).
                distance(delivery.getDistance()).
                address(delivery.getAddress()).
                build();
    }
}
