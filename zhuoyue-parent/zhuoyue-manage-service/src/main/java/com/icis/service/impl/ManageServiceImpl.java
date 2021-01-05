package com.icis.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.icis.bean.*;
import com.icis.mapper.*;
import com.icis.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//管理接口实现类
@Service
public class ManageServiceImpl implements ManageService {
    //注入对应mapper  在同一个系统 直接用 @Autowired
    //注入一级分类mapper
    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;
    //注入2级分类mapper
    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;
    //注入3级分类mapper
    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;
    //注入平台属性mapper
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    //注入平台属性值mapper
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    //注入spu mapper
    @Autowired
    private SpuInfoMapper spuInfoMapper;

    //注入基本销售表属性mapper spu通用,例如颜色,版本
    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    //注入销售属性mapper
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    //注入销售属性值mapper
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    //注入spu图片mapper
    @Autowired
    private SpuImageMapper spuImageMapper;

    //注入sku mapper
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    //注入sku图片 mapper
    @Autowired
    private SkuImageMapper skuImageMapper;
    //注入sku平台属性值mapper
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;
    //注入sku销售属性值mapper
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;



    //获得一级分类
    @Override
    public List<BaseCatalog1> getCa1() {
        return this.baseCatalog1Mapper.selectAll();
    }

    //获得二级分类
    @Override
    public List<BaseCatalog2> getCa2(String catalog1Id) {
        //创建二级分类对象
        BaseCatalog2 baseCatalog2=new BaseCatalog2();
        //设置一级分类id
        baseCatalog2.setCatalog1Id(catalog1Id);
        return this.baseCatalog2Mapper.select(baseCatalog2);
    }

    //获得三级分类
    @Override
    public List<BaseCatalog3> getCa3(String catalog2Id) {
        BaseCatalog3 baseCatalog3=new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        return this.baseCatalog3Mapper.select(baseCatalog3);
    }

    //根据三级分类id获得平台属性
    @Override
    public List<BaseAttrInfo> getBaseAttrInfo(String catalog3Id) {
        BaseAttrInfo baseAttrInfo=new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        return this.baseAttrInfoMapper.select(baseAttrInfo);
    }

    //添加保存平台属性和平台属性值
    @Override
    @Transactional //事务,涉及多个表
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //前端的设计  添加修改都是 同一个请求
        //如果有id 或者 id 不为空  就做修改操作
        if(baseAttrInfo.getId()!=null && baseAttrInfo.getId().length()>0){
            //执行修改平台属性
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else {
            //没有id  做添加操作
            //1.添加平台属性
            this.baseAttrInfoMapper.insertSelective(baseAttrInfo);

        }

        //先清空原有数据平台属性值  再添加
        BaseAttrValue delbaseAttrValue=new BaseAttrValue();
        delbaseAttrValue.setAttrId(baseAttrInfo.getId());
        this.baseAttrValueMapper.delete(delbaseAttrValue);


        //2.添加平台属性值
        //2.1获得对应的平台属性值
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        //2.2遍历集合  添加数据
        if (attrValueList!=null && attrValueList.size()>0){
            for (BaseAttrValue baseAttrValue : attrValueList) {
                //设置平台属性值对应的平台属性
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                    baseAttrValueMapper.insertSelective(baseAttrValue);
            }
        }

    }


    //查询平台属性值列表  getAttrValueList?attrId=97
    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        BaseAttrValue baseAttrValue=new BaseAttrValue();
        //设置条件
        baseAttrValue.setAttrId(attrId);
        //构建一平台属性对象
        return this.baseAttrValueMapper.select(baseAttrValue);
    }

    //根据三级分类id  查询spu集合 http://localhost:8082/spuList?catalog3Id=1
    @Override
    public List<SpuInfo> getSpuList(SpuInfo spuInfo) {
        return this.spuInfoMapper.select(spuInfo);
    }

    // 查询基本销售属性表
    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    //保存spu
    @Transactional
    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //判断spuinfo
        if (spuInfo!=null){
            //2.保存spu
            this.spuInfoMapper.insertSelective(spuInfo);
            //3.获得销售属性列表 保存
            List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
            if (spuSaleAttrList!=null && spuSaleAttrList.size()>0){
                for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                    //设置spuId
                    spuSaleAttr.setSpuId(spuInfo.getId());
                    //保存销售属性
                    this.spuSaleAttrMapper.insertSelective(spuSaleAttr);
                    //3.1保存完销售属性  根据销售属性获得销售属性值 保存
                    List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                    if (spuSaleAttrValueList!=null && spuSaleAttrValueList.size()>0){
                        for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                            //设置spuInfoId
                            spuSaleAttrValue.setSpuId(spuInfo.getId());
                            //保存销售属性值
                            this.spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                        }
                    }

                }

            }

            //4.获得spu图片列表  保存 注意设置SpuId
            List<SpuImage> spuImageList = spuInfo.getSpuImageList();
            if (spuImageList!=null && spuImageList.size()>0){
                for (SpuImage spuImage : spuImageList) {
                    spuImage.setSpuId(spuInfo.getId());
                    this.spuImageMapper.insertSelective(spuImage);
                }
            }



        }else {
            return;
        }

    }

    //获得spu图片列表
    @Override
    public List<SpuImage> getspuImageList(SpuImage spuImage) {
        return this.spuImageMapper.select(spuImage);
    }

    //根据三级分类id  获得平台属性和平台属性值
    @Override
    public List<BaseAttrInfo> attrInfoList(String catalog3Id) {

        return baseAttrInfoMapper.attrInfoList(catalog3Id);
    }

    //根据spuId获得销售属性和销售属性值
    @Override
    public List<SpuSaleAttr> getspuSaleAttrList(String spuId) {
        return this.spuSaleAttrMapper.getspuSaleAttrList(spuId);
    }

    //保存sku
    @Transactional
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        //1.保存sku
        if (skuInfo!=null ){
            this.skuInfoMapper.insertSelective(skuInfo);
            //2.根据sku 获得sku的平台属性值 保存
            //获得sku平台属性值
            List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
            if (skuAttrValueList!=null && skuAttrValueList.size()>0){
                for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                    skuAttrValue.setSkuId(skuInfo.getId());
                    skuAttrValueMapper.insertSelective(skuAttrValue);
                }
            }

            //3.根据sku  获得 sku的销售属性值 保存
            List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            if (skuSaleAttrValueList!=null && skuSaleAttrValueList.size()>0){
                for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                    skuSaleAttrValue.setSkuId(skuInfo.getId());
                    this.skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
                }
            }
            //4.根据sku  获得 sku的图片属性列表 保存
            List<SkuImage> skuImageList = skuInfo.getSkuImageList();
            if (skuImageList!=null && skuImageList.size()>0){
                for (SkuImage skuImage : skuImageList) {
                    skuImage.setSkuId(skuInfo.getId());
                    this.skuImageMapper.insertSelective(skuImage);

                }
            }

        }else {
            return;
        }

    }





}
