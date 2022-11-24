package com.example.moonkey.service;

import com.example.moonkey.domain.*;
import com.example.moonkey.dto.OrderDto;
import com.example.moonkey.dto.OrderDisplayDto;

import com.example.moonkey.dto.PartyDto;
import com.example.moonkey.exception.*;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.MenuRepository;
import com.example.moonkey.repository.OrderRepository;
import com.example.moonkey.repository.StoreRepository;
import com.example.moonkey.util.SecurityUtil;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository
                        ,MenuRepository menuRepository, StoreRepository storeRepository){
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
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
                    categoryName(order.getStoreId().getCategoryName().getCategoryName()).
                    build();

            orderDisplayDtoList.add(orderDisplayDto);
        }
        return orderDisplayDtoList;
    }

    @Transactional
    public List<OrderDisplayDto> getOrderListByUid(long uid){

        List<Orders> ordersList = orderRepository.findAll();
        Iterator<Orders> iter = ordersList.iterator();

        List<OrderDisplayDto> orderDisplayDtoList = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Orders order = iter.next();

            if(order.getUid().getUid() == uid) {
                OrderDisplayDto orderDisplayDto = OrderDisplayDto.builder().
                        orderId(order.getOrderId()).
                        number(order.getNumber()).
                        orderDate(order.getOrderDate()).
                        menuName(order.getMenuId().getMenuName()).
                        storeNmae(order.getStoreId().getName()).
                        price(order.getMenuId().getPrice() * order.getNumber()).
                        categoryName(order.getStoreId().getCategoryName().getCategoryName()).
                        build();

                orderDisplayDtoList.add(orderDisplayDto);
            }
        }
        return orderDisplayDtoList;
    }

    @Transactional
    public OrderDto register(OrderDto orderDto){

        Account account =
                SecurityUtil.getCurrentUsername()
                        .flatMap(accountRepository::findOneWithAuthoritiesById)
                        .orElseThrow(()->new NotFoundMemberException("Member not found"));

        Menu menu = menuRepository.findOneByMenuId(orderDto.getMenuId());
        if(menu == null) throw new NotFoundMenuException("Menu not found");

        Store store = storeRepository.findOneByStoreId(orderDto.getStoreId());
        if(store == null) throw new NotFoundStoreException("Store not found");

        LocalDateTime now = LocalDateTime.now();

        Orders order = Orders.builder()
                .orderId(orderDto.getOrderId())
                .number(orderDto.getNumber())
                .orderDate(new Timestamp(Timestamp.valueOf(now).getTime()))
                .menuId(menu)
                .storeId(store)
                .uid(account)
                .build();

        return orderDto.from(orderRepository.save(order));
    }

    @Transactional
    public void unregister(long orderId){
        Orders orders = orderRepository.findOneByOrderId(orderId)
                .orElseThrow(()->new NotFoundOrderException("Order not found"));

        OrderDto orderDto = OrderDto.builder()
                .orderId(orders.getOrderId())
                .number(orders.getNumber())
                .orderDate(orders.getOrderDate())
                .menuId(orders.getMenuId().getMenuId())
                .storeId(orders.getStoreId().getStoreId())
                .uid(orders.getUid().getUid())
                .build();


        orderRepository.deleteById(orderDto.getOrderId());
    }
}
