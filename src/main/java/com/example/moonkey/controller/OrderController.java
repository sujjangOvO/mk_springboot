package com.example.moonkey.controller;

import com.example.moonkey.domain.Orders;
import com.example.moonkey.dto.OrderDto;
import com.example.moonkey.dto.StoreDisplayDto;
import com.example.moonkey.repository.OrderRepository;
import com.example.moonkey.service.OrderService;


import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/app")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/order/list")
    public ResponseEntity<List<OrderDto>> getStores(HttpServletRequest request){
        return ResponseEntity.ok(orderService.getOrders());
    }

    @PostMapping("/order/reg")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<OrderDto> register(
            @Valid @RequestBody OrderDto orderDto
    ) {
        return ResponseEntity.ok(orderService.register(orderDto));
    }

}
