package com.shinhan.peoch.security;

import com.shinhan.peoch.auth.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser extends User {
    private static final String ROLE_PREFIX = "ROLE_";
    private final UserEntity user;

    public SecurityUser(UserEntity user) {
        super(user.getEmail(), user.getPassword(), makeRole(user));
        this.user = user;
    }

    private static Collection<? extends GrantedAuthority> makeRole(UserEntity user) {
        Collection<GrantedAuthority> roleList = new ArrayList<>();
        roleList.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole()));
        return roleList;
    }

    //UserEntity의 정보를 직접 가져올 수 있도록 getter 추가
    public Long getUserId() {
        return user.getUserId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getRole() {
        return user.getRole().name();
    }

    public UserEntity getUserEntity() {
        return user;  //UserEntity 자체를 가져올 수 있도록 추가
    }
}