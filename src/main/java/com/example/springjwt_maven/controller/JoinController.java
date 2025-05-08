package com.example.springjwt_maven.controller;

import com.example.springjwt_maven.model.service.JoinService;
import com.example.springjwt_maven.vo.in.RegistRequestVo;
import org.springframework.web.bind.annotation.PutMapping;
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
    public String joinProcess(RegistRequestVo vo) {

        joinService.joinProcess(RegistRequestVo.from(vo));

        return "ok";
    }
}
