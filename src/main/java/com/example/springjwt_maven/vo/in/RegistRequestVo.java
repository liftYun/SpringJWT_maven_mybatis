package com.example.springjwt_maven.vo.in;

import com.example.springjwt_maven.dto.in.RegistRequestDto;
import com.example.springjwt_maven.dto.out.RegistResponseDto;
import com.example.springjwt_maven.entity.UserEntity;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class RegistRequestVo {
    private int id;
    private String username;
    private String password;
    private String role;

    public static RegistResponseDto from(RegistRequestVo vo){
        return RegistResponseDto.builder()
                .id(vo.id)
                .username(vo.username)
                .password(vo.password)
                .role(vo.role)
                .build();
    }
}
