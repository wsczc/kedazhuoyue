package com.icis.mapper;

import com.icis.bean.SpuSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

//基本销售属性
public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {
    //根据spuId  查询销售属性和销售属性值
    List<SpuSaleAttr> getspuSaleAttrList(String spuId);
    //根据skuId和spuid查询销售属性和销售属性值
    List<SpuSaleAttr> getSpusaleAttrListSku(@Param("skuId") String skuId,@Param("spuId") String spuId);
}
