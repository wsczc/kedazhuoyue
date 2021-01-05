package com.icis.service;

import com.icis.pojo.UserInfo;

import java.util.List;

public interface UserInfoService {
    //查询所有
    public List<UserInfo> getAll();
    //条件查询
    List<UserInfo> getAll2(UserInfo userInfo);
    //模糊查询
    List<UserInfo> getAll3(UserInfo userInfo);

    //查询总数
    Integer getCount(UserInfo userInfo);

    //保存用户
    void saveUser(UserInfo userInfo);

    void addUserSelect(UserInfo userInfo);

    void updateUser(UserInfo userInfo);

    void updateUser2(UserInfo userInfo);

    void updateUserByName(UserInfo userInfo);

    void delUserByPrimarykey(UserInfo userInfo);

    void delUserBy(UserInfo userInfo);
}
