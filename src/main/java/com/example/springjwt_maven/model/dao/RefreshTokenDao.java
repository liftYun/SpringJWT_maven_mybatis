package com.example.springjwt_maven.model.dao;

import com.example.springjwt_maven.model.dto.RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Mapper
public interface RefreshTokenDao {

    /**
     * refresh token 저장
     */
    void save(RefreshToken refreshToken);

    /**
     * refresh token 갱신
     */
    void update(RefreshToken refreshToken);

    /**
     * 주어진 username 으로 저장된 RefreshToken 조회
     */
    Optional<RefreshToken> findByUserId(int userId);

    /**
     * 주어진 username 으로 저장된 RefreshToken 레코드를 삭제
     */
    void deleteByUserId(int userId);
}
