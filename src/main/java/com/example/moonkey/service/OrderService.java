package com.example.moonkey.service;

import com.example.moonkey.domain.*;
import com.example.moonkey.domain.Package;
import com.example.moonkey.dto.OrderDto;
import com.example.moonkey.dto.OrderDisplayDto;

import com.example.moonkey.exception.*;
import com.example.moonkey.repository.*;
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

    private final PartyRepository partyRepository;
    private final PackageRepository packageRepository;

    public OrderService(OrderRepository orderRepository, AccountRepository accountRepository
                        , MenuRepository menuRepository, StoreRepository storeRepository, PartyRepository partyRepository, PackageRepository packageRepository){
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
        this.partyRepository = partyRepository;
        this.packageRepository = packageRepository;
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

        Menu menu = menuRepository.findOneByMenuId(orderDto.getMenuId())
                .orElseThrow(()->new NotFoundMenuException("Menu not found"));

        Store store = storeRepository.findOneByStoreId(orderDto.getStoreId())
                .orElseThrow(()->new NotFoundStoreException("Store not found"));

        //현재 파티 찾아서 패키지 등록에 자동 등록
        Party party = getCurrentParty(account);

        LocalDateTime now = LocalDateTime.now();

        Orders order = Orders.builder()
                .orderId(orderDto.getOrderId())
                .number(orderDto.getNumber())
                .orderDate(new Timestamp(Timestamp.valueOf(now).getTime()))
                .menuId(menu)
                .storeId(store)
                .uid(account)
                .build();

        OrderDto result = OrderDto.from(orderRepository.save(order));
        Orders orders = orderRepository.findOneByOrderId(order.getOrderId())
                .orElseThrow(()->new NotFoundOrderException("Orders not found"));
        if(party!=null){
            add(party,orders);
        }

        return result;
    }

    public void add(Party party, Orders order){
        Package pack = packageRepository.findOneByPartyId(party)
                .orElseThrow(()->new RuntimeException("Package not found"));
        List<Orders> ordersList = pack.getOrderId();
        List<String> products = pack.getProduct();
        int new_price = order.getNumber()*order.getMenuId().getPrice();

        ordersList.add(order);
        products.add(order.getMenuId().getMenuName());

        Package newpack = Package.builder().
                packageId(pack.getPackageId()).
                orderId(ordersList).
                partyId(pack.getPartyId()).
                storeId(pack.getStoreId()).
                address(pack.getAddress()).
                amount(pack.getAmount()+new_price).
                product(products).
                build();

        packageRepository.save(newpack);
    }


    public void sub(Party party, long orderId){
        Package pack = packageRepository.findOneByPartyId(party)
                .orElseThrow(()->new RuntimeException("Package not found"));
        List<Orders> ordersList = pack.getOrderId();

        Orders order = orderRepository.findOneByOrderId(orderId)
                .orElseThrow(()->new NotFoundOrderException("Order not found"));

        ordersList.remove(order);

        pack.setOrderId(ordersList);

        packageRepository.save(pack);
    }

    public Party getCurrentParty(Account account){
        List<Party> partyList = partyRepository.findAll();
        Iterator<Party> iter = partyList.iterator();
        Party party = null;
        while(iter.hasNext()){
            Party temp = iter.next();
            if(temp.isActivated()){
                if(temp.getMembers().contains(account));
                    party = temp;
                    break;
            }
        }

        return party;
    }
    @Transactional
    public void unregister(long orderId){
        Account account =
                SecurityUtil.getCurrentUsername()
                        .flatMap(accountRepository::findOneWithAuthoritiesById)
                        .orElseThrow(()->new NotFoundMemberException("Member not found"));

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

        Party party = getCurrentParty(account);
        if(party!=null){
            sub(party,orderId);
        }

        orderRepository.deleteById(orderDto.getOrderId());
    }

    @Transactional
    public OrderDisplayDto getOrdersByOrderId(long orderId){
        Orders orders = orderRepository.findOneByOrderId(orderId)
                .orElseThrow(()->new NotFoundOrderException("Order not found"));

        return OrderDisplayDto.from(orders);
    }
}
