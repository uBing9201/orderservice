package com.playdata.orderservice.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.playdata.orderservice.common.entity.Address;
import com.playdata.orderservice.user.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 가입 테스트")
    void insertTest() {
        // given
        Address addr = Address.builder()
                .city("서울특별시")
                .street("효령로 335")
                .zipCode("12345")
                .build();

        User user = User.builder()
                .email("abc1234@naver.com")
                .password("aaa1111!")
                .name("김메롱")
                .address(addr)
                .build();

        // when
        userRepository.save(user);

        // then
        User foundUser
                = userRepository.findByEmail("abc1234@naver.com").orElseThrow();

        assertEquals(user.getName(), foundUser.getName());
    }

}