package com.example.springjwt_maven.dto.in;

import com.example.springjwt_maven.dto.out.RefreshTokenResponseDto;
import com.example.springjwt_maven.entity.RefreshTokenEntity;
import lombok.*;

import java.util.Date;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueRefreshTokenDto {
    private int id;
    private String uuid;
    private String token;
    private Date expires;
    private Date created;

    public static RefreshTokenEntity from(IssueRefreshTokenDto dto){
        return RefreshTokenEntity.builder()
                .id(dto.id)
                .uuid(dto.uuid)
                .token(dto.token)
                .expiresAt(dto.expires)
                .createdAt(dto.created)
                .build();
    }
}
