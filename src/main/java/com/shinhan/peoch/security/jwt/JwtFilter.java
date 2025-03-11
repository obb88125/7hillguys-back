package com.shinhan.peoch.security.jwt;

import com.shinhan.peoch.auth.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        log.info("=== [JwtFilter] 요청 도착: {} ===", request.getRequestURI());

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.warn("[JwtFilter] Authorization 헤더 없음 또는 잘못된 형식");
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.warn("[JwtFilter] Authorization 헤더 없음 또는 잘못된 형식");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);
        log.info("[JwtFilter] 추출된 JWT: {}", token);

        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            log.warn("[JwtFilter] 블랙리스트에 등록된 JWT 사용 감지. 접근 거부.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "블랙리스트에 등록된 토큰입니다.");
            return;
        }

        // 토큰 유효성 검사 결과를 변수에 저장하여 중복 호출 방지
        boolean isValid = jwtUtil.validationToken(token);
        if (!isValid) {
            log.warn("[JwtFilter] JWT가 유효하지 않음");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 JWT입니다.");
            return;
        }

        String email = jwtUtil.getUserEmail(token);
        if (email == null) {
            log.warn("[JwtFilter] JWT에서 이메일 추출 실패");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT에서 이메일을 추출할 수 없습니다.");
            return;
        }
        log.info("[JwtFilter] 추출된 이메일: {}", email);

        UserDetails userDetails = userService.loadUserByUsername(email);
        if (userDetails == null) {
            log.warn("[JwtFilter] 이메일에 해당하는 사용자 정보 없음: {}", email);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "사용자 정보를 찾을 수 없습니다.");
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("[JwtFilter] 사용자 인증 완료. SecurityContext 업데이트됨. 사용자: {}", email);

        filterChain.doFilter(request, response);
    }

    //로그인과 회원가입 요청에서는 필터를 실행하지 않도록 예외 처리
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register");
    }
}