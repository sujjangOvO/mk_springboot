package com.example.moonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    private int order_number; // FK로 수정
}
