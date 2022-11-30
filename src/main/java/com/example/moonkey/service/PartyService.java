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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class PartyService {
    private final PartyRepository partyRepository;
    private final AccountRepository accountRepository;
    private final StoreRepository storeRepository;

    private final AccountService accountService;

    @Transactional // 가게 정보 받아와야 함
    public PartyDto register(long storeId, long uid, PartyDto partyDto) { // uid는 파티장
        Store store = storeRepository.findStoreByStoreId(storeId)
                .orElseThrow(() -> new NotFoundStoreException("Store not found"));

        Set<Account> members = new HashSet<>(Collections.emptyList());
        members.add(accountRepository.findAccountByUid(uid).orElseThrow(() -> new NotFoundMemberException("Member not found")));

        Party party = Party.builder()
                .storeId(store)
                .partyId(partyDto.getPartyId())
                .partyTitle(partyDto.getPartyTitle())
                .members(members)
                .maxnum(partyDto.getMaxnum())
                .addr(partyDto.getAddr())
                .build();

        // 패키지 생성 호출

        return PartyDto.from(partyRepository.save(party));
    }

    @Transactional
    public void unregister(long partyId) {
        Party party = partyRepository.findOneByPartyId(partyId)
                .orElseThrow(() -> new NotFoundPartyException("Party not found"));

        PartyDto partyDto = PartyDto.from(party);

        partyRepository.deleteById(partyDto.getPartyId());
    }

    @Transactional
    public List<PartyDisplayDto> getRecParties() {
        List<Party> partyList = partyRepository.findAll();

        List<PartyDisplayDto> partyDtos = new ArrayList<>(partyList.stream()
                .map(PartyDisplayDto::from)
                .toList());

        //TODO 사용자 기준 정렬
        List<StatsDto> statsList = accountService.getMyUserStats(); // 카테고리별 사용자 주문량 및 순서

        AtomicInteger i = new AtomicInteger(statsList.size());
        HashMap<String, Integer> ordering = statsList.stream()
                .collect(HashMap::new, (m, v) -> m.put(v.getCategory(), i.getAndDecrement()), HashMap::putAll);

        //Collections.sort(partyDtos,);
        partyDtos.sort(Comparator.comparingInt(item -> ordering.get(item.getCategory())));

        return partyDtos;
    }

    @Transactional
    public List<PartyDisplayDto> getParties() {
        List<Party> partyList = partyRepository.findAll();

        return partyList.stream()
                .map(PartyDisplayDto::from)
                .toList();
    }

    @Transactional
    public PartyDisplayDto getUserParties(long uid) {
        Account user = accountRepository.findAccountByUid(uid).
                orElseThrow(() -> new NotFoundMemberException("Member not found"));

        List<Party> partyList = partyRepository.findAll();
        return partyList.stream()
                .filter(party -> party.getMembers().contains(user))
                .map(PartyDisplayDto::from)
                .findFirst()
                .orElseThrow(() -> new NotFoundPartyException("Party not found"));
    }

    @Transactional(readOnly = true)
    public List<PartyDisplayDto> getParties(long storeId) {
        List<Party> partyList = partyRepository.findAll();
        return partyList.stream()
                .map(PartyDisplayDto::from)
                .toList();
    }

    @Transactional
    public PartyDto getParty(String partyTitle) {
        return PartyDto.from(partyRepository.findOneByPartyTitle(partyTitle)
                .orElseThrow(() -> new NotFoundPartyException("Party not found")));
    }

    @Transactional
    public PartyDto getParty(long partyId) {
        return PartyDto.from(
                partyRepository.findOneByPartyId(partyId)
                        .orElseThrow(() -> new NotFoundPartyException("Party not found")));
    }


    @Transactional
    public PartyDto join(long partyId, long uid) {

        Party party = partyRepository.findOneByPartyId(partyId)
                .orElseThrow(() -> new NotFoundPartyException("Party not found"));

        Account account = accountRepository.findAccountByUid(uid)
                .orElseThrow(() -> new NotFoundMemberException("Member not found"));


        Set<Account> members = party.getMembers();

        if (!members.contains(account)) {
            if (members.size() >= party.getMaxnum()) {
                throw new FullPartyNumException("Party member maxnum Full");
            }
            members.add(account);
        } else throw new DuplicateMemberInPartyException("Member already exits in party"); // 파티에 이미 있는 사용자인 경우 예외 처리

        party = Party.builder()
                .partyId(party.getPartyId())
                .partyTitle(party.getPartyTitle())
                .members(members)
                .storeId(party.getStoreId())
                .maxnum(party.getMaxnum())
                .addr(party.getAddr())
                .build();

        return PartyDto.from(partyRepository.save(party));
    }

    @Transactional
    public void leave(long partyId, long uid) {
        Party party = partyRepository.findOneByPartyId(partyId)
                .orElseThrow(() -> new NotFoundPartyException("Party not found"));

        Account account = accountRepository.findAccountByUid(uid)
                .orElseThrow(() -> new NotFoundMemberException("Member not found"));


        Set<Account> members = party.getMembers();
        if (!members.contains(account)) {
            throw new NotIncludeMemberException("Not include member in party");
        } else {
            members.remove(account);
        }
        //TODO 마지막 사용자(파티장)이 호출할 경우 unregister 호출
        //TODO Package에서 해당 인원이 추가한 order 지우기


        party = Party.builder()
                .partyId(party.getPartyId())
                .partyTitle(party.getPartyTitle())
                .storeId(party.getStoreId())
                .members(members)
                .addr(party.getAddr())
                .maxnum(party.getMaxnum())
                .build();

        partyRepository.save(party);
        if (members.isEmpty())
            unregister(partyId);
    }

    @Transactional
    public boolean setCompleteParty(long partyId) {
        Party party = partyRepository.findOneByPartyId(partyId)
                .orElseThrow(() -> new NotFoundPartyException("Party not found"));

        party.setPartyActivatedFalse(party);
        partyRepository.save(party);

        Set<Account> members = party.getMembers();
        Account account = accountService.getAccount();

        return members.contains(account);
    }

    @Transactional
    public List<PartyDisplayDto> getMyParties() { //TODO 사용자의 파티 완료 기록
        Account account = accountService.getAccount();

        List<Party> partyList = partyRepository.findAll();

        return partyList.stream()
                .filter(party -> party.getMembers().contains(account))
                .map(PartyDisplayDto::from)
                .toList();
    }

    @Transactional
    public List<PartyDisplayDto> getActivates() {
        List<Party> partyList = partyRepository.findAll();

        return partyList.stream()
                .filter(Party::isActivated)
                .map(PartyDisplayDto::from)
                .toList();
    }
}
