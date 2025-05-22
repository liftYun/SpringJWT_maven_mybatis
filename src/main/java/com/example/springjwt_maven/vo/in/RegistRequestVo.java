package com.example.springjwt_maven.vo.in;

import com.example.springjwt_maven.dto.in.RegistRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

//@Setter
@Getter
@Builder
public class RegistRequestVo {
    private String nickname;
    private String username;
    private String userEmail;
    private String password;
    private String phoneNumber;

    public static RegistRequestDto from(RegistRequestVo vo){

        return RegistRequestDto.builder()
                .nickname(vo.nickname)
                .username(vo.username)
                .userEmail(vo.userEmail)
                .password(vo.password)
                .phoneNumber(vo.phoneNumber)
                .build();

    }

}
