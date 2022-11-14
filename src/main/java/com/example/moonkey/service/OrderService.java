package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Store;
import com.example.moonkey.domain.Menu;
import com.example.moonkey.domain.Orders;
import com.example.moonkey.dto.MenuDto;
import com.example.moonkey.dto.StoreDisplayDto;
import com.example.moonkey.dto.StoreDto;
import com.example.moonkey.dto.OrderDto;

import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.OrderRepository;
import com.example.moonkey.repository.StoreRepository;
import com.example.moonkey.util.SecurityUtil;


import lombok.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;

    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository){
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
    }
    @Transactional
    public List<OrderDto> getOrders(){

        List<Orders> ordersList = orderRepository.findAll();
        Iterator<Orders> iter = ordersList.iterator();


        List<OrderDto> orderDtoList = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Orders order = iter.next();
            OrderDto orderDto = OrderDto.builder().
                    orderId(order.getOrderId()).
                    number(order.getNumber()).
                    orderDate(order.getOrderDate()).
                    menuId(order.getMenuId()).
                    storeId(order.getStoreId()).
                    uid(order.getUid()).
                    build();

            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
