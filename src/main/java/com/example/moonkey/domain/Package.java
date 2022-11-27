package com.example.moonkey.domain;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql="UPDATE package SET deleted = true WHERE package_id = ?")
@Entity
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageId")
    private long packageId;

    @OneToMany(mappedBy= "orderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orderId; // FK

    @OneToOne
    @JoinColumn(name="partyId")
    private Party partyId; // FK

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "package_product")
    private List<String> product;

    @NotNull
    private String address;

    @NotNull
    private int amount;

    public List<Long> getOrderIds(){
        List<Long> ordersList = new ArrayList<>(orderId.size());
        Iterator<Orders> iter = orderId.iterator();
        while(iter.hasNext()){
            ordersList.add(iter.next().getOrderId());
        }
        return ordersList;
    }

    @Builder.Default
    private boolean activated = Boolean.TRUE;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
}
