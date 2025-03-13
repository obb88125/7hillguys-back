package com.shinhan.peoch.lifecycleincome.controller;

import com.shinhan.entity.InvestmentEntity;
import com.shinhan.entity.UserProfileEntity;
import com.shinhan.peoch.invest.service.UserProfileService;
import com.shinhan.peoch.lifecycleincome.DTO.*;
import com.shinhan.peoch.lifecycleincome.service.ExitCostService;
import com.shinhan.peoch.lifecycleincome.service.InvestmentService;
import com.shinhan.peoch.lifecycleincome.service.SetInvestAmountService;
import com.shinhan.peoch.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class InvestmentController {

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.secret}")  // application.yml의 secret 가져오기
    String secretKey;

    @Autowired
    InvestmentService investmentService;

    @Autowired
    SetInvestAmountService setInvestAmountService;

    @Autowired
    ExitCostService exitCostService;

    @Autowired
    UserProfileService userProfileService;

    @GetMapping("/investment")
    public InvestmentEntity saveInvestment(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String jwtToken = token.substring(7); // "Bearer " 제거
        Claims claims;
        try {
            claims = jwtUtil.parseClaims(jwtToken);
        } catch (Exception e) {
            return null;
        }
        Integer userId = claims.get("userId", Integer.class);
        return investmentService.createInvestment(userId);
    }

    @GetMapping("/investment/refund-rate")
    public ResponseEntity<Double> updateRefundRate(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String jwtToken = token.substring(7); // "Bearer " 제거
        Claims claims;
        try {
            claims = jwtUtil.parseClaims(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
        Integer userId = claims.get("userId", Integer.class);
        double refundRate = investmentService.updateRefundRate(userId);
        return ResponseEntity.ok(refundRate);
    }

    @GetMapping("/investment/tempallowance")
    public InvestmentTempAllowanceDTO getInvestmentDetails(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String jwtToken = token.substring(7); // "Bearer " 제거
        Claims claims;
        try {
            claims = jwtUtil.parseClaims(jwtToken);
        } catch (Exception e) {
            return null;
        }
        Integer userId = claims.get("userId", Integer.class);
        return investmentService.calculateInvestmentDetails(userId);
    }

    @GetMapping("/investment/exit")
    public ResponseEntity<ExitResponseDTO> exitResponse(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String jwtToken = token.substring(7); // "Bearer " 제거
        Claims claims;
        try {
            claims = jwtUtil.parseClaims(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
        Integer userId = claims.get("userId", Integer.class);
        ExitResponseDTO exitResponseDTO;
        try {
            exitResponseDTO = exitCostService.exitResponseService(userId);
        } catch (Exception e) {
            exitResponseDTO = ExitResponseDTO.builder().message("현재 예상 소득 산출중입니다. 3분 이내로 완료 됩니다.").build();
        }
        return ResponseEntity.ok(exitResponseDTO);
    }
    @GetMapping("/investment/reallyexit")
    public ResponseEntity<ReallyExitResponseDTO> getInvestmentExitInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String jwtToken = token.substring(7); // "Bearer " 제거
        Claims claims;
        try {
            //JwtUtil 사용 claims 받아오기
            claims = jwtUtil.parseClaims(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
        Integer userId = claims.get("userId", Integer.class);
        ReallyExitResponseDTO response = investmentService.getInvestmentExitInfo(userId);
        return ResponseEntity.ok(response);
    }
    //userid를 받아서 userprofile로 바꿔야함!
    @GetMapping("/investment/setamount")
    public ResponseEntity<SetInvestAmountDTO> getInvestmentData(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String jwtToken = token.substring(7); // "Bearer " 제거
        Claims claims;
        try {
            //JwtUtil 사용 claims 받아오기
            claims = jwtUtil.parseClaims(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
        Integer userId = claims.get("userId", Integer.class);
        //UserID에 해당되는 profile 중에 가장 최신 profile챙겨옴
        UserProfileEntity userProfileEntity = userProfileService.findUserProfileByUserIdOrderByUpdatedAtDesc(Long.valueOf(userId));
        SetInvestAmountDTO response = setInvestAmountService.getInvestmentData(userProfileEntity.getUserProfileId());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/investment/refund-rate")
    public ResponseEntity<Double> expectedRefundRate(HttpServletRequest request,@RequestBody InvestmentRequestDTO requestDTO) {
        String token = request.getHeader("Authorization");
        String jwtToken = token.substring(7); // "Bearer " 제거
        Claims claims;
        try {
            //JwtUtil 사용 claims 받아오기
            claims = jwtUtil.parseClaims(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
        Integer userId = claims.get("userId", Integer.class);
        requestDTO.setUserId(userId);
        double refundRate = investmentService.checkRefundRate(requestDTO.getUserId(), requestDTO.getInvestAmount());
        return ResponseEntity.ok(refundRate);
    }

}
