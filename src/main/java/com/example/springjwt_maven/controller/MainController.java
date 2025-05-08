package com.example.springjwt_maven.controller;

import com.example.springjwt_maven.annotation.UserAuth;
import com.example.springjwt_maven.dto.out.CustomUserDetails;
import com.example.springjwt_maven.entity.UserEntity;
import com.example.springjwt_maven.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
@ResponseBody
@RequestMapping("/")
// 공식문서에는 Authowired 방식보다는 생성자 주입 방식 권고
public class MainController {
    private final JWTUtil jwtUtil;

    public MainController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public String mainP(){

        // Stateless 상태이지만 일시적으로 생성되는 세션에서 추출 ㄱㄴ
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        // userRole 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "Main Controller / name : " + name  + ", role : " + role;
    }

    // @PreAuthorize 사용으로 허가하고자 하는 ROLE 지정가능...
    // 커스텀 어노테이션 테스트 원할 시 해당 어노테이션 삭제 요망
    @PreAuthorize("hasRole('ADMIN')")
    // 커스텀 어노테이션으로 권한 인가작업 테스트 코드
    @GetMapping("/userAuthTest")
    public String annotationTestUser(@UserAuth UserEntity user, HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader("Authorization");

        String token = authorization.split(" ")[1];

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        System.out.println("token : "+token);

        System.out.println("username : " + username + ", role : " + role);

        System.out.println("user : " + user);

        if(user == null) {
            response.setStatus(403);
            return "Not authorized";
        }
        response.setStatus(200);
        return "Test Success / "+user.getId();
    }
}
