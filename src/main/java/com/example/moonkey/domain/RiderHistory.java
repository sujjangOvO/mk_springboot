package com.example.moonkey.domain;

import javax.persistence.*;

@Entity
@Table(name = "rider_history")
public class RiderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deliveyr_number;

    private String rider_id; // FK로 등록
}
