package com.example.springjwt_maven.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RefreshToken {
    private int id;
    private int userId;
    private String token;

    public RefreshToken(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
