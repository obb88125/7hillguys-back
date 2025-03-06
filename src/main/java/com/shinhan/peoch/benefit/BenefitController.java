package com.shinhan.peoch.benefit;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/benefit")
public class BenefitController {
    private final BenefitService benefitService;

    public BenefitController(BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    // 전체 혜택 조회
    @GetMapping("/allBenefit/{userId}")
    public List<AllBenefitDTO> getAllBenefit(@PathVariable Long userId, @RequestParam(required = false) Integer month) {
        return benefitService.getAllBenefit(userId, month);
    }
}
