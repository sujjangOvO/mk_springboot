package com.example.moonkey.domain;

import org.hibernate.criterion.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="partyId")
    private int partyId;

    @OneToMany
    @JoinColumn(name = "accountUid")
    private Set<Account> members = new HashSet<>();

    /*
    @OneToMany(mappedBy = "party_partyId")
    private List<Account> members = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store storeId;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Orders orderId;
    */

    @NotNull
    private String partyTitle;
}
