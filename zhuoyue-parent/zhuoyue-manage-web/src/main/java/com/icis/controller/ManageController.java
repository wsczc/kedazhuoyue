package com.icis.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.icis.bean.*;
import com.icis.service.ManageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//后台管理控制器
@RestController
//解决跨域问题
@CrossOrigin
public class ManageController {
    //应用服务
    @Reference
    private ManageService manageService;

    //构建请求路径  获得一级分类列表
    @PostMapping("/getCatalog1")
    public List<BaseCatalog1> getCatalog1(){
        //获得一级分类
        List<BaseCatalog1> ca1 = this.manageService.getCa1();

        return ca1;
    }

    //构建请求路径  获得二级分类列表 getCatalog2?catalog1Id=3
    @RequestMapping("/getCatalog2")
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
        //获得二级分类
        List<BaseCatalog2> ca2 = this.manageService.getCa2(catalog1Id);

        return ca2;
    }

    //查询三级  getCatalog3?catalog2Id=1
    @RequestMapping("/getCatalog3")
    public List<BaseCatalog3> getCatalog3(String catalog2Id){
        //获得二级分类
        List<BaseCatalog3> ca3 = this.manageService.getCa3(catalog2Id);

        return ca3;
    }

    //根据三级分类获得平台属性列表   attrInfoList?catalog3Id=5
    @RequestMapping("/attrInfoList")
    public List<BaseAttrInfo> getAttrInfoList(String catalog3Id){
        //根据三级分类获得平台属性  单表  获取不到平台属性值
       // List<BaseAttrInfo> baseAttrInfo = this.manageService.getBaseAttrInfo(catalog3Id);
        List<BaseAttrInfo> baseAttrInfo =   this.manageService.attrInfoList(catalog3Id);
        return baseAttrInfo;

    }

    //添加平台属性和属性值
    @RequestMapping("/saveAttrInfo")
    public void saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
            this.manageService.saveAttrInfo(baseAttrInfo);
    }

    //查询平台属性值列表  getAttrValueList?attrId=23
    @RequestMapping("/getAttrValueList")
    public List<BaseAttrValue>  getAttrValueList(String attrId){
        return   this.manageService.getAttrValueList(attrId);

    }

}
