package com.shinhan.peoch.card;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;
    private final CardDataService cardDataService;

    public CardController(CardService cardService, CardDataService cardDataService) {
        this.cardService = cardService;
        this.cardDataService = cardDataService;
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

    // 관리자 대시보드에서 카드 데이터 요청
    @GetMapping("/cardData/{userId}")
    public CardDataResponseDTO getCardData(@PathVariable Long userId, @RequestParam String date) {
        return cardDataService.getCardData(userId, date);
    }

}
