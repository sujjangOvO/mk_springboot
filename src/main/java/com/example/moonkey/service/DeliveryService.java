package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Delivery;
import com.example.moonkey.domain.Orders;
import com.example.moonkey.dto.DeliveryDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundOrderException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.DeliveryRepository;
import com.example.moonkey.repository.OrderRepository;
import com.example.moonkey.repository.StoreRepository;
import com.example.moonkey.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private AccountRepository accountRepository;
    private StoreRepository storeRepository;
    private OrderRepository orderRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, AccountRepository accountRepository
                            ,StoreRepository storeRepository, OrderRepository orderRepository){
        this.deliveryRepository = deliveryRepository;
        this.accountRepository = accountRepository;
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public DeliveryDto register(DeliveryDto deliveryDto){
        Account account =
                SecurityUtil.getCurrentUsername()
                        .flatMap(accountRepository::findOneWithAuthoritiesById)
                        .orElseThrow(()->new NotFoundMemberException("Member not found"));

        Orders orders = orderRepository.findOneByOrderId(deliveryDto.getOrderId())
                .orElseThrow(()->new NotFoundOrderException("Order not found"));

        Delivery delivery = Delivery.builder()
                .uid(account)
                .orderId(orders)
                .storeId(storeRepository.findOneByStoreId(deliveryDto.getStoreId()))
                .distance(deliveryDto.getDistance())
                .address(deliveryDto.getAddress())
                .callCheck(deliveryDto.isCallCheck())
                .deliveryCheck(deliveryDto.isDeliveryCheck())
                .requests(deliveryDto.getRequests())
                .pay(deliveryDto.getPay())
                .totalPay(deliveryDto.getTotalPay())
                .build();


        return deliveryDto.from(deliveryRepository.save(delivery));
    }


    @Transactional
    public List<DeliveryDto> getMyDeliveries(long uid){

        List<Delivery> deliveryList = deliveryRepository.findAll();
        Iterator<Delivery> iter = deliveryList.iterator();


        List<DeliveryDto> deliveryDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Delivery delivery = iter.next();

            if(delivery.getUid().getUid() != uid) continue;

            DeliveryDto deliveryDto = DeliveryDto.builder()
                    .deliveryId(delivery.getDeliveryId())
                    .uid(delivery.getUid().getUid())
                    .orderId(delivery.getOrderId().getOrderId())
                    .storeId(delivery.getStoreId().getStoreId())
                    .distance(delivery.getDistance())
                    .address(delivery.getAddress())
                    .callCheck(delivery.isCallCheck())
                    .deliveryCheck(delivery.isDeliveryCheck())
                    .requests(delivery.getRequests())
                    .pay(delivery.getPay())
                    .totalPay(delivery.getTotalPay())
                    .build();

            deliveryDtos.add(deliveryDto);
        }
        return deliveryDtos;

    }


    @Transactional
    public List<DeliveryDto> getRequests(){

        List<Delivery> deliveryList = deliveryRepository.findAll();
        Iterator<Delivery> iter = deliveryList.iterator();


        List<DeliveryDto> deliveryDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Delivery delivery = iter.next();

            if(delivery.isCallCheck() == true) continue;

            DeliveryDto deliveryDto = DeliveryDto.builder()
                    .deliveryId(delivery.getDeliveryId())
                    .uid(delivery.getUid().getUid())
                    .orderId(delivery.getOrderId().getOrderId())
                    .storeId(delivery.getStoreId().getStoreId())
                    .distance(delivery.getDistance())
                    .address(delivery.getAddress())
                    .callCheck(delivery.isCallCheck())
                    .deliveryId(delivery.getDeliveryId())
                    .requests(delivery.getRequests())
                    .pay(delivery.getPay())
                    .totalPay(delivery.getTotalPay())
                    .build();

            deliveryDtos.add(deliveryDto);
        }
        return deliveryDtos;

    }
    @Transactional
    public List<DeliveryDto> getProcesses(){

        List<Delivery> deliveryList = deliveryRepository.findAll();
        Iterator<Delivery> iter = deliveryList.iterator();


        List<DeliveryDto> deliveryDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Delivery delivery = iter.next();

            if(delivery.isCallCheck()==true)
                if(delivery.isDeliveryCheck()==false){
                    DeliveryDto deliveryDto = DeliveryDto.builder()
                            .deliveryId(delivery.getDeliveryId())
                            .uid(delivery.getUid().getUid())
                            .orderId(delivery.getOrderId().getOrderId())
                            .storeId(delivery.getStoreId().getStoreId())
                            .distance(delivery.getDistance())
                            .address(delivery.getAddress())
                            .callCheck(delivery.isCallCheck())
                            .deliveryId(delivery.getDeliveryId())
                            .requests(delivery.getRequests())
                            .pay(delivery.getPay())
                            .totalPay(delivery.getTotalPay())
                            .build();

                    deliveryDtos.add(deliveryDto);
                }

        }
        return deliveryDtos;

    }


    @Transactional
    public List<DeliveryDto> getCompletes(){

        List<Delivery> deliveryList = deliveryRepository.findAll();
        Iterator<Delivery> iter = deliveryList.iterator();


        List<DeliveryDto> deliveryDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Delivery delivery = iter.next();

            if(delivery.isCallCheck()==false || delivery.isDeliveryCheck()==false) continue;

            DeliveryDto deliveryDto = DeliveryDto.builder()
                    .deliveryId(delivery.getDeliveryId())
                    .uid(delivery.getUid().getUid())
                    .orderId(delivery.getOrderId().getOrderId())
                    .storeId(delivery.getStoreId().getStoreId())
                    .distance(delivery.getDistance())
                    .address(delivery.getAddress())
                    .callCheck(delivery.isCallCheck())
                    .deliveryId(delivery.getDeliveryId())
                    .requests(delivery.getRequests())
                    .pay(delivery.getPay())
                    .totalPay(delivery.getTotalPay())
                    .build();

            deliveryDtos.add(deliveryDto);
        }
        return deliveryDtos;

    }


    @Transactional
    public List<DeliveryDto> getDeliveries(){

        List<Delivery> deliveryList = deliveryRepository.findAll();
        Iterator<Delivery> iter = deliveryList.iterator();


        List<DeliveryDto> deliveryDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Delivery delivery = iter.next();
            DeliveryDto deliveryDto = DeliveryDto.builder()
                    .deliveryId(delivery.getDeliveryId())
                    .uid(delivery.getUid().getUid())
                    .orderId(delivery.getOrderId().getOrderId())
                    .storeId(delivery.getStoreId().getStoreId())
                    .distance(delivery.getDistance())
                    .address(delivery.getAddress())
                    .callCheck(delivery.isCallCheck())
                    .deliveryId(delivery.getDeliveryId())
                    .requests(delivery.getRequests())
                    .pay(delivery.getPay())
                    .totalPay(delivery.getTotalPay())
                    .build();

            deliveryDtos.add(deliveryDto);
        }
        return deliveryDtos;
    }


}
