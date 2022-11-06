package com.example.moonkey.domain;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "store")
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="storeId")
    private long storeId;

    /*
    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>(); 오류

    @OneToMany(mappedBy = "party_partyId")
    private List<Party> partyList;
    */

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "accountUid")
    private Account ownerId; // FK

    @NotNull
    private String address;

    @Column(nullable = true)
    private String description;


}
