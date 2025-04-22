package com.playdata.orderservice.user.service;

import com.playdata.orderservice.user.dto.UserLoginReqDto;
import com.playdata.orderservice.user.dto.UserSaveReqDto;
import com.playdata.orderservice.user.entity.User;
import com.playdata.orderservice.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    // 서비스는 repository에 의존하고 있다. -> repository의 기능을 써야 한다.
    // repository 객체를 자동으로 주입받다. (JPA가 만들어서 컨테이너에 등록해 놓음)
    private final UserRepository userRepository;

    // 컨트롤러가 이 메서드를 호출할 것이다.
    // 그리고 지가 전달받은 dto를 그대로 넘길 것이다.
    public User userCreate(UserSaveReqDto dto) {
        Optional<User> foundEmail = userRepository.findByEmail(dto.getEmail());

        if(foundEmail.isPresent()){
            // 이메일 존재? -> 이메일 중복 -> 회원가입 불가!
            // 예외를 일부러 생성시켜서 컨트롤러가 감지하게 한다.
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        // 이메일 중복 안됨 -> 회원가입 진행
        User saved = userRepository.save(dto.toEntity());

        return saved;
    }

    public User login(UserLoginReqDto dto) {
        // 이메일로 user 조회하기
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("이메일을 확인해 주세요.")
        );

        // 비밀번호 확인
        if(!user.getPassword().equals(dto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
