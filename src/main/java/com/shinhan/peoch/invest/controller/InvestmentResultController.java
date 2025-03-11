package com.shinhan.peoch.invest.controller;

import com.shinhan.peoch.invest.service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investment")
@RequiredArgsConstructor
public class InvestmentResultController {
    private final InvestmentService investmentService;

    @GetMapping("{grantId}/status")
    public String checkInvestmentStatus(@PathVariable Integer grantId) {
        return investmentService.getInvestmentStatus(grantId);
    }
}
