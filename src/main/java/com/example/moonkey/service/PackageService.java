package com.example.moonkey.service;

import com.example.moonkey.domain.*;
import com.example.moonkey.domain.Package;
import com.example.moonkey.domain.Party;
import com.example.moonkey.dto.*;

import com.example.moonkey.exception.NotFoundOrderException;
import com.example.moonkey.exception.NotFoundPartyException;
import com.example.moonkey.repository.*;
import com.example.moonkey.repository.PackageRepository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PackageService {

    private final PackageRepository packageRepository;
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    private final PartyRepository partyRepository;

    public PackageService(PackageRepository packageRepository, AccountRepository accountRepository
                        ,OrderRepository orderRepository, PartyRepository partyRepository){
        this.packageRepository = packageRepository;
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
        this.partyRepository = partyRepository;
    }

    @Transactional
    public PackageDto register(PackageDto packageDto, long orderId, long partyId){
        Party party =  partyRepository.findOneByPartyId(partyId)
                .orElseThrow(()->new NotFoundPartyException("Party not found"));

        Orders orders = orderRepository.findOneByOrderId(orderId)
                .orElseThrow(()->new NotFoundOrderException("Order notrder found"));

        List<Orders> ordersList = packageDto.getOrders(packageDto.getOrderId());
        ordersList.add(orders);

        Package aPackage = Package.builder()
                .packageId(packageDto.getPackageId())
                .product(packageDto.getProduct())
                .address(packageDto.getAddress())
                .amount(packageDto.getAmount())
                .orderId(ordersList)
                .partyId(party)
                .build();


        return packageDto.from(packageRepository.save(aPackage));
    }

    @Transactional
    public List<PackageDto> getPackages(){
        List<Package> packageList = packageRepository.findAll();
        Iterator<Package> iter = packageList.iterator();


        List<PackageDto> packageDtoList = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Package aPackage = iter.next();

            PackageDto packageDto = PackageDto.builder()
                    .packageId(aPackage.getPackageId())
                    .product(aPackage.getProduct())
                    .address(aPackage.getAddress())
                    .amount(aPackage.getAmount())
                    .orderId(aPackage.getOrderIds())
                    .partyId(aPackage.getPartyId().getPartyId())
                    .build();

            packageDtoList.add(packageDto);
        }
        return packageDtoList;

    }


    @Transactional
    public List<PackageDto> getPackagesByStoreId(long storeId){
        List<Package> packageList = packageRepository.findAll();
        Iterator<Package> iter = packageList.iterator();

        List<PackageDto> packageDtoList = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Package aPackage = iter.next();

            if(aPackage.getPartyId().getStoreId().getStoreId() == storeId){
                PackageDto packageDto = PackageDto.builder()
                        .packageId(aPackage.getPackageId())
                        .product(aPackage.getProduct())
                        .address(aPackage.getAddress())
                        .amount(aPackage.getAmount())
                        .orderId(aPackage.getOrderIds())
                        .partyId(aPackage.getPartyId().getPartyId())
                        .build();
                packageDtoList.add(packageDto);
            }
        }
        return packageDtoList;

    }
}
