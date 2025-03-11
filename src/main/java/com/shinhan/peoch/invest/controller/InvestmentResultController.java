package com.shinhan.peoch.invest.controller;

import com.shinhan.peoch.invest.service.InvestmentResultService;
import com.shinhan.peoch.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/investment")
@RequiredArgsConstructor
public class InvestmentResultController {
    private final InvestmentResultService investmentResultService;
    private final JwtUtil jwtUtil;

    @Value("${jwt.secret}")  // application.yml의 secret 가져오기
    private String secretKey;

    @GetMapping("/status")  //API 매핑 확인
    public ResponseEntity<String> checkInvestmentStatus(HttpServletRequest request) {
        log.info("🔍 [백엔드] API 요청 수신: /api/investment/status");

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("토큰이 없습니다.");
        }

        String jwtToken = token.substring(7); // "Bearer " 제거
        Claims claims;
        try {
            //JwtUtil 활용하여 검증 및 Claims 추출
            claims = jwtUtil.parseClaims(jwtToken);
            log.info("[백엔드] 토큰 검증 성공");
        } catch (Exception e) {
            log.error("[백엔드] JWT 검증 실패", e);
            return ResponseEntity.status(401).body("잘못된 토큰입니다.");
        }

        Integer userId = claims.get("userId", Integer.class);
        log.info("[백엔드] userId: " + userId);
        String status = investmentResultService.getInvestmentStatus(userId);
        log.info("[백엔드] 투자 상태 반환: " + status);
        return ResponseEntity.ok(status);
    }
}
