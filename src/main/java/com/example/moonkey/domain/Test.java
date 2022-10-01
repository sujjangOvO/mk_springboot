package com.example.moonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Test {
    private String name;

    @Id
    private long id;

    public String getName() {
        return name;
    }
}
