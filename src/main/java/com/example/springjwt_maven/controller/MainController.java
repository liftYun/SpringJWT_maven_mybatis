package com.example.springjwt_maven.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
@ResponseBody
// 공식문서에는 Authowired 방식보다는 생성자 주입 방식 권고
public class MainController {

    @GetMapping("/")
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
}
