package com.example.moonkey.domain;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String pw;
    private int phone;
    private int flag;
}
