package com.icis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
//sku的平台属性值
@Data
public class SkuAttrValue implements Serializable {
    @Id
    @Column
    String id;

    @Column
    String attrId;

    @Column
    String valueId;

    @Column
    String skuId;

}
