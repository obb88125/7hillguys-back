package com.shinhan.peoch.card;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // 카드 명세서 조회
    @GetMapping("/cardStatement/{userId}")
    public CardStatementResponseDTO getCardStatement(@PathVariable Long userId, @RequestParam(required = false) String yearMonth) {
        return cardService.getCardStatement(userId, yearMonth);
    }

    // 혜택 명세서 조회
    @GetMapping("/benefitStatement/{userId}")
    public BenefitStatementResponseDTO getCardPerformance(@PathVariable Long userId, @RequestParam(required = false) String yearMonth) {
        return cardService.getCardPerformance(userId, yearMonth);
    }

    // 전체 혜택 조회
    @GetMapping("/allBenefitSearch/{userId}")
    public List<AllBenefitDTO> getAllBenefit(@PathVariable Long userId) {
        return cardService.getAllBenefit(userId);
    }

}
