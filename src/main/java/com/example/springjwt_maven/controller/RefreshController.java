package com.example.springjwt_maven.controller;

import com.example.springjwt_maven.jwt.JWTUtil;
import com.example.springjwt_maven.service.CustomUserDetailsService;
import com.example.springjwt_maven.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            @CookieValue(name="refreshToken", required=true) String refreshToken, HttpServletResponse response) {

        // 1) 서명+만료 검사
        Claims claims = jwtUtil.parseClaims(refreshToken);
        Integer userId = claims.get("userId", Integer.class);
        System.out.println("UserId : "+ userId);
        // 2) DB에 저장된 토큰과 일치하는지 확인
        if (!refreshTokenService.isValid(userId, refreshToken)) {
            // refreshToken이 DB에 저장된 값과 다르므로 예외 상황
            System.out.println("Before to delete Token : "+userId);
            // 저장된 토큰 삭제
            refreshTokenService.deleteToken(userId);
            System.out.println("delete RefreshToken");
            // 이후 방침에 따라 추가 인증 등 확인 요망

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        // 3) UserDetailsService 로부터 권한 조회
        UserDetails userDetails = customUserDetailsService.loadUserByUserId(userId);
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                // (여러 개라면 “ROLE_USER,ROLE_ADMIN” 식으로 합침)
                .collect(Collectors.joining(","));
        String newRefreshToken = jwtUtil.createRefreshToken(userId);

        refreshTokenService.saveToken(userId, newRefreshToken);
        Cookie cookie = new Cookie("refreshToken", newRefreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int)(jwtUtil.getRefreshExpiredMs()/1000));
        response.addCookie(cookie);

        String newAccessToken = jwtUtil.createAccessToken(userId, userDetails.getUsername(), role);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newAccessToken)
                .build();
    }
}
