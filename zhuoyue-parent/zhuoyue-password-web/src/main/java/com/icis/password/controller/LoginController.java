package com.icis.password.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//登录控制器
@Controller
public class LoginController {
    //创建一个映射路径  跳转到登录页
    @RequestMapping("/index")
    public String toIndex(){

        //跳转到登录页
        return "index.html";
    }
}
