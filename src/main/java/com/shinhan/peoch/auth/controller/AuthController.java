package com.shinhan.peoch.auth.controller;

import com.shinhan.peoch.auth.dto.UserResponseDTO;
import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.peoch.auth.service.UserService;
import com.shinhan.peoch.security.SecurityUser;
import com.shinhan.peoch.security.jwt.AuthService;
import com.shinhan.peoch.security.jwt.JwtUtil;
import com.shinhan.peoch.security.jwt.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody UserEntity user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserEntity user) {
        return authService.login(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
        }

        token = token.substring(7); //"Bearer " 제거

        if(!jwtUtil.validationToken(token)) {
            return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
        }

        String email = jwtUtil.getUserEmail(token);
        if(email == null) {
            return ResponseEntity.badRequest().body("JWT에서 이메일을 추출할 수 없습니다.");
        }

        long expirationTime = jwtUtil.getExpirationTime(token);
        tokenBlacklistService.blacklistToken(token, expirationTime);

        return ResponseEntity.ok("로그아웃이 성공적으로 완료되었습니다.");
    }

    @GetMapping("/user")
    public UserResponseDTO getCurrentUser(@AuthenticationPrincipal SecurityUser securityUser) {
        if (securityUser == null) {
            throw new RuntimeException("SecurityUser인증되지 않은 사용자입니다.");
        }
        return new UserResponseDTO(securityUser.getUserId());
    }

}
