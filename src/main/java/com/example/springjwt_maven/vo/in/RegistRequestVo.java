package com.example.springjwt_maven.vo.in;

import com.example.springjwt_maven.dto.in.RegistRequestDto;
import com.example.springjwt_maven.dto.out.RegistResponseDto;
import com.example.springjwt_maven.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RegistRequestVo {
    private String username;
    private String password;
    private String role;

    public static RegistResponseDto from(RegistRequestVo vo){
        return RegistResponseDto.builder()
                .username(vo.username)
                .password(vo.password)
                .role(vo.role)
                .build();
    }
}
