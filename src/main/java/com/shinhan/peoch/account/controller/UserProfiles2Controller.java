package com.shinhan.peoch.account.controller;

import com.shinhan.entity.UserProfiles2Entity;
import com.shinhan.peoch.auth.service.UserProfiles2Service;
import com.shinhan.peoch.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserProfiles2Controller {

    private final UserProfiles2Service userProfiles2Service;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping("/userprofiles2")
    public ResponseEntity<UserProfiles2Entity> getUserProfiles2(@CookieValue(value = "jwt", required = false) String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            return null;
        }

        // JWT에서 userId 추출
        Long userIdLong = jwtTokenProvider.getUserIdFromToken(jwtToken);
        if (userIdLong == null) {
            return null;
        }
        Integer userId = userIdLong.intValue();

        UserProfiles2Entity profile = userProfiles2Service.getUserProfiles2ByUserId(userId);
        return ResponseEntity.ok(profile);
    }
}
