package com.shinhan.peoch.lifecycleincome.controller;

import com.shinhan.peoch.lifecycleincome.entity.lifecycleincomeEntity;
import com.shinhan.peoch.lifecycleincome.service.lifecycleincomeService;
import com.shinhan.peoch.security.jwt.AuthService;
import com.shinhan.peoch.security.jwt.JwtUtil;
import com.shinhan.peoch.security.jwt.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class lifecycleincomeController {
    private final lifecycleincomeService userService;
    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody lifecycleincomeEntity user) {
        return userService.register(user);
    }





}
