package com.example.moonkey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Producer {
    @Id
    private long producer_id;

    @Column(unique = true)
    private String id;

    @OneToMany(mappedBy = "producer_id")
    private List<Store> stores = new ArrayList<>();
}
