package com.icis.mapper;

import com.icis.bean.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue> {
    List<SkuSaleAttrValue> getSkuSaleAttrrListBySpuId(String spuId);
}
