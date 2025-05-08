package com.example.springjwt_maven.vo.out;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDetailResponseVo {
    private int id;
    private String username;
    private String password;
    private String role;
}
