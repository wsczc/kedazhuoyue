package com.icis.controller;

import com.icis.pojo.UserInfo;
import com.icis.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  //@Controller+@ResponseBody
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    //查询全部结果，select(null)方法能达到同样的效果
    @GetMapping("findAll")
    public List<UserInfo> findAll(){
        List<UserInfo> all = this.userInfoService.getAll();
        return  all;
    }


    //根据实体中的属性值进行查询，查询条件使用等号
    @GetMapping("findBy")
    public List<UserInfo> findA(UserInfo userInfo){
      return   this.userInfoService.getAll2(userInfo);
    }

    //模糊查询
    @GetMapping("findBySome")
    public List<UserInfo> findN(UserInfo userInfo){
     return    this.userInfoService.getAll3(userInfo);
    }


    //根据实体中的属性查询总数，查询条件使用等号
    @GetMapping("getCount")
    public Integer getCount( UserInfo userInfo){
        return  this.userInfoService.getCount(userInfo);
    }


    /*--------------------------------------------------*/

    //添加用户  前端传递的是json数据  需要用@RequestBody
    @PostMapping("addUser")
    public String addUser(@RequestBody UserInfo userInfo){
        userInfoService.saveUser(userInfo);
        //需要返回多组数据  1.Map集合   2.自己定义类
        return "ok";
    }

    //有选择性的保存用户
    //添加用户  前端传递的是json数据  需要用@RequestBody
    @PostMapping("addUserSelect")
    public String addUserSelect(@RequestBody UserInfo userInfo){
        userInfoService.addUserSelect(userInfo);
        //需要返回多组数据  1.Map集合   2.自己定义类
        return "ok";
    }
/*--------------------------------------*/
    //更新用户数据
    //根据主键更新 如果实体类没有传主键  不执行任何操作
    @PostMapping("updateUser")
    public String updateUser(@RequestBody UserInfo userInfo){
        userInfoService.updateUser(userInfo);
        //需要返回多组数据  1.Map集合   2.自己定义类
        return "update ok";
    }

    //选择性更新 根据主键更新属性不为null的值  如果是null  不作处理
    @PostMapping("updateUser2")
    public String updateUser2(@RequestBody UserInfo userInfo){
        userInfoService.updateUser2(userInfo);
        //需要返回多组数据  1.Map集合   2.自己定义类
        return "update ok";
    }

    //按照条件更新  更新名字有猪的
    @PostMapping("updateByName")
    public String updateByName( UserInfo userInfo){
        userInfoService.updateUserByName(userInfo);
        return "update ok";
    }
    /*//JSON格式
     @PostMapping("updateByName")
    public String updateByName(@RequestBody UserInfo userInfo){
        userInfoService.updateUserByName(userInfo);
        return "update ok";
    }*/

    /*------------------------------*/

    //根据主键字段进行删除，方法参数必须包含完整的主键属性
    @GetMapping("delUserByPrimarykey")
    public String delUserByPrimarykey(UserInfo userInfo){
        this.userInfoService.delUserByPrimarykey(userInfo);
        return "del ok";
    }

    //根据实体属性作为条件进行删除，查询条件使用等号
    @GetMapping("delUserBy")
    public String delUserBy(UserInfo userInfo){
        this.userInfoService.delUserBy(userInfo);
        return "del ok";
    }


}
