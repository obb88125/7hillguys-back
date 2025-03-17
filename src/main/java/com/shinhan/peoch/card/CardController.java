package com.shinhan.peoch.card;

import org.springframework.beans.factory.annotation.Autowired;
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

//    @Autowired
//    JwtTokenProvider jwtTokenProvider;

    // 카드 명세서 조회
    @GetMapping("/cardStatement")
    public CardStatementResponseDTO getCardStatement(@CookieValue(value = "jwt", required = false) String jwtToken,
            @RequestParam(required = false) String yearMonth) {

//        if (jwtToken == null || jwtToken.isEmpty()) {
//            return null;
//        }
//
//        // JWT에서 userId 추출
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
//        if (userId == null) {
//            return null;
//        }
        Long userId = 1L;

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
