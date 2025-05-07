package com.example.springjwt_maven.model.service;

import com.example.springjwt_maven.model.dao.UserDao;
import com.example.springjwt_maven.model.dto.JoinDTO;
import com.example.springjwt_maven.model.dto.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
//        System.out.println("username: " + username + " password: " + password);

        boolean isExist = userDao.existsByUsername(username);

        if(isExist) {
            System.out.println("이미 있는 UserAthentication");
            return;
        }

        User data = new User();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userDao.registUser(data);
    }
}
