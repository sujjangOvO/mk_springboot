package com.example.moonkey.service;

import com.example.moonkey.domain.Menu;
import com.example.moonkey.domain.Orders;
import com.example.moonkey.domain.Package;
import com.example.moonkey.domain.Party;
import com.example.moonkey.dto.PackageDto;
import com.example.moonkey.exception.NotFoundMenuException;
import com.example.moonkey.exception.NotFoundOrderException;
import com.example.moonkey.exception.NotFoundPackageException;
import com.example.moonkey.exception.NotFoundPartyException;
import com.example.moonkey.repository.MenuRepository;
import com.example.moonkey.repository.OrderRepository;
import com.example.moonkey.repository.PackageRepository;
import com.example.moonkey.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PackageService {

    private final PackageRepository packageRepository;
    private final OrderRepository orderRepository;
    private final PartyRepository partyRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public PackageDto register(PackageDto packageDto, long orderId, long partyId) {
        Party party = partyRepository.findOneByPartyId(partyId)
                .orElseThrow(() -> new NotFoundPartyException("Party not found"));

        Orders orders = orderRepository.findOneByOrderId(orderId)
                .orElseThrow(() -> new NotFoundOrderException("Order not found"));

        Package packs = packageRepository.findOneByPartyId(party);


        List<String> productList;
        List<Orders> ordersList;
        if (packs != null) {
//            productList = packs.getProduct();
            productList = new ArrayList<>(Collections.emptyList());
            ordersList = packs.getOrderId();
        } else {
            productList = new ArrayList<>(Collections.emptyList());
            ordersList = new ArrayList<>(Collections.emptyList());
        }

        ordersList.add(orders);
        int amount = 0;

        for (Orders orders1 : ordersList) {
            Menu menu = menuRepository.findMenuByMenuId(orders1.getMenuId().getMenuId())
                    .orElseThrow(() -> new NotFoundMenuException("Menu not found"));
            productList.add(menu.getMenuName());
            amount += menu.getPrice();
        }
        Package aPackage;
        PackageDto result;
        if (packs == null) {
            aPackage = Package.builder()
                    .storeId(party.getStoreId())
                    .product(productList)
                    .amount(amount)
                    .orderId(ordersList)
                    .partyId(party)
                    .address(party.getAddr()) // party로 부터 addr 받아오도록 변경
                    .build();
            result = PackageDto.from(packageRepository.save(aPackage));
        } else {
            packs.setOrderId(ordersList);
            result = PackageDto.from(packageRepository.save(packs));

        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<PackageDto> getPackages() {
        List<Package> packageList = packageRepository.findAll();
        return packageList.stream()
                .map(PackageDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<PackageDto> getPackagesByStoreId(long storeId) {
        List<Package> packageList = packageRepository.findAll();
        return packageList.stream()
                .filter(aPackage -> aPackage.getPartyId().getStoreId().getStoreId() == storeId)
                .map(PackageDto::from)
                .toList();
    }

    @Transactional
    public PackageDto setCompletePackage(long packageId) {
        Package aPackage = packageRepository.findOneByPackageId(packageId)
                .orElseThrow(() -> new NotFoundPackageException("Package not found"));
        aPackage.setPackageActivatedFalse(aPackage);
        packageRepository.save(aPackage);
        return PackageDto.from(aPackage);
    }
}
