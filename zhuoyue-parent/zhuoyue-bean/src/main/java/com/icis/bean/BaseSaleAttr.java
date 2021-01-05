package com.icis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

//基本销售属性
@Data
public class BaseSaleAttr implements Serializable {
    @Id
    @Column
    String id ;

    @Column
    String name;
}
