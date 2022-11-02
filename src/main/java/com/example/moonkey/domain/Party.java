package com.example.moonkey.domain;

import org.hibernate.criterion.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int partyId;

    /*
    @OneToMany(mappedBy = "party_partyId")
    private List<Account> members = new ArrayList<>(); 오류*/

    @ManyToOne
    @JoinColumn(name = "store_storeId")
    private Store storeId;

    @OneToOne
    @JoinColumn(name = "orders_orderId")
    private Orders orderId;

    @NotNull
    private String partyTitle;
}
