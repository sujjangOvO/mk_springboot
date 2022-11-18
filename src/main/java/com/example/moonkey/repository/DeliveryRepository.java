package com.example.moonkey.repository;

import com.example.moonkey.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {

    List<Delivery> findAll();

    Optional<Delivery> findOneByDeliveryId(long id);
}
