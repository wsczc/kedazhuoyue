package com.example.demo;

import com.icis.SpringBootStart;
import com.icis.mapper.UserInfoMapper;
import com.icis.pojo.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = SpringBootStart.class)
class DemoApplicationTests {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    void contextLoads() {
        List<UserInfo> userInfos = userInfoMapper.selectAll();
        for (UserInfo userInfo : userInfos) {
            System.out.println(userInfo);
        }
    }

}
