package com.shinhan.peoch.card;

import com.shinhan.peoch.security.jwt.JwtTokenProvider;
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

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    // 카드 명세서 조회
    @GetMapping("/cardStatement")
    public CardStatementResponseDTO getCardStatement(@CookieValue(value = "jwt", required = false) String jwtToken,
            @RequestParam(required = false) String yearMonth) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            return null;
        }

        // JWT에서 userId 추출
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        if (userId == null) {
            return null;
        }

        return cardService.getCardStatement(userId, yearMonth);
    }

    // 혜택 명세서 조회
    @GetMapping("/benefitStatement")
    public BenefitStatementResponseDTO getCardPerformance(@CookieValue(value = "jwt", required = false) String jwtToken,
                                                          @RequestParam(required = false) String yearMonth) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            return null;
        }

        // JWT에서 userId 추출
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        if (userId == null) {
            return null;
        }

        return cardService.getCardPerformance(userId, yearMonth);
    }

    // 전체 혜택 조회
    @GetMapping("/allBenefitSearch")
    public List<AllBenefitDTO> getAllBenefit(@CookieValue(value = "jwt", required = false) String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            return null;
        }

        // JWT에서 userId 추출
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        if (userId == null) {
            return null;
        }

        return cardService.getAllBenefit(userId);
    }

    // 관리자 대시보드에서 카드 데이터 요청
    @GetMapping("/cardData")
    public CardDataResponseDTO getCardData(@CookieValue(value = "jwt", required = false) String jwtToken,
                                           @RequestParam String date) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            return null;
        }

        // JWT에서 userId 추출
        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);
        if (userId == null) {
            return null;
        }

        return cardDataService.getCardData(userId, date);
    }

}
