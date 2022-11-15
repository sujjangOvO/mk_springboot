package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Orders;
import com.example.moonkey.dto.OrderDto;
import com.example.moonkey.dto.OrderDisplayDto;

import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.OrderRepository;
import com.example.moonkey.util.SecurityUtil;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<OrderDisplayDto> getOrderList(){

        List<Orders> ordersList = orderRepository.findAll();
        Iterator<Orders> iter = ordersList.iterator();



        List<OrderDisplayDto> orderDisplayDtoList = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Orders order = iter.next();
            OrderDisplayDto orderDisplayDto = OrderDisplayDto.builder().
                    orderId(order.getOrderId()).
                    number(order.getNumber()).
                    orderDate(order.getOrderDate()).
                    menuName(order.getMenuId().getMenuName()).
                    storeNmae(order.getStoreId().getName()).
                    price(order.getMenuId().getPrice()*order.getNumber()).
                    build();

            orderDisplayDtoList.add(orderDisplayDto);
        }
        return orderDisplayDtoList;
    }

    @Transactional
    public OrderDto register(OrderDto orderDto){

        Account account =
                SecurityUtil.getCurrentUsername()
                        .flatMap(accountRepository::findOneWithAuthoritiesById)
                        .orElseThrow(()->new NotFoundMemberException("Member not found"));

        LocalDateTime now = LocalDateTime.now();

        Orders order = Orders.builder()
                .orderId(orderDto.getOrderId())
                .number(orderDto.getNumber())
                .orderDate(new Timestamp(Timestamp.valueOf(now).getTime()))
                .menuId(orderDto.getMenuId())
                .storeId(orderDto.getStoreId())
                .uid(orderDto.getUid())
                .build();

        return orderDto.from(orderRepository.save(order));
    }
}
