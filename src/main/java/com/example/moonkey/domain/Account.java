package com.example.moonkey.domain;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;

    @Column(unique = true)
    private String id;

    @NotNull
    private String key;

    @NotNull
    private String phone;

    @Column(unique = true)
    @NotNull
    private String nickname;

    @NotNull
    private int flag; // 0 user, 1 owner, 2 rider

    @NotNull
    private String addr;

    @Column(name="activated")
    private boolean activated; // 활성화여부

    @ManyToMany // Account 객체와 authority 객체의 다대다 관계를 일대다, 다대일 관계의 조인 테이블로 정의하는 어노테이션
    @JoinTable(
            name = "account_authority",
            joinColumns = @JoinColumn(name = "account_uid"),
            inverseJoinColumns = @JoinColumn(name="authority_authority_name"))
    private Set<Authority> authorities;

    @ManyToOne
    @JoinColumn(name="store_storeId")
    private Store storeId;

    /*
    @OneToMany(mappedBy = "orders_orderId")
    private List<Orders> orderList = new ArrayList<>(); // 에러

    @OneToMany(mappedBy = "account")
    private List<Store> storeList = new ArrayList<>(); */

    @ManyToOne
    @JoinColumn(name = "party_partyId")
    private Party partyId;
}
