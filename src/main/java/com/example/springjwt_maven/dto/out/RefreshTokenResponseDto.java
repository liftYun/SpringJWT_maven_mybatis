package com.example.springjwt_maven.dto.out;

import com.example.springjwt_maven.entity.RefreshTokenEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenResponseDto {
    private int id;
    private int userId;
    private String token;

    public static RefreshTokenResponseDto from(RefreshTokenEntity entity){
        return RefreshTokenResponseDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .token(entity.getToken())
                .build();
    }
}
