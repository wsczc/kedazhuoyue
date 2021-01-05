package com.icis.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.icis.bean.BaseSaleAttr;
import com.icis.bean.SpuInfo;
import com.icis.bean.SpuSaleAttr;
import com.icis.service.ManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//spu控制器  销售属性
@RestController
@CrossOrigin
public class SpuController {

    @Reference
    private ManageService manageService;

    //根据三级分类id  查询spu集合 http://localhost:8082/spuList?catalog3Id=1
    @RequestMapping("/spuList")
    public List<SpuInfo> getSpuList(SpuInfo spuInfo){
        List<SpuInfo> spuList = manageService.getSpuList(spuInfo);
        return spuList;
    }

    //从字典表中获得 销售属性
    @RequestMapping("/baseSaleAttrList")
    public List<BaseSaleAttr> getBaseSaleAttrList(){
            return this.manageService.getBaseSaleAttrList();
    }

    //保存spu  http://localhost:8082/saveSpuInfo
    @RequestMapping("/saveSpuInfo")
    public void SaveSpuInfo(@RequestBody SpuInfo spuInfo){
        this.manageService.saveSpuInfo(spuInfo);

    }


//根据spuId获得销售属性和销售属性值  http://localhost:8082/spuSaleAttrList?spuId=58
    @RequestMapping("/spuSaleAttrList")
    public List<SpuSaleAttr> getspuSaleAttrList(String spuId){
        return this.manageService.getspuSaleAttrList(spuId);

    }





}
