package com.example.springjwt_maven.controller;

import com.example.springjwt_maven.service.JoinService;
import com.example.springjwt_maven.vo.in.RegistRequestVo;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(@RequestBody RegistRequestVo vo) {

        System.out.println("JoinController's nickname : "+vo.getNickname());
        System.out.println("JoinController's username : "+vo.getUsername());
        System.out.println("JoinController's userEmail : "+vo.getUserEmail());
        System.out.println("JoinController's userPassword : "+vo.getPassword());
        System.out.println("JoinController's phoneNumber : "+vo.getPhoneNumber());

        joinService.joinProcess(RegistRequestVo.from(vo));

        return "ok";
    }
}
