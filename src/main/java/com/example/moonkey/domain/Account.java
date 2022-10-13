package com.example.moonkey.domain;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String pw;
    private int phone;
    private int flag;

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

}
