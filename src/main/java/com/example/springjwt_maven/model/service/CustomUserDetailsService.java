package com.example.springjwt_maven.model.service;

import com.example.springjwt_maven.model.dao.UserDao;
import com.example.springjwt_maven.model.dto.CustomUserDetails;
import com.example.springjwt_maven.model.dto.User;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userDao.findByUsername(username);

        if(userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
