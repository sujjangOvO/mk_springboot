package com.example.moonkey.dto;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Party;
import com.example.moonkey.repository.AccountRepository;
import lombok.*;

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

    private AccountRepository accountRepository;

    @NotNull
    private long partyId;

    @NotNull
    private String partyTitle;

    @NotNull
    private Set<Long> members = new HashSet<>(); // uid 집합

    private long storeId;


    public Set<Account> getAccounts(Set<Long> list){
        Set<Account> memberList = new HashSet<>();
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            long num = (long) iter.next();
            System.out.println(accountRepository.findAccountById((num)));
            memberList.add(accountRepository.findAccountById((num)));
        }
        return memberList;
    }

    public static PartyDto from(Party party){
        return PartyDto.builder().
                partyId(party.getPartyId()).
                partyTitle(party.getPartyTitle()).
                members(party.getUids()).
                storeId(party.getStoreId().getStoreId()).
                build();
    }
}
