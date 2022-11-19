package com.example.moonkey.dto;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Party;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyDisplayDto {

    @NotNull
    private long partyId;

    private String storeName;

    @NotNull
    private String partyTitle;

    @NotNull
    private Set<Long> members = new HashSet<>();

    public static PartyDisplayDto from(Party party){
        return PartyDisplayDto.builder().
                storeName(party.getStoreId().getName()).
                partyId(party.getPartyId()).
                partyTitle(party.getPartyTitle()).
                members(party.getUids()).
                build();
    }
}
