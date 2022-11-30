package com.example.moonkey.controller;

import com.example.moonkey.domain.Account;
import com.example.moonkey.dto.OrderDisplayDto;
import com.example.moonkey.dto.OrderDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.service.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/app")
public class OrderController {

    private final OrderService orderService;
    private final AccountRepository accountRepository;

    public OrderController(OrderService orderService, AccountRepository accountRepository) {
        this.orderService = orderService;
        this.accountRepository = accountRepository;
    }


    @GetMapping("/order/list")
    public ResponseEntity<List<OrderDisplayDto>> getStores(HttpServletRequest request) {
        return ResponseEntity.ok(orderService.getOrderList());
    }


    @GetMapping("/order/{orderId}/get")  //  OrderId로 Order 정보 찾기
    public ResponseEntity<OrderDisplayDto> getOrdersByOrderId(@PathVariable long orderId) {
        return ResponseEntity.ok(orderService.getOrdersByOrderId(orderId));
    }

    @GetMapping("/order/list/{uid}")
    public ResponseEntity<List<OrderDisplayDto>> getOrderListByUid(@PathVariable long uid) {   // 사용자 주문내역
        Account account = accountRepository.findAccountByUid(uid).orElseThrow(() -> new NotFoundMemberException("Member not found"));
        return ResponseEntity.ok(orderService.getOrderListByUid(uid));
    }

    @PostMapping("/order/reg")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<OrderDto> register(
            @Valid @RequestBody OrderDto orderDto
    ) {
        return ResponseEntity.ok(orderService.register(orderDto));
    }

    @GetMapping("/order/reg")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<OrderDto> registerByGet(
            @Valid @RequestBody OrderDto orderDto
    ) {
        return ResponseEntity.ok(orderService.register(orderDto));
    }

    @PostMapping("/order/unreg/{orderId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> register(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.unregister(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/app/order/list"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

}
