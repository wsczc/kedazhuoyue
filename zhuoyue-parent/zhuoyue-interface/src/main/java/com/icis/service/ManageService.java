package com.icis.service;

import com.icis.bean.*;

import java.util.List;

public interface ManageService {
    //查询一级分类列表
    List<BaseCatalog1> getCa1();
    //根据一级分类查询二级分类
    List<BaseCatalog2> getCa2(String catalog1Id);
    //根据二级分类查询三级分类
    List<BaseCatalog3> getCa3(String catalog2Id);
    //根据三级分类查询平安属性
    List<BaseAttrInfo> getBaseAttrInfo(String catalog3Id);
    //保存平台属性
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    //根据平台属性查询平台属性值
    List<BaseAttrValue> getAttrValueList(String attrId);

    //查询spu
    List<SpuInfo> getSpuList(SpuInfo spuInfo);

    // 查询基本销售属性表
    List<BaseSaleAttr> getBaseSaleAttrList();

    //保存spu
    void saveSpuInfo(SpuInfo spuInfo);

    //根据spuid获得图片
    List<SpuImage> getspuImageList(SpuImage spuImage);

    //根据三级分类id  获得平台属性和平台属性值
    List<BaseAttrInfo> attrInfoList(String catalog3Id);
    //根据spuId获得销售属性和销售属性值
    List<SpuSaleAttr> getspuSaleAttrList(String spuId);

    //保存sku
    void saveSkuInfo(SkuInfo skuInfo);
}
