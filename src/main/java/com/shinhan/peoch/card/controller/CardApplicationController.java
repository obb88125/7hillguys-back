package com.shinhan.peoch.card.controller;
import com.shinhan.entity.CardDesignEntity;
import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.peoch.card.dto.CardRequestDTO;
import com.shinhan.peoch.card.dto.UserInfoDTO;
import com.shinhan.peoch.card.service.CardApplicationService;
import com.shinhan.peoch.card.service.CardApplicationService;
import com.shinhan.peoch.design.dto.CardDesignDTO;
import com.shinhan.peoch.design.service.CardDesignService;
import com.shinhan.peoch.security.SecurityUser;
import com.shinhan.peoch.security.jwt.JwtTokenProvider;
import com.shinhan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardApplicationController {
    private final JwtTokenProvider jwtTokenProvider;
    private final CardApplicationService cardService;
    private final CardDesignService cardDesignService;

    private final UserRepository userRepository;



    @PostMapping("/insert")
    public ResponseEntity<String> applyCard(
            @RequestPart("cardDesignDTO") CardDesignDTO cardDesignDTO,
            @RequestPart("englishName") String englishName,
            @RequestPart("pin") String pin,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {

        // 로깅으로 전달된 값 확인
        log.info("CardRequestDTO: {}", cardDesignDTO);
        log.info("영문 이름: {}", englishName);
        log.info("PIN: {}", pin);
        if (imageFile != null) {
            log.info("이미지 파일 이름: {}", imageFile.getOriginalFilename());
        } else {
            log.info("이미지 파일 없음");
        }
        CardRequestDTO cardRequestDTO = new CardRequestDTO();
        cardRequestDTO.setEnglishName(englishName);
        cardRequestDTO.setPin(pin);

        cardService.createCardApplication(securityUser.getUserId(), cardRequestDTO, cardDesignDTO, imageFile);

        return ResponseEntity.ok("카드 신청이 완료되었습니다.");


    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> getUserInfo(@CookieValue(value = "jwt", required = false) String jwtToken) {
        // 현재 JWT 적용 전이므로 userId를 고정 (예: 16L)
        if (jwtToken == null || jwtToken.isEmpty()) {
            log.warn("🚨 [ContractController] JWT 쿠키 없음!");
            return ResponseEntity.status(401).body(Map.of("error", "인증 토큰이 없습니다."));
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(jwtToken);

        UserEntity user = userRepository.findById(16L)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        UserInfoDTO dto = new UserInfoDTO(user.getName(), user.getPhone(), user.getEmail(), user.getAddress());
        return ResponseEntity.ok(dto);
    }
}
