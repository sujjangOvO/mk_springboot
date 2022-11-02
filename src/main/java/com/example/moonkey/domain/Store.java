package com.example.moonkey.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeId;

    /*
    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>(); 오류

    @OneToMany(mappedBy = "party_partyId")
    private List<Party> partyList;
    */

    @NotNull
    private String store_name;

    @ManyToOne
    @JoinColumn(name = "account_uid")
    private Account ownerId; // FK

    @NotNull
    private String address;

    @Column(nullable = true)
    private String description;




}
