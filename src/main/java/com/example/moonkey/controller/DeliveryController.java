package com.example.moonkey.controller;

import com.example.moonkey.dto.DeliveryDto;
import com.example.moonkey.service.DeliveryService;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/app")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }

    @GetMapping("/delivery/list")
    public ResponseEntity<List<DeliveryDto>> getDeliveries(HttpServletRequest request){
        return ResponseEntity.ok(deliveryService.getDeliveries());
    }


}
