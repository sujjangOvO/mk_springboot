package com.example.moonkey.repository;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface OrderRepository extends  JpaRepository<Orders,Long>{
    Orders findAllByOrderDate(LocalDateTime now);
    Optional<Orders> findOneByOrderId(long id);
    List<Orders> findAll();

    List<Orders> findAllByAccountUid(Account account);

}
