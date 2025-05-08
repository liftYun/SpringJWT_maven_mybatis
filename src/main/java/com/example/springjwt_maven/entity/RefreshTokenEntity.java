package com.example.springjwt_maven.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("RefreshTokenEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RefreshTokenEntity {
    private int id;
    private int userId;
    private String token;
}
