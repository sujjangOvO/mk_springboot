package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Menu;
import com.example.moonkey.domain.Party;
import com.example.moonkey.dto.MenuDto;
import com.example.moonkey.dto.PartyDto;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.exception.NotFoundPartyException;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.PartyRepository;
import com.example.moonkey.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
        /*
        Account account = SecurityUtil.getCurrentUsername()
                .flatMap(accountRepository::findOneWithAuthoritiesById)
                .orElseThrow(()->new NotFoundMemberException("Member not found")); */


        Party party = Party.builder()
                .partyId(partyDto.getPartyId())
                .partyTitle(partyDto.getPartyTitle())
                .build();

        return PartyDto.from(party);
    }

    @Transactional
    public List<PartyDto> getlist(){
        List<Party> partyList = partyRepository.findAll();
        Iterator<Party> iter = partyList.iterator();

        List<PartyDto> partyDtos = Collections.emptyList();

        while(iter.hasNext())
        {
            Party party = iter.next();
            PartyDto partyDto = PartyDto.builder()
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
}