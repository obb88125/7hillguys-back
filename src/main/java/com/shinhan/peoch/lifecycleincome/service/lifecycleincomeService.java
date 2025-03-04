//package com.shinhan.peoch.lifecycleincome.service;
//
//import com.shinhan.peoch.lifecycleincome.entity.lifecycleincomeEntity;
//import com.shinhan.peoch.lifecycleincome.repository.lifecycleincomeRepository;
//import com.shinhan.peoch.security.SecurityUser;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//@Service
//@RequiredArgsConstructor
//public class lifecycleincomeService {
//    private final lifecycleincomeRepository userRepository;
//
//
//    public String register(lifecycleincomeEntity user) {
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            throw new RuntimeException("이미 존재하는 사용자입니다.");
//        }
//
//    return "";
//    }
//
//}
