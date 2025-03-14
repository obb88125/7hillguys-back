package com.shinhan.peoch.benefit.controller;


import com.shinhan.peoch.benefit.dto.BenefitApplyDTO;
import com.shinhan.peoch.benefit.dto.BenefitResponseDTO;
import com.shinhan.peoch.benefit.service.MyBenefitService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benefit")
@RequiredArgsConstructor
@Getter
@Slf4j
public class MyBenefitController {

    private final MyBenefitService myBenefitService;

    // 1. 내 카드에 적용된 혜택 및 사용 가능한 혜택 목록 가져오기
    @GetMapping("/card")
    public ResponseEntity<BenefitResponseDTO> getCardBenefits() {
        // 예시: 토큰에서 사용자 ID 추출 (실제 구현은 JWT 라이브러리 등을 사용)
        // String token = authHeader.substring(7); // "Bearer " 제거
        // Long userId = tokenService.getUserIdFromToken(token);
        // 여기서는 임시로 고정값 사용 예: userId = 16L;
        Long userId = 16L;

        BenefitResponseDTO response = myBenefitService.getBenefitsByUserId(userId);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{benefitId}")
    public ResponseEntity<?> deleteBenefit(
            @PathVariable("benefitId") Long benefitId,
            @RequestParam("cardId") Long cardId) {
        myBenefitService.deleteBenefit(benefitId, cardId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/apply")
    public ResponseEntity<String> applyBenefits(@RequestBody BenefitApplyDTO benefitApplyDTO) {

        log.info("benefitApplyDTO: {}", benefitApplyDTO);

        myBenefitService.applyBenefits(benefitApplyDTO);
        return ResponseEntity.ok("혜택이 성공적으로 적용되었습니다.");
    }
}
