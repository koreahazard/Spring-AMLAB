package com.amlab.spring_amlab.account.repository;

import com.amlab.spring_amlab.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
}
