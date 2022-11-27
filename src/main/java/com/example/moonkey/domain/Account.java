package com.example.moonkey.domain;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql="UPDATE account SET deleted = true WHERE uid = ?")
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;

    @Column(unique = true)
    private String id;

    @NotNull
    private String password;

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


    @ManyToMany
    @JoinTable(
            name = "account_authority",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name="authorityName"))
    private Set<Authority> authorities;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="account_store",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name="storeId")
    )
    private Set<Store> stores;

    @ManyToOne
    @JoinColumn(name = "partyId")
    private Party partyId;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}
