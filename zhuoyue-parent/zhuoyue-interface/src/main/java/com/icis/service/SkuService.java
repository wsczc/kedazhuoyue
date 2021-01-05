package com.icis.service;

import com.icis.bean.SkuImage;
import com.icis.bean.SkuInfo;
import com.icis.bean.SpuSaleAttr;

import java.util.List;
import java.util.Map;

//Sku服务接口
public interface SkuService {
    //根据skuId查询sku数据
        public SkuInfo getSkuBySkuId(String skuId);
    //根据skuId查询当前sku图片列表
    public List<SkuImage> getSkuImgByskuId(String skuId);

    //根据skuId和spuid查询销售属性和销售属性值
    List<SpuSaleAttr> getSpuSaleAttrList(String skuId, String spuId);

    //查询spu下的销售属性和销售属性值
    Map<String, Object> getSkuSaleAttrrListBySpuId(String spuId);


}
