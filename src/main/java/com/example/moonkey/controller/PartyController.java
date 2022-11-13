package com.example.moonkey.controller;

import com.example.moonkey.domain.Party;
import com.example.moonkey.dto.PartyDisplayDto;
import com.example.moonkey.dto.PartyDto;
import com.example.moonkey.exception.NotFoundPartyException;
import com.example.moonkey.repository.PartyRepository;
import com.example.moonkey.service.PartyService;
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
public class PartyController {
    private final PartyService partyService;
    private final PartyRepository partyRepository;

    public PartyController(PartyService partyService, PartyRepository partyRepository){
        this.partyService = partyService;
        this.partyRepository = partyRepository;
    }

    @GetMapping("/party/list")
    public ResponseEntity<List<PartyDisplayDto>> partyList(HttpServletRequest request){
        return ResponseEntity.ok(partyService.getParties());
    }

    @PostMapping("/party/reg")
    public ResponseEntity<PartyDto> partyReg(
            @Valid @RequestBody PartyDto partyDto
    ){
        return ResponseEntity.ok(partyService.register(partyDto));
    }

    @PostMapping("/party/join")
    public ResponseEntity<PartyDto> partyJoin(
            @Valid @RequestBody int partyId
    ){
        return ResponseEntity.ok(partyService.join(partyId));
    }

    @PostMapping("/party/unreg")
    public String partyUnreg(
            @RequestBody @Valid int partyId
    ){
        Optional<Party> partyDto =  partyRepository.findOneByPartyId(partyId);

        if(partyDto == null){
            throw new NotFoundPartyException("Party not found");
        }

        partyService.unregister(partyId);

        return "/app/party/list";
    }

    @PostMapping("/party/leave")
    public ResponseEntity<PartyDto> partyLeave(
            @RequestBody @Valid long partyId,
            @RequestBody @Valid long uid
    ){
        return null;
    }

}
