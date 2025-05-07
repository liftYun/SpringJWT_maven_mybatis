package com.example.springjwt_maven.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
// 런타임 중에도 해당 어노테이션 사용해야하므로 RUNTIME명시
@Retention(RetentionPolicy.RUNTIME)
/* 인증 객체 정보를 가지고 다니는 커스텀 어노테이션, CustomUserDetails 필드인 user를 가져온다. */
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")
public @interface User {
}
