package com.example.moonkey.repository;

import com.example.moonkey.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long>  { // JpaRepository를 extends하면 findAll, save 등의 메소드를 기본적으로 사용 가능
    @EntityGraph(attributePaths = "authorities") // 쿼리가 수행될 때 Lazy 조회가 아니라 Eager 조회로 authorities 정보를 같이 가져오는 어노테이션
    Optional<Account> findOneWithAuthoritiesById(String id); // username을 기준으로 Account 정보를 가져올 때 권한 정보도 함께 가져옴
}
