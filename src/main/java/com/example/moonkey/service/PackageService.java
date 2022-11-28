package com.example.moonkey.service;

import com.example.moonkey.domain.*;
import com.example.moonkey.domain.Package;
import com.example.moonkey.domain.Party;
import com.example.moonkey.dto.*;

import com.example.moonkey.exception.NotFoundMenuException;
import com.example.moonkey.exception.NotFoundOrderException;
import com.example.moonkey.exception.NotFoundPackageException;
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
    private final MenuRepository menuRepository;

    public PackageService(PackageRepository packageRepository, AccountRepository accountRepository
                        ,OrderRepository orderRepository, PartyRepository partyRepository, MenuRepository menuRepository){
        this.packageRepository = packageRepository;
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
        this.partyRepository = partyRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public PackageDto register(PackageDto packageDto, long orderId, long partyId){
        Party party =  partyRepository.findOneByPartyId(partyId)
                .orElseThrow(()->new NotFoundPartyException("Party not found"));

        Orders orders = orderRepository.findOneByOrderId(orderId)
                .orElseThrow(()->new NotFoundOrderException("Order not found"));

        List<String> productList = new ArrayList<>(Collections.emptyList());
        List<Orders> ordersList = new ArrayList<>(Collections.emptyList());
        // List<Orders> ordersList = packageDto.getOrders(packageDto.getOrderId());
        ordersList.add(orders);
        int amount = 0;

        Iterator<Orders> iterator = ordersList.iterator();
        while(iterator.hasNext()){
            Orders orders1 = iterator.next();
            Menu menu = menuRepository.findMenuByMenuId(orders1.getMenuId().getMenuId())
                    .orElseThrow(()->new NotFoundMenuException("Menu not found"));
            productList.add(menu.getMenuName());
            amount += menu.getPrice();
        }

        System.out.println(productList);

        Package aPackage = Package.builder()
                .packageId(packageDto.getPackageId())
                .product(productList)
                .amount(amount)
                .orderId(ordersList)
                .partyId(party)
                .address(party.getAddr()) // party로 부터 addr 받아오도록 변경
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
            PackageDto packageDto = PackageDto.from(aPackage);
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
                PackageDto packageDto = PackageDto.from(aPackage);
                packageDtoList.add(packageDto);
            }
        }
        return packageDtoList;

    }

    @Transactional
    public PackageDto setCompletePackage(long packageId){

        Package aPackage = packageRepository.findOneByPackageId(packageId)
                .orElseThrow(()->new NotFoundPackageException("Package not found"));

        aPackage.setPackageActivatedFalse(aPackage);
        packageRepository.save(aPackage);

        return PackageDto.from(aPackage);
    }

}
