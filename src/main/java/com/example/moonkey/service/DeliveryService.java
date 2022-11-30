package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Delivery;
import com.example.moonkey.domain.Package;
import com.example.moonkey.dto.DeliveryDto;
import com.example.moonkey.exception.NotFoundDeliveryException;
import com.example.moonkey.repository.DeliveryRepository;
import com.example.moonkey.repository.OrderRepository;
import com.example.moonkey.repository.PackageRepository;
import com.example.moonkey.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final AccountService accountService;
    private final DeliveryRepository deliveryRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final PackageRepository packageRepository;

    @Transactional
    public DeliveryDto register(DeliveryDto deliveryDto) {
        Account account = accountService.getAccount();

        Package packages = packageRepository.findOneByPackageId(deliveryDto.getPackageId())
                .orElseThrow(() -> new RuntimeException(("Package not found")));


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

        return DeliveryDto.from(deliveryRepository.save(delivery));
    }

    @Transactional(readOnly = true)
    public List<DeliveryDto> getMyDeliveries(long uid) {
        List<Delivery> deliveryList = deliveryRepository.findAll();
        return deliveryList.stream()
                .filter(delivery -> delivery.getUid().getUid() == uid)
                .map(DeliveryDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DeliveryDto> getRequests() {
        List<Delivery> deliveryList = deliveryRepository.findAll();
        return deliveryList.stream()
                .filter(delivery -> !delivery.isCallCheck() && !delivery.isDeliveryCheck())
                .map(DeliveryDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DeliveryDto> getProcesses() {
        List<Delivery> deliveryList = deliveryRepository.findAll();
        return deliveryList.stream()
                .filter(Delivery::isCallCheck)
                .filter(delivery -> !delivery.isDeliveryCheck())
                .map(DeliveryDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DeliveryDto> getCompletes() {
        List<Delivery> deliveryList = deliveryRepository.findAll();
        return deliveryList.stream()
                .filter(Delivery::isCallCheck)
                .filter(Delivery::isDeliveryCheck)
                .map(DeliveryDto::from)
                .toList();
    }

    @Transactional
    public List<DeliveryDto> getDeliveries() {

        List<Delivery> deliveryList = deliveryRepository.findAll();
        return deliveryList.stream()
                .map(DeliveryDto::from)
                .toList();
    }

    @Transactional
    public DeliveryDto setDeliveryCheck(long deliveryId) {
        Delivery delivery = deliveryRepository.findOneByDeliveryId(deliveryId)
                .orElseThrow(() -> new NotFoundDeliveryException("Delivery not found"));

        delivery.setDeliveryCheck(true);
        deliveryRepository.save(delivery);

        return DeliveryDto.from(delivery);
    }

    @Transactional
    public DeliveryDto setCallCheck(long deliveryId) {
        Delivery delivery = deliveryRepository.findOneByDeliveryId(deliveryId)
                .orElseThrow(() -> new NotFoundDeliveryException("Delivery not found"));

        delivery.setCallCheck(true);
        deliveryRepository.save(delivery);
        return DeliveryDto.from(delivery);
    }

}
