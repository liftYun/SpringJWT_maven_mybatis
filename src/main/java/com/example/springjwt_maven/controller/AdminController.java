package com.example.springjwt_maven.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/check")
    public String adminP(){
        return "Admin Controller";
    }
}
