package com.playdata.orderservice.common.auth;

import com.playdata.orderservice.user.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 역할: 토큰을 발급하고, 서명 위조를 검사하는 객체
@Component
public class JwtTokenProvider {

    // yml에 있는 값 땡겨오기 (properties 방식으로 지목)
    // 서명에 사용할 값 (512비트 이상의 랜덤 문자열을 권장)
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expiration;

    // 토큰 생성 메서드
    /*
        {
            "iss": "서비스 이름(발급자)",
            "exp": "2023-12-27(만료일자)",
            "iat": "2023-11-27(발급일자)",
            "email": "로그인한 사람 이메일",
            "role": "Premium"
            ...
            == 서명
        }
    */
    public String createToken(String email, Role role){
        // Claims: 페이로드에 들어갈 사용자 정보
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                // 현재 시간 밀리초에 30분을 더한 시간을 만료시간으로 세팅
                .setExpiration(new Date(now.getTime() + expiration * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
