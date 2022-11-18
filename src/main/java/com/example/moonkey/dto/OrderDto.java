package com.example.moonkey.dto;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Menu;
import com.example.moonkey.domain.Orders;
import com.example.moonkey.domain.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
    private Menu menuId;

    @NotNull
    private Store storeId;

    @NotNull
    private Account uid;

    public static OrderDto from(Orders order){
        return OrderDto.builder().
                orderId(order.getOrderId()).
                number(order.getNumber()).
                orderDate(order.getOrderDate()).
                menuId(order.getMenuId()).
                storeId(order.getStoreId()).
                uid(order.getUid()).
                build();
    }
}
