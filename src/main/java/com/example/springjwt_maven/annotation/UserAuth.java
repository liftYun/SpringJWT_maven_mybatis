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
/**
 * @AuthenticationPrincipal : SecurityContextHolder에 접근할 수 있도록 하는 어노테이션(인증객체 접근 가능)
 *
 * (expression = "#this == 'anonymousUser' ? null : member")은 SpEL(Spring Expression Language) 표현식
 * 만약 현재의 객체 #this가 스프링 시큐리티에서 인증되지 않은 사용자를 나타내는 특별한 값인 anonymousUser라면, null을 반환
 * 아니면 Authentication.getPrincipal()를 통해서 가져올 수 있는 UserDetails 객체의 user 필드를 가져올 수 있음
 */
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : userEntity")
public @interface UserAuth {
}
