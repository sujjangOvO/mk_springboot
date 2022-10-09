package com.example.moonkey.domain;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    private String customer_id;

    private String address;
    private String nickname;
}
