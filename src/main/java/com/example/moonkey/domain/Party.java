package com.example.moonkey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="partyId")
    private long partyId;

    @OneToMany
    @JoinColumn(name = "accountUid")
    private Set<Account> members = new HashSet<>();

    @NotNull
    private String partyTitle;
}
