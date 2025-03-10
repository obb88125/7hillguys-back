package com.shinhan.peoch.invest.controller;

import com.shinhan.peoch.invest.service.InvestmentResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investment")
@RequiredArgsConstructor
public class InvestmentResultController {
    private final InvestmentResultService investmentResultService;

    @GetMapping("{grantId}/status")
    public String checkInvestmentStatus(@PathVariable Integer grantId) {
        return investmentResultService.getInvestmentStatus(grantId);
    }
}
