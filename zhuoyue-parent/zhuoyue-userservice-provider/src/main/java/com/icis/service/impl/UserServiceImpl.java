package com.icis.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icis.bean.UserInfo;
import com.icis.mapper.UserMapper;
import com.icis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
//@Service  spring的注解  把服务放到当前项目的spring容器 不能用了 因为需要放到注册中心
@Service  //alibaba 根据你的服务 放入到 注册中心   需要开启dubbo支持spring才会理这个注解
            //去 Provider 的启动类上 加个 @EnableDubbo
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserInfo> getAll() {
        return userMapper.selectAll();
    }
}
