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
    private String uuid;
    private String nickname;
    private String username;
    private String userEmail;
    private String password;
    private String phoneNumber;
    private String role;

    public static UserEntity from(UserDetailRequestDto dto){
        return UserEntity.builder()
                .id(dto.id)
                .uuid(dto.uuid)
                .userEmail(dto.userEmail)
                .username(dto.username)
                .userEmail(dto.userEmail)
                .password(dto.password)
                .phoneNumber(dto.phoneNumber)
                .role(dto.role)
                .build();
    }
}
