package com.example.springjwt_maven.dto.in;

import com.example.springjwt_maven.entity.UserEntity;
import lombok.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDetailRequestDto {
    private int id;
    private String username;
    private String password;
    private String role;

    public static UserEntity from(UserDetailRequestDto dto){
        return UserEntity.builder()
                .id(dto.id)
                .username(dto.username)
                .password(dto.password)
                .role(dto.role)
                .build();
    }
}
