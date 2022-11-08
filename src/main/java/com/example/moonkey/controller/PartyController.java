package com.example.moonkey.controller;

import com.example.moonkey.dto.PartyDto;
import com.example.moonkey.service.PartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app")
public class PartyController {
    private final PartyService partyService;

    public PartyController(PartyService partyService){
        this.partyService = partyService;
    }

    @PostMapping("/party/list")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<PartyDto>> partyList(
            @Valid @RequestBody PartyDto partyDto
    ){
        return ResponseEntity.ok(
                partyService.getlist());
    }

    @PostMapping("/party/reg")
    public ResponseEntity<PartyDto> partyReg(
            @Valid @RequestBody PartyDto partyDto
    ){
        return ResponseEntity.ok(partyService.register(partyDto));
    }

}
