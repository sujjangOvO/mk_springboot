package com.example.moonkey.repository;

import com.example.moonkey.domain.Menu;
import com.example.moonkey.domain.Store;
import com.example.moonkey.domain.Orders;
import com.example.moonkey.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface OrderRepository extends  JpaRepository<Orders,Long>{
    Orders findAllByOrderDate(LocalDateTime now);
    Optional<Orders> findOneByOrderId(long id);
    List<Orders> findAll();
}
