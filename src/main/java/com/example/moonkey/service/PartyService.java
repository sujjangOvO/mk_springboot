package com.example.moonkey.service;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Party;
import com.example.moonkey.domain.Store;
import com.example.moonkey.dto.PartyDisplayDto;
import com.example.moonkey.dto.PartyDto;
import com.example.moonkey.dto.StatsDto;
import com.example.moonkey.exception.*;
import com.example.moonkey.repository.AccountRepository;
import com.example.moonkey.repository.PartyRepository;
import com.example.moonkey.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PartyService {
    private final PartyRepository partyRepository;
    private final AccountRepository accountRepository;
    private final StoreRepository storeRepository;

    private final AccountService accountService;

    public PartyService(PartyRepository partyRepository, AccountRepository accountRepository, StoreRepository storeRepository, AccountService accountService){
        this.partyRepository = partyRepository;
        this.accountRepository = accountRepository;
        this.storeRepository = storeRepository;
        this.accountService = accountService;
    }

    @Transactional // 가게 정보 받아와야 함
    public PartyDto register(long storeId, long uid, PartyDto partyDto){ // uid는 파티장
        Store store = storeRepository.findStoreByStoreId(storeId)
                .orElseThrow(()->new NotFoundStoreException("Store not found"));

        Set<Account> members = new HashSet<>(Collections.emptyList());
        members.add(accountRepository.findAccountByUid(uid).orElseThrow(()-> new NotFoundMemberException("Member not found")));

        Party party = Party.builder()
                .storeId(store)
                .partyId(partyDto.getPartyId())
                .partyTitle(partyDto.getPartyTitle())
                .members(members)
                .build();

        return PartyDto.from(partyRepository.save(party));
    }

    @Transactional
    public void unregister(long partyId){
        Party party =  partyRepository.findOneByPartyId(partyId)
                .orElseThrow(()->new NotFoundPartyException("Party not found"));

        PartyDto partyDto = PartyDto.builder()
                .partyId(party.getPartyId())
                .partyTitle(party.getPartyTitle())
                .members(party.getUids())
                .build();

        partyRepository.deleteById(partyDto.getPartyId());
    }

    @Transactional
    public List<PartyDisplayDto> getRecParties(){

        List<Party> partyList = partyRepository.findAll();
        Iterator<Party> iter = partyList.iterator();

        List<PartyDisplayDto> partyDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Party party = iter.next();
            PartyDisplayDto partyDto = PartyDisplayDto.builder()
                    .partyId(party.getPartyId())
                    .partyTitle(party.getPartyTitle())
                    .members(party.getUids())
                    .build();
            partyDtos.add(partyDto);
        }

        //TODO 사용자 기준 정렬
        List<StatsDto> statsList = accountService.getMyUserStats(); // 카테고리별 사용자 주문량 및 순서
        int i =0;
        Iterator<StatsDto> statsIter = statsList.iterator();
        HashMap<String,Integer> ordering = new HashMap<>();

        while(statsIter.hasNext()){
            StatsDto statsDto = statsIter.next();
            ordering.put(statsDto.getCategory(),i++);
        }
        //Collections.sort(partyDtos,);

        return partyDtos;
    }

    @Transactional
    public List<PartyDisplayDto> getParties(){
        List<Party> partyList = partyRepository.findAll();
        //TODO deleted entity에 대한 접근은 어떻게 할 것인가?
        Iterator<Party> iter = partyList.iterator();

        List<PartyDisplayDto> partyDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext()) {
            Party party = iter.next();
            PartyDisplayDto partyDto = PartyDisplayDto.builder()
                    .partyId(party.getPartyId())
                    .partyTitle(party.getPartyTitle())
                    .members(party.getUids())
                    .build();
            partyDtos.add(partyDto);
        }

        return	partyDtos;
    }

    @Transactional
    public PartyDisplayDto getUserParties(long uid){
        Account user = accountRepository.findAccountByUid(uid).
                orElseThrow(()->new NotFoundMemberException("Member not found"));

        List<Party> partyList = partyRepository.findAll();
        Iterator<Party> iter = partyList.iterator();
        PartyDisplayDto partyDto = null;

        while(iter.hasNext()) {
            Party party = iter.next();
            Set<Account> members = party.getMembers();

            if(members.contains(user)){
                partyDto = PartyDisplayDto.builder()
                        .partyId(party.getPartyId())
                        .partyTitle(party.getPartyTitle())
                        .members(party.getUids())
                        .build();
            }
        }

        return	partyDto;
    }

    @Transactional
    public List<PartyDisplayDto> getParties(long storeId){
        List<Party> partyList = partyRepository.findAll();
        Iterator<Party> iter = partyList.iterator();

        List<PartyDisplayDto> partyDtos = new ArrayList<>(Collections.emptyList());

        while(iter.hasNext())
        {
            Party party = iter.next();

            if(party.getStoreId().getStoreId() != storeId) continue;

            PartyDisplayDto partyDto = PartyDisplayDto.builder()
                    .partyId(party.getPartyId())
                    .partyTitle(party.getPartyTitle())
                    .members(party.getUids())
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
    public PartyDto getParty(long partyId){
        return PartyDto.from(
                partyRepository.findOneByPartyId(partyId)
                        .orElseThrow(()->new NotFoundPartyException("Party not found")));
    }


    @Transactional
    public PartyDto join(long partyId, long uid){

        Party party =  partyRepository.findOneByPartyId(partyId)
                .orElseThrow(()->new NotFoundPartyException("Party not found"));

        Account account = accountRepository.findAccountByUid(uid)
                .orElseThrow(()->new NotFoundMemberException("Member not found"));


        Set<Account> members = party.getMembers();

        if(!members.contains(account)) members.add(account);
        else throw new DuplicateMemberInPartyException("Member already exits in party"); // 파티에 이미 있는 사용자인 경우 예외 처리

        party = Party.builder()
                .partyId(party.getPartyId())
                .partyTitle(party.getPartyTitle())
                .storeId(party.getStoreId())
                .members(members)
                .build();

        return PartyDto.from(party);
    }

    @Transactional
    public void leave(long partyId, long uid){

        Party party =  partyRepository.findOneByPartyId(partyId)
                .orElseThrow(()->new NotFoundPartyException("Party not found"));

        Account account = accountRepository.findAccountByUid(uid)
                .orElseThrow(()->new NotFoundMemberException("Member not found"));


        Set<Account> members = party.getMembers();
        if(!members.contains(account)){
            throw new NotIncludeMemberException("Not include member in party");
        }
        else{
            members.remove(account);
        }
        //TODO 마지막 사용자(파티장)이 호출할 경우 unregister 호출


        party = Party.builder()
                .partyId(party.getPartyId())
                .partyTitle(party.getPartyTitle())
                .storeId(party.getStoreId())
                .members(members)
                .build();
		partyRepository.save(party);
		if(members.isEmpty())
			unregister(partyId);
    }

}