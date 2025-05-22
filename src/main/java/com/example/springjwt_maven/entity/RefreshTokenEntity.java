package com.example.springjwt_maven.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("RefreshTokenEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RefreshTokenEntity {
    private int id;
    private String uuid;
    private String token;
    private Date expiresAt;
    private Date createdAt;
}
