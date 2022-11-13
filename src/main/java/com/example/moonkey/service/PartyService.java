package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Menu;
import com.example.moonkey.domain.Party;
import com.example.moonkey.dto.MenuDto;
import com.example.moonkey.dto.PartyDisplayDto;
import com.example.moonkey.dto.PartyDto;
import com.example.moonkey.dto.StoreDisplayDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundPartyException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.PartyRepository;
import com.example.moonkey.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import java.util.*;

@Service
public class PartyService {
    private final PartyRepository partyRepository;
    private AccountRepository accountRepository;


    public PartyService(PartyRepository partyRepository, AccountRepository accountRepository){
        this.partyRepository = partyRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public PartyDto register(PartyDto partyDto){
        Party party = Party.builder()
                .partyId(partyDto.getPartyId())
                .partyTitle(partyDto.getPartyTitle())
                .build();

        return PartyDto.from(party);
    }

    @Transactional
    public void unregister(int partyId){
        Party party = partyRepository.findOneByPartyId(partyId)
                .orElseThrow(()->new NotFoundPartyException("Party not found"));

        PartyDto partyDto = PartyDto.builder()
                .partyId(party.getPartyId())
                .partyTitle(party.getPartyTitle())
                .build();

        partyRepository.deleteById(partyDto.getPartyId());
    }

    @Transactional
    public List<PartyDisplayDto> getParties(){
        List<Party> partyList = partyRepository.findAll();
        Iterator<Party> iter = partyList.iterator();

        List<PartyDisplayDto> partyDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Party party = iter.next();
            PartyDisplayDto partyDto = PartyDisplayDto.builder()
                    .partyId(party.getPartyId())
                    .partyTitle(party.getPartyTitle())
                    .build();
            partyDtos.add(partyDto);
        }

        return	partyDtos;
    }

    @Transactional
    public PartyDto getParty(String partyTitle){
        return PartyDto.from(
                partyRepository.findOneByPartyTitle(partyTitle)
                        .orElseThrow(()->new NotFoundPartyException("Party not found")));
    }

    @Transactional
    public PartyDto join(int partyId){
        Account account = SecurityUtil.getCurrentUsername()
                .flatMap(accountRepository::findOneWithAuthoritiesById)
                .orElseThrow(()->new NotFoundMemberException("Member not found"));

        Party party = partyRepository.findOneByPartyId(partyId)
                .orElseThrow(()->new NotFoundPartyException("Party not found"));

        Set<Account> members = party.getMembers();
        members.add(account);

        party = Party.builder()
                .partyId(party.getPartyId())
                .partyTitle(party.getPartyTitle())
                .members(members)
                .build();

        return PartyDto.from(party);
    }


}