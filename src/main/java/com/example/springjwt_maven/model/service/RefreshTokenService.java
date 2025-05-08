package com.example.springjwt_maven.model.service;

import com.example.springjwt_maven.dto.in.IssueRefreshTokenDto;
import com.example.springjwt_maven.dto.out.RefreshTokenResponseDto;
import com.example.springjwt_maven.entity.RefreshTokenEntity;
import com.example.springjwt_maven.jwt.JWTUtil;
import com.example.springjwt_maven.repository.RefreshTokenDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenDao refreshTokenDao;
    private final JWTUtil jwtUtil;

    public RefreshTokenService(RefreshTokenDao refreshTokenDao, JWTUtil jwtUtil) {
        this.refreshTokenDao = refreshTokenDao;
        this.jwtUtil = jwtUtil;
    }

    /**
     * DB에 저장된 refresh token과 전달받은 토큰을 비교하고,
     * JWTUtil.isExpired로 만료 여부까지 검사합니다.
     */
    public boolean isValid(int userId, String refreshToken) {
        Optional<RefreshTokenEntity> tokenOpt = refreshTokenDao.findByUserId(userId);
        if (tokenOpt.isEmpty()) {
            return false;
        }
        RefreshTokenResponseDto stored = RefreshTokenResponseDto.from(tokenOpt.get());
        // 1) 문자열 일치 여부
        if (!stored.getToken().equals(refreshToken)) {
            return false;
        }
        // 2) 만료 여부 검사
        return !jwtUtil.isExpired(refreshToken);
    }

    /**
     * 로그아웃 등 필요 시 DB에서 refresh token 삭제
     */
    public void deleteToken(int userId) {
        refreshTokenDao.deleteByUserId(userId);
    }

    /**
     * 로그인 시, 혹은 refresh 토큰 재발급 시 호출
     * 이미 사용자(username)로 저장된 레코드가 있으면 토큰만 갱신하고,
     * 없으면 새로 생성
     */
    public void save(int userId, String refreshToken) {
        refreshTokenDao.findByUserId(userId)
                .ifPresentOrElse(
                        existing -> {
                            existing.setToken(refreshToken);
                            refreshTokenDao.update(existing);
                        },
                        () -> {
//                            IssueRefreshTokenDto dto = new IssueRefreshTokenDto(userId, refreshToken);
                            refreshTokenDao.save(IssueRefreshTokenDto.from(new IssueRefreshTokenDto(0,userId, refreshToken)));
                        }
                );
    }
}