package com.icis.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.icis.bean.SkuInfo;
import com.icis.bean.SpuImage;
import com.icis.bean.SpuInfo;
import com.icis.bean.SpuSaleAttr;
import com.icis.service.ManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//sku控制器
@RestController
@CrossOrigin
public class SkuController {

    @Reference
    private ManageService manageService;


    //根据spuId获得图片列表  http://localhost:8082/spuImageList?spuId=58
    @RequestMapping("/spuImageList")
    public List<SpuImage> getspuImageList(SpuImage spuImage){
            return  this.manageService.getspuImageList(spuImage);
    }


    //根据三级分类id获得平台属性和平台属性值  http://localhost:8082/attrInfoList?catalog3Id=61
    //在ManageController


    //保存 sku  http://localhost:8082/saveSkuInfo
    @RequestMapping("/saveSkuInfo")
    public void saveSkuInfo(@RequestBody  SkuInfo skuInfo){
        this.manageService.saveSkuInfo(skuInfo);
    }



}
