package com.example.springjwt_maven.vo.in;

import com.example.springjwt_maven.dto.in.RegistRequestDto;
import com.example.springjwt_maven.dto.out.RegistResponseDto;
import com.example.springjwt_maven.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@Builder
public class RegistRequestVo {
    private String nickname;
    private String username;
    private String userEmail;
    private String password;
    private String phoneNumber;
    private String role;

    public static RegistResponseDto from(RegistRequestVo vo){
        List<Integer> list = new LinkedList<>();

        return RegistResponseDto.builder()
                .nickname(vo.nickname)
                .username(vo.username)
                .userEmail(vo.userEmail)
                .password(vo.password)
                .phoneNumber(vo.phoneNumber)
                .role(vo.role)
                .build();

    }

}
