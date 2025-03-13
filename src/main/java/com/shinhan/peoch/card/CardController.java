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
    @GetMapping("/statement/{userId}")
    public CardStatementResponseDTO getCardStatement(@PathVariable("userId") Long userId, @RequestParam(required = false) String yearMonth) {
        System.out.println("요청옴");
        return cardService.getCardStatement(userId, yearMonth);
    }

    // 카드 실적/혜택 조회
    @GetMapping("/summary/{userId}")
    public List<CardPerformanceDTO> getCardPerformance(@PathVariable Long userId, @RequestParam(required = false) Integer month) {
        return cardService.getCardPerformance(userId, month);
    }

}
