package com.example.moonkey.service;

import com.example.moonkey.domain.Delivery;
import com.example.moonkey.dto.DeliveryDto;
import com.example.moonkey.repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
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
                    .build();

            deliveryDtos.add(deliveryDto);
        }
        return deliveryDtos;

    }
}
