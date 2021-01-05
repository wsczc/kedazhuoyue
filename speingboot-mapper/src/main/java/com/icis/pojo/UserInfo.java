package com.icis.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data   //提供get/set等方法
public class UserInfo implements Serializable {
    //@Id 用于标注这个属性对应的是主键字段
    @Id
    //@Column  注解实体类字段和数据库表字段映射关系
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)//添加数据完成后生成的主键赋值给对应字段
    private String id;
    @Column
    private String loginName;
    @Column
    private String nickName;
    @Column
    private String passwd;
    @Column
    private String name;
    @Column
    private String phoneNum;
    @Column
    private String email;
    @Column
    private String headImg;
    @Column
    private String userLevel;
}
