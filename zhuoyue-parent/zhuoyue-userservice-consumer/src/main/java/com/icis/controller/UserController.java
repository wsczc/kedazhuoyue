package com.icis.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.icis.bean.UserInfo;
import com.icis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    //@Autowired  //自动注入当前容器的接口实现类  本地服务
    @Reference  //远程  拉取注册中心上的服务
    private UserService userService;

    @RequestMapping("/getAll")
    @ResponseBody
    public List<UserInfo> getAllUser(){

        return this.userService.getAll();
    }
}
