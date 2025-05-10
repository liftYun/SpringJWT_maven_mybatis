package com.example.springjwt_maven.jwt;

import com.example.springjwt_maven.dto.out.CustomUserDetails;
import com.example.springjwt_maven.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

//        System.out.println("LoginFilter username : " + username);
//        System.out.println("LoginFilter password : " + password);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
//        System.out.println("authToken : " + authToken);

        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username =  customUserDetails.getUsername();
        int userId = customUserDetails.getUserId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String accessToken = jwtUtil.createAccessToken(userId, username, role);
        String refreshToken = jwtUtil.createRefreshToken(userId);
        System.out.println("loginFiler's refreshToken= " + refreshToken);

        // Refresh Token 을 DB 혹은 Redis 등에 저장 (토큰 회수/무효화 위해)
        refreshTokenService.saveToken(userId, refreshToken);

        response.addHeader("Authorization", "Bearer " + accessToken);
        // 보안성을 위해 HttpOnly Cookie 로 발급하는 것을 권장
        // SPA(Single Page Application)를 가정하여 메모리 저장 방식(Java Script 변수 저장)을 사용
        // 새로고침 시에 사라지므로 아래와 같이 쿠키 저장을 하기도 함.
//        Cookie accessCookie = new Cookie("accessToken", accessToken);
//        accessCookie.setHttpOnly(true);
//        accessCookie.setPath("/");
//        accessCookie.setMaxAge((int)(jwtUtil.getAccessExpiredMs() / 1000));
//        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge((int)(jwtUtil.getRefreshExpiredMs() / 1000));
        response.addCookie(refreshCookie);
        System.out.println("After refreshToken= " + refreshToken);
    }

    //로그인 실패시 실행하는 메소드
    // 추후 추가적인 실패시 기능 추가 가능
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }
}
