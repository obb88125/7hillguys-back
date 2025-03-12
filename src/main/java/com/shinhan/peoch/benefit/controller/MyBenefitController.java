package com.shinhan.peoch.benefit.controller;


import com.shinhan.peoch.benefit.dto.BenefitApplyDTO;
import com.shinhan.peoch.benefit.dto.BenefitResponseDTO;
import com.shinhan.peoch.benefit.service.MyBenefitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/benefits")
@RequiredArgsConstructor
public class MyBenefitController {

    private final MyBenefitService myBenefitService;

    // 1. 내 카드에 적용된 혜택 및 사용 가능한 혜택 목록 가져오기
    @GetMapping("/card/{cardId}")
    public ResponseEntity<com.shinhan.peoch.benefit.dto.BenefitResponseDTO> getCardBenefits(@PathVariable Long cardId) {
        // 예시: 토큰에서 사용자 ID 추출 (실제 구현은 JWT 라이브러리 등을 사용)
        // String token = authHeader.substring(7); // "Bearer " 제거
        // Long userId = tokenService.getUserIdFromToken(token);
        // 여기서는 임시로 고정값 사용 예: userId = 16L;
        Long userId = 16L;

        BenefitResponseDTO response = myBenefitService.getBenefitsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    // 2. 적용된 혜택 삭제하기 (benefitId와 타입("applied")를 프론트에서 보내거나 경로 변수로)
    @DeleteMapping("/{benefitId}")
    public ResponseEntity<String> deleteBenefit(@PathVariable Long benefitId) {
        myBenefitService.deleteBenefit(benefitId);
        return ResponseEntity.ok("혜택 삭제가 완료되었습니다.");
    }

    // 3. 결제 시 혜택 적용 (benefits를 카드에 병합)
    @PostMapping("/apply")
    public ResponseEntity<String> applyBenefits(@RequestBody BenefitApplyDTO dto) {
        myBenefitService.applyBenefits(dto);
        return ResponseEntity.ok("혜택이 적용되었습니다.");
    }
}
