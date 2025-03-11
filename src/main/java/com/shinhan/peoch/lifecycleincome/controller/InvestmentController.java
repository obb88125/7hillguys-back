package com.shinhan.peoch.lifecycleincome.controller;


import com.shinhan.entity.InvestmentEntity;
import com.shinhan.peoch.lifecycleincome.DTO.ExitResponseDTO;
import com.shinhan.peoch.lifecycleincome.DTO.InvestmentTempAllowanceDTO;
import com.shinhan.peoch.lifecycleincome.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

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
    @GetMapping("/invest/exit/{userId}")
    public ResponseEntity<ExitResponseDTO> exitResponse(
            @PathVariable Integer userId) {
        ExitResponseDTO exitResponseDTO= null;
        return ResponseEntity.ok(exitResponseDTO);
    }
}
