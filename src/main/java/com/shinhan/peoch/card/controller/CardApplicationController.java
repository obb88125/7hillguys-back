package com.shinhan.peoch.card.controller;
import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.peoch.card.dto.CardRequestDTO;
import com.shinhan.peoch.card.dto.UserInfoDTO;
import com.shinhan.peoch.card.service.CardApplicationService;
import com.shinhan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardApplicationController {

    private final CardApplicationService cardService;


    private final UserRepository userRepository;



    @PostMapping
    public ResponseEntity<String> applyCard(@RequestBody CardRequestDTO request) {
        cardService.createCard(request);
        return ResponseEntity.ok("카드 신청이 완료되었습니다.");
    }

    @GetMapping("/userInfo")
    public ResponseEntity<UserInfoDTO> getUserInfo() {
        // 현재 JWT 적용 전이므로 userId를 고정 (예: 16L)
        UserEntity user = userRepository.findById(16L)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        UserInfoDTO dto = new UserInfoDTO(user.getName(), user.getPhone(), user.getEmail(), user.getAddress());
        return ResponseEntity.ok(dto);
    }
}
