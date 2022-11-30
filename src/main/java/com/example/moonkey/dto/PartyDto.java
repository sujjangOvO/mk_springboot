package com.example.moonkey.dto;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Party;
import com.example.moonkey.exception.NotFoundMemberException;
import com.example.moonkey.repository.AccountRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartyDto {

    @Autowired
    @JsonIgnore
    private AccountRepository accountRepository;

    @NotNull
    private long partyId;

    @NotNull
    private String partyTitle;

    @NotNull
    @Builder.Default
    private Set<Long> members = new HashSet<>(); // uid 집합

    private long storeId;

    @NotNull
    private int maxnum;

    @Nullable
    private String category;

    @NotNull
    private String addr;


    public Set<Account> getAccounts(Set<Long> list){
        Set<Account> memberList = new HashSet<>();
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            long num = (long) iter.next();
            memberList.add(accountRepository.findAccountByUid((num)).orElseThrow(()->new NotFoundMemberException("Member not found")));
        }
        return memberList;
    }

    public static PartyDto from(Party party){
        return PartyDto.builder().
                partyId(party.getPartyId()).
                partyTitle(party.getPartyTitle()).
                members(party.getUids()).
                storeId(party.getStoreId().getStoreId()).
                maxnum(party.getMaxnum()).
                addr(party.getAddr()).
                category(party.getStoreId().getCategoryName().getCategoryName()).
                build();
    }
}
