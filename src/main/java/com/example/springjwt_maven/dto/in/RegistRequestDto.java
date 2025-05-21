package com.example.springjwt_maven.dto.in;

import com.example.springjwt_maven.entity.UserEntity;
import com.example.springjwt_maven.vo.in.RegistRequestVo;
import lombok.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistRequestDto {
    private int id;
    private String nickname;
    private String username;
    private String userEmail;
    private String password;
    private String phoneNumber;
    private String role;

    public static UserEntity from(RegistRequestDto dto){
        return UserEntity.builder()
                .id(dto.id)
                .nickname(dto.nickname)
                .username(dto.username)
                .userEmail(dto.userEmail)
                .password(dto.password)
                .phoneNumber(dto.phoneNumber)
                .role(dto.role)
                .build();
    }
}
