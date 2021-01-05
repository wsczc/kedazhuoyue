package com.icis.service.impl;

import com.icis.mapper.UserInfoMapper;
import com.icis.pojo.UserInfo;
import com.icis.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    //查询全部结果，select(null)方法能达到同样的效果
    @Override
    public List<UserInfo> getAll() {
        return this.userInfoMapper.selectAll();
    }

    //根据实体中的属性值进行查询，查询条件使用等号
    @Override
    public List<UserInfo> getAll2(UserInfo userInfo) {
        return this.userInfoMapper.select(userInfo);
    }


    //模糊查询
    @Override
    public List<UserInfo> getAll3(UserInfo userInfo) {
        //构建一个Example条件查询对象
        Example example=new Example(UserInfo.class);
        //构建条件
        Example.Criteria criteria=example.createCriteria();
        //添加具体条件 property 字段名  字段值
        criteria.andLike("name","%"+userInfo.getName()+"%");
        return this.userInfoMapper.selectByExample(example);
    }

    //根据实体中的属性查询总数，查询条件使用等号
    @Override
    public Integer getCount(UserInfo userInfo) {
        return userInfoMapper.selectCount(userInfo);
    }

    /*----------------*/

    @Override
    public void saveUser(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    @Override
    public void addUserSelect(UserInfo userInfo) {
        userInfoMapper.insertSelective(userInfo);
    }

    /*-----------------------------*/
    @Override
    public void updateUser(UserInfo userInfo) {
        //更新  根据主键更新
        userInfoMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    public void updateUser2(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public void updateUserByName(UserInfo userInfo) {

        Example example=new Example(UserInfo.class);
        //构建条件
        example.createCriteria().andLike("name","%"+userInfo.getName()+"%");
        //根据条件更新数据
        userInfoMapper.updateByExample(userInfo,example);
    }

    @Override
    public void delUserByPrimarykey(UserInfo userInfo) {
        userInfoMapper.deleteByPrimaryKey(userInfo);
    }

    @Override
    public void delUserBy(UserInfo userInfo) {
        userInfoMapper.delete(userInfo);
    }
}
