package com.shinhan.peoch.user.service;

import com.shinhan.peoch.auth.entity.UserEntity;
import com.shinhan.peoch.user.Repository.UserSearchRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;

@Service
public class UserSearchService {

    @Autowired
    private UserSearchRepository UserSearchRepository;
     
    public List<UserEntity> searchUsersByName(String query) { 
        return UserSearchRepository.findByNameContaining(query);
    }
}
