package com.example.springjwt_maven.controller;

import com.example.springjwt_maven.service.JoinService;
import com.example.springjwt_maven.vo.in.RegistRequestVo;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PutMapping("/join")
    public String joinProcess(@RequestBody RegistRequestVo vo) {
        System.out.println("JoinController's username : "+vo.getUsername());
        System.out.println("JoinController's userPassword : "+vo.getPassword());
        joinService.joinProcess(RegistRequestVo.from(vo));

        return "ok";
    }
}
