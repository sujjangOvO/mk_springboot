package com.example.moonkey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql="UPDATE party SET deleted = true WHERE party_id = ?")
@Table(name = "party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="partyId")
    private long partyId;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store storeId;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "accountUid")
    @Builder.Default
    private Set<Account> members = new HashSet<>();


    @NotNull
    private String partyTitle;

    public void setStoreId(Store storeId) {
        this.storeId = storeId;
    }

    public Set<Long> getUids(){
        Set<Long> memberList = new HashSet<>(members.size());
        Iterator<Account> iter = members.iterator();
        while(iter.hasNext()){
            memberList.add(iter.next().getUid());
        }
        return memberList;
    }
    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}
