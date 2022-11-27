package com.example.moonkey.controller;

import com.example.moonkey.domain.Party;
import com.example.moonkey.dto.DeliveryDto;
import com.example.moonkey.dto.PartyDisplayDto;
import com.example.moonkey.dto.PartyDto;
import com.example.moonkey.exception.NotFoundPartyException;
import com.example.moonkey.repository.PartyRepository;
import com.example.moonkey.service.PartyService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
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

    @GetMapping("/party/{uid}/list")
    public ResponseEntity<PartyDisplayDto> userPartyList(@PathVariable long uid){
        return ResponseEntity.ok(partyService.getUserParties(uid));
    }

    @GetMapping("/party/myPartyList")
    public ResponseEntity<List<PartyDisplayDto>> getMyParties(HttpServletRequest request){
        return ResponseEntity.ok(partyService.getMyParties());
    }

    @GetMapping("/party/activatesList")
    public ResponseEntity<List<PartyDisplayDto>> getActivates(HttpServletRequest request){
        return ResponseEntity.ok(partyService.getActivates());
    }

    @GetMapping("/party/list/{storeId}")
    public ResponseEntity<List<PartyDisplayDto>> storePartyList(
            @PathVariable @Valid long storeId){
        return ResponseEntity.ok(partyService.getParties(storeId));
    }

    @GetMapping("/party/{partyId}")
    public ResponseEntity<PartyDto> getParty(
            @PathVariable @Valid long partyId
    ){
        return ResponseEntity.ok(partyService.getParty(partyId));
    }

    @PostMapping("/party/reg/{storeId}/{uid}") // 어느 가게에 누가 등록할 건지 추가 해둘 것
    public ResponseEntity<PartyDto> register(
            @PathVariable @Valid long storeId, @Valid @RequestBody PartyDto partyDto,
            @PathVariable @Valid long uid
    ){
        return ResponseEntity.ok(partyService.register(storeId, uid, partyDto));
    }

    @PostMapping("/party/unreg/{partyId}")
    public ResponseEntity<?> unregister(
            @PathVariable @Valid Long partyId
    ){
        Party party =  partyRepository.findOneByPartyId(partyId)
                .orElseThrow(()->new NotFoundPartyException("Party not found"));

        partyService.unregister(partyId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/app/party/list"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/party/join/{partyId}/{uid}")
    public ResponseEntity<PartyDto> partyJoin(
            @Valid @PathVariable long partyId,
            @Valid @PathVariable long uid
    ){
        return ResponseEntity.ok(partyService.join(partyId, uid));
    }

    @PostMapping("/party/leave/{partyId}/{uid}")
    public ResponseEntity<?> partyLeave(
            @PathVariable @Valid long partyId,
            @PathVariable @Valid long uid
    ){
        Party party =  partyRepository.findOneByPartyId(partyId)
                .orElseThrow(()->new NotFoundPartyException("Party not found"));

        partyService.leave(partyId, uid);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/app/party/list"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
    @GetMapping("party/list/rec")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<PartyDisplayDto>> partyRecList(HttpServletRequest request){
        return ResponseEntity.ok(partyService.getRecParties());
    }

    @PatchMapping("/party/complete/{partyId}")
    public ResponseEntity<PartyDto> setCompleteParty(@PathVariable @Valid long partyId){
        return ResponseEntity.ok((partyService.setCompleteParty(partyId)));
    }

}
