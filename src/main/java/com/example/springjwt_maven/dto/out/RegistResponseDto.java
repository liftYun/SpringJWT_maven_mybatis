package com.example.springjwt_maven.dto.out;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistResponseDto {
    private int id;
    private String nickname;
    private String username;
    private String userEmail;
    private String password;
    private String phoneNumber;
    private String role;
}
