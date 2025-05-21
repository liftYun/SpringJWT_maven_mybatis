package com.example.springjwt_maven.service;

import com.example.springjwt_maven.dto.in.RegistRequestDto;
import com.example.springjwt_maven.dto.out.RegistResponseDto;
import com.example.springjwt_maven.repository.UserDao;
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

    public void joinProcess(RegistResponseDto registResponseDto) {

        String username = registResponseDto.getUsername();
        String password = registResponseDto.getPassword();

        boolean isExist = userDao.existsByUsername(username);

        if(isExist) {

            System.out.println("이미 있는 UserAthentication");
            return;
        }

        RegistRequestDto registUserData = new RegistRequestDto();

        registUserData.setUsername(username);
        registUserData.setPassword(bCryptPasswordEncoder.encode(password));
        registUserData.setRole("ROLE_USER");

        userDao.registUser(RegistRequestDto.from(registUserData));
    }
}
