package com.example.springjwt_maven.controller;

import com.example.springjwt_maven.jwt.JWTUtil;
import com.example.springjwt_maven.model.service.CustomUserDetailsService;
import com.example.springjwt_maven.model.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@ResponseBody
public class RefreshController {

    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;

    public RefreshController(JWTUtil jwtUtil, RefreshTokenService refreshTokenService, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @CookieValue(name="refreshToken", required=true) String refreshToken) {

        // 1) 서명+만료 검사
        Claims claims = jwtUtil.parseClaims(refreshToken);
        String username = claims.getSubject();
        int userId = Integer.parseInt(claims.getId());

        // 2) DB에 저장된 토큰과 일치하는지 확인
        if (!refreshTokenService.isValid(userId, refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 3) UserDetailsService 로부터 권한 조회
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        // (여러 개라면 “ROLE_USER,ROLE_ADMIN” 식으로 합침)
        String newAccessToken = jwtUtil.createAccessToken(userId, username, role);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newAccessToken)
                .build();
    }
}
