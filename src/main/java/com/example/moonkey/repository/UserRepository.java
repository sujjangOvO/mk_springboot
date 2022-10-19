package com.example.moonkey.repository;

import com.example.moonkey.domain.Account;
import com.example.moonkey.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities") // 쿼리가 수행될 떄 Lazy 조회가 아니라 Eager 조회로 authorities 정보를 같이 가져오는 어노테이션
    Optional<User> findOneWithAuthoritiesByUsername(String username);
    //Optional<Account> findOneWithAuthoritiesByUsername(String username); Account
}
