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

    @NotNull
    private String partyTitle;

    @NotNull
    private Set<Long> members = new HashSet<>();

    public static PartyDto from(Party party){
        return PartyDto.builder().
                partyId(party.getPartyId()).
                partyTitle(party.getPartyTitle()).
                members(party.getUids()).
                build();
    }
}
