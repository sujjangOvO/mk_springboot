package com.example.moonkey.domain;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql="UPDATE account_authority SET deleted = true WHERE uid = ?")
@Table(name = "account_authority")
public class account_authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountAuthorityId;

    @ManyToOne
    @JoinColumn(name = "partyId")
    private Account uid;

    @ManyToOne
    @JoinColumn(name = "authorityName")
    private Authority Authority;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}
