package com.example.springjwt_maven.service;

import com.example.springjwt_maven.dto.out.CustomUserDetails;
import com.example.springjwt_maven.entity.UserEntity;
import com.example.springjwt_maven.repository.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {

        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        UserEntity userData = userDao.findByUserEmail(userEmail);

        if(userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }

    public UserDetails loadUserByUuid(String uuid) throws UsernameNotFoundException {

        UserEntity userData = userDao.findByUuid(uuid);

        if(userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }

    public UserDetails loadUserByUserEmail(String userEmail) throws UsernameNotFoundException {

        UserEntity userData = userDao.findByUserEmail(userEmail);

        if(userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
