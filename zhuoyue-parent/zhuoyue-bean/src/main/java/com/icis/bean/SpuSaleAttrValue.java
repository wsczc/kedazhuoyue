package com.icis.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

//销售属性值
@Data
public class SpuSaleAttrValue implements Serializable {


    @Id
    @Column
    String id ;

    @Column
    String spuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrValueName;

    //标注当前的销售属性值是否处于选中状态
    @Transient
    String isChecked;
}
