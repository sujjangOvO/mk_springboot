package com.example.moonkey.dto;

import com.example.moonkey.domain.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderDisplayDto {

    @NotNull
    private long orderId;

    @NotNull
    private int number;

    @NotNull
    private Timestamp orderDate;

    @NotNull
    private String menuName;

    @NotNull
    private String storeNmae;

    @NotNull
    private String categoryName;

    @NotNull
    private int price;

    public static OrderDisplayDto from(Orders order) {
        return OrderDisplayDto.builder().
                orderId(order.getOrderId()).
                number(order.getNumber()).
                orderDate(order.getOrderDate()).
                menuName(order.getMenuId().getMenuName()).
                storeNmae(order.getStoreId().getName()).
                categoryName(order.getStoreId().getCategoryName().getCategoryName()).
                build();
    }

}
