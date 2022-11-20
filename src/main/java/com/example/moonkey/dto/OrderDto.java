package com.example.moonkey.dto;

import com.example.moonkey.domain.Orders;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderDto {

    @NotNull
    private long orderId;

    @NotNull
    private int number;

    @NotNull
    private Timestamp orderDate;

    @NotNull
    private long menuId;

    @NotNull
    private long storeId;

    @NotNull
    private long uid;

    public static OrderDto from(Orders order){
        return OrderDto.builder().
                orderId(order.getOrderId()).
                number(order.getNumber()).
                orderDate(order.getOrderDate()).
                menuId(order.getMenuId().getMenuId()).
                storeId(order.getStoreId().getStoreId()).
                uid(order.getUid().getUid()).
                build();
    }
}
