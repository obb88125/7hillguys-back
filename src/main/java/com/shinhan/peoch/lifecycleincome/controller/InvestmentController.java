package com.shinhan.peoch.lifecycleincome.controller;

import com.shinhan.entity.InvestmentEntity;
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

    @GetMapping("/investment/{userId}")
    public InvestmentEntity saveInvestment(@PathVariable Integer userId) {
        return investmentService.createInvestment(userId);
    }
    @GetMapping("/investment/{userId}/refund-rate")
    public ResponseEntity<Double> updateRefundRate(@PathVariable Integer userId) {
        double refundRate = investmentService.updateRefundRate(userId);
        return ResponseEntity.ok(refundRate);
    }
    @GetMapping("/investment/tempallowance/{userId}")
    public InvestmentTempAllowanceDTO getInvestmentDetails(@PathVariable Integer userId) {
        return investmentService.calculateInvestmentDetails(userId);
    }
    @GetMapping("/investment/exit/{userId}")
    public ResponseEntity<ExitResponseDTO> exitResponse(
            @PathVariable Integer userId) {
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
    @GetMapping("/investment/setamount/{userProfileId}")
    public SetInvestAmountDTO getInvestmentData(@PathVariable Integer userProfileId) {
        return setInvestAmountService.getInvestmentData(userProfileId);
    }
    @PostMapping("/investment/refund-rate")
    public ResponseEntity<Double> expectedRefundRate(@RequestBody InvestmentRequestDTO requestDTO) {
        double refundRate = investmentService.checkRefundRate(requestDTO.getUserId(), requestDTO.getInvestAmount());
        System.out.println(refundRate);
        return ResponseEntity.ok(refundRate);
    }
}
