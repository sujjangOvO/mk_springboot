package com.example.moonkey.domain;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(unique = true)
    private String username;

    private String pw;
    private String phone;
    private int flag;

    @Column(name="activated")
    private boolean activated; // 활성화여부

    @ManyToMany // 중간테이블 자동 생성
    @JoinTable(name = "account_customer",
                joinColumns = @JoinColumn(name = "account_user_id"),
                inverseJoinColumns = @JoinColumn(name = "customer_customer_id"))
    private List<Customer> customers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "account_producer",
                joinColumns = @JoinColumn(name = "account_user_id"),
                inverseJoinColumns = @JoinColumn(name = "producer_producer_id"))
    private List<Producer> producers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "account_producer",
            joinColumns = @JoinColumn(name = "account_user_id"),
            inverseJoinColumns = @JoinColumn(name = "rider_rider_id"))
    private List<Rider> riders = new ArrayList<>();

    @ManyToMany // Account 객체와 authority 객체의 다대다 관계를 일대다, 다대일 관계의 조인 테이블로 정의하는 어노테이션
    @JoinTable(
            name = "account_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name="authority_name",referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}
