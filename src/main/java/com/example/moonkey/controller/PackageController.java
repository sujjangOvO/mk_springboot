package com.example.moonkey.controller;


import com.example.moonkey.domain.Package;
import com.example.moonkey.dto.OrderDto;
import com.example.moonkey.dto.PackageDto;
import com.example.moonkey.repository.PackageRepository;
import com.example.moonkey.service.PackageService;

import org.apache.coyote.Response;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/app")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService){
        this.packageService = packageService;
    }

    @GetMapping("/package/list")
    public ResponseEntity<List<PackageDto>> getPackages(HttpServletRequest request){
        return ResponseEntity.ok(packageService.getPackages());
    }

    @PostMapping("/package/reg/{orderId}/{partyId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PackageDto> register(
            @Valid @RequestBody PackageDto packageDto,
            @PathVariable("orderId") long orderId,
            @PathVariable("partyId") long partyId
    ){
        try{
            return ResponseEntity.ok(packageService.register(packageDto, orderId, partyId));
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Not Found");
        }
    }

}
