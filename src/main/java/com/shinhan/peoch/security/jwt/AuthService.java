package com.shinhan.peoch.security.jwt;

import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Map<String, String>> login(UserEntity dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 사용자가 존재하지 않습니다."));

        //암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(user);

        // HTTPOnly 쿠키 설정
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", accessToken)
                .httpOnly(true)  // JavaScript에서 접근 불가 (XSS 보호)
                .secure(true)  // HTTPS 환경에서만 전송
                .path("/")  // 모든 경로에서 사용 가능
                .maxAge(Duration.ofDays(7))  // 쿠키 유효 기간 설정
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())  // 쿠키로 반환
                .body(Map.of("message", "로그인 성공"));
    }
}
