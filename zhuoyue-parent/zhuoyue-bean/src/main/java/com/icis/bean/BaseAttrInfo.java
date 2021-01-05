package com.icis.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class BaseAttrInfo implements Serializable {
    @Id
    @Column
    //配置主键生成策略
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String attrName;
    @Column
    private String catalog3Id;
    //构建一个平台属性值集合  attrValueList
    //通用mapper  如果实体类对应的字段和数据库没有对应关系 @Transient
    //告诉通用mapper  这个字段是临时的
    @Transient
    private List<BaseAttrValue>  attrValueList;

}
