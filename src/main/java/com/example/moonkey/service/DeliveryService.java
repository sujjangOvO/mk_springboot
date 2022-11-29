package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Delivery;
import com.example.moonkey.domain.Package;
import com.example.moonkey.dto.DeliveryDto;
import com.example.moonkey.exception.NotFoundDeliveryException;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundStoreException;
import com.example.moonkey.repository.*;
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

    private final PackageRepository packageRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, AccountRepository accountRepository
                            , StoreRepository storeRepository, OrderRepository orderRepository, PackageRepository packageRepository){
        this.deliveryRepository = deliveryRepository;
        this.accountRepository = accountRepository;
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
        this.packageRepository = packageRepository;
    }

    @Transactional
    public DeliveryDto register(DeliveryDto deliveryDto){
        Account account =
                SecurityUtil.getCurrentUsername()
                        .flatMap(accountRepository::findOneWithAuthoritiesById)
                        .orElseThrow(()->new NotFoundMemberException("Member not found"));

        Package packages =  packageRepository.findOneByPackageId(deliveryDto.getPackageId())
                .orElseThrow(()-> new RuntimeException(("Package not found")));


        Delivery delivery = Delivery.builder()
                .uid(account)
                .packageId(packages)
                .storeId(packages.getStoreId())
                .distance(deliveryDto.getDistance())
                .address(deliveryDto.getAddress())
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

            DeliveryDto deliveryDto = DeliveryDto.from(delivery);
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
            if(delivery.isCallCheck() == false && delivery.isDeliveryCheck() == false) {
                DeliveryDto deliveryDto = DeliveryDto.from(delivery);
                deliveryDtos.add(deliveryDto);
            }
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
                    DeliveryDto deliveryDto = DeliveryDto.from(delivery);
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

            DeliveryDto deliveryDto = DeliveryDto.from(delivery);
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
            DeliveryDto deliveryDto = DeliveryDto.from(delivery);
            deliveryDtos.add(deliveryDto);
        }
        return deliveryDtos;
    }

    @Transactional
    public DeliveryDto setDeliveryCheck(long deliveryId){

        Delivery delivery = deliveryRepository.findOneByDeliveryId(deliveryId)
                .orElseThrow(()->new NotFoundDeliveryException("Delivery not found"));

        delivery.setDeliveryCheck(true);
        deliveryRepository.save(delivery);

        return DeliveryDto.from(delivery);
    }

    @Transactional
    public DeliveryDto setCallCheck(long deliveryId){

        Delivery delivery = deliveryRepository.findOneByDeliveryId(deliveryId)
                .orElseThrow(()->new NotFoundDeliveryException("Delivery not found"));

        delivery.setCallCheck(true);
        deliveryRepository.save(delivery);

        return DeliveryDto.from(delivery);
    }

}
