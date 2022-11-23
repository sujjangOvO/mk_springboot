package com.example.moonkey.controller;

import com.example.moonkey.dto.DeliveryDto;
import com.example.moonkey.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @GetMapping("/delivery/requestList")
    public ResponseEntity<List<DeliveryDto>> getRequests(HttpServletRequest request){
        return ResponseEntity.ok(deliveryService.getRequests());
    }

    @GetMapping("/delivery/processList")
    public ResponseEntity<List<DeliveryDto>> getProcesses(HttpServletRequest request){
        return ResponseEntity.ok(deliveryService.getProcesses());
    }

    @GetMapping("/delivery/completeList")
    public ResponseEntity<List<DeliveryDto>> getCompletes(HttpServletRequest request){
        return ResponseEntity.ok(deliveryService.getCompletes());
    }

    @PostMapping("/delivery/reg")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<DeliveryDto> register(
            @Valid @RequestBody DeliveryDto deliveryDto
    ){
        return ResponseEntity.ok(deliveryService.register(deliveryDto));
    }

    @GetMapping("/delivery/list/{uid}")
    public ResponseEntity<List<DeliveryDto>> getMyDeliveries(@PathVariable @Valid long uid){
        return ResponseEntity.ok(deliveryService.getMyDeliveries(uid));
    }

    @PatchMapping("/delivery/deliveryCheck/{deliveryId}")
    public ResponseEntity<DeliveryDto> setDeliveryCheck(@PathVariable @Valid long deliveryId){
        return ResponseEntity.ok((deliveryService.setDeliveryCheck(deliveryId)));
    }



}
