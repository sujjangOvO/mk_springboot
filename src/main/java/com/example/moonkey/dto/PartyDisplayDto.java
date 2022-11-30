package com.example.moonkey.dto;

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
    private String storeName;

    @NotNull
    private String partyTitle;

    @NotNull
    private int maxnum;

    @NotNull
    private String category;

    @NotNull
    private String addr;

    @NotNull
    @Builder.Default
    private Set<Long> members = new HashSet<>();

    public static PartyDisplayDto from(Party party) {
        return PartyDisplayDto.builder().
                storeName(party.getStoreId().getName()).
                partyId(party.getPartyId()).
                partyTitle(party.getPartyTitle()).
                members(party.getUids()).
                maxnum(party.getMaxnum()).
                addr(party.getAddr()).
                category(party.getStoreId().getCategoryName().getCategoryName()).
                build();
    }
}
