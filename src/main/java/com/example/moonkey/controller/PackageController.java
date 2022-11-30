package com.example.moonkey.controller;


import com.example.moonkey.dto.PackageDto;
import com.example.moonkey.service.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/app")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping("/package/list")
    public ResponseEntity<List<PackageDto>> getPackages(HttpServletRequest request) {
        return ResponseEntity.ok(packageService.getPackages());
    }

    @GetMapping("/package/{storeId}/list")
    public ResponseEntity<List<PackageDto>> getPackages(@PathVariable long storeId) {
        return ResponseEntity.ok(packageService.getPackagesByStoreId(storeId));
    }

    @PostMapping("/package/reg/{orderId}/{partyId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PackageDto> register(
            @RequestBody PackageDto packageDto,
            @PathVariable("orderId") long orderId,
            @PathVariable("partyId") long partyId
    ) {
        try {
            return ResponseEntity.ok(packageService.register(packageDto, orderId, partyId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/package/complete/{packageId}")
    public ResponseEntity<PackageDto> setCompletePackage(@PathVariable @Valid long packageId) {
        return ResponseEntity.ok((packageService.setCompletePackage(packageId)));
    }

}
