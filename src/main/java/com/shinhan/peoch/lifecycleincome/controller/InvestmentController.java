package com.shinhan.peoch.lifecycleincome.controller;


import com.shinhan.entity.InvestmentEntity;
import com.shinhan.peoch.lifecycleincome.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
