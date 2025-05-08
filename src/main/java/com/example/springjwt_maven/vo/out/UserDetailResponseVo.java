package com.example.springjwt_maven.vo.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailResponseVo {
    private int id;
    private String username;
    private String password;
    private String role;
}
