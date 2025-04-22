package com.playdata.orderservice.user.repository;

import com.playdata.orderservice.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // email 존재하는지 확인
    Optional<User> findByEmail(String email);

}
