package com.icis.mapper;

import com.icis.pojo.UserInfo;
import tk.mybatis.mapper.common.Mapper;

//用户接口  当前你的业务接口继承自通用Mapper提供的接口  泛型就是你要操作的实体类
public interface UserInfoMapper extends Mapper<UserInfo> {
}
