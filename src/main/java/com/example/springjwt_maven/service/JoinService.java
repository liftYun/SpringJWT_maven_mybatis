package com.example.springjwt_maven.service;

import com.example.springjwt_maven.dto.in.RegistRequestDto;
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

    public void joinProcess(RegistRequestDto dto) {

        String username = dto.getUsername();

        boolean isExist = userDao.existsByUsername(username);

        if(isExist) {

            System.out.println("이미 있는 UserAthentication");
            return;
        }

//        RegistRequestDto registUserData = new RegistRequestDto();
//
//        registUserData.setUsername(username);
//        registUserData.setPassword(bCryptPasswordEncoder.encode(password));
//        registUserData.setRole("ROLE_USER");

        RegistRequestDto registUserData = RegistRequestDto.builder()
                                                            .nickname(dto.getNickname())
                                                            .username(dto.getUsername())
                                                            .userEmail(dto.getUserEmail())
                                                            .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                                                            .phoneNumber(dto.getPhoneNumber())
                                            //                .role(dto.getRole())
                                                            .role("ROLE_USER")
                                                            .build();

        userDao.registUser(RegistRequestDto.from(registUserData));
    }
}
