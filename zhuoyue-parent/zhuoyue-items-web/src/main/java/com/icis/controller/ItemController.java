package com.icis.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icis.bean.SkuAttrValue;
import com.icis.bean.SkuImage;
import com.icis.bean.SkuInfo;
import com.icis.bean.SpuSaleAttr;
import com.icis.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ItemController {
    @Reference
    private SkuService skuService;

    private final ObjectMapper mapper = new ObjectMapper();


    //接收skuid  查询数据模型  渲染到  item.html
    //100016053470.html
    @RequestMapping("/{skuId}.html")
    public String getItemBySkuid(@PathVariable(value = "skuId") String skuId, Model model) throws JsonProcessingException {
      /*  System.out.println(skuId);
        model.addAttribute("skuId",skuId);*/
        //根据skuid 查询当前sku的基本数据
        SkuInfo skuInfo = this.skuService.getSkuBySkuId(skuId);
       // List<SkuImage> skuImages = this.skuService.getSkuImgByskuId(skuId);//sku的图片列表
        //存储
        model.addAttribute("skuInfo", skuInfo);
      //  model.addAttribute("skuImages", skuImages);
        //根据skuId和spuid查询销售属性和销售属性值(查询当前sku所属的spu的销售属性和销售属性值  去看看京东你就懂了)
        List<SpuSaleAttr> spuSaleAttrList = this.skuService.getSpuSaleAttrList(skuId, skuInfo.getSpuId());
        //存储销售属性和销售属性值
        model.addAttribute("spuSaleAttrList", spuSaleAttrList);
        //根据spu_id 61  查询所有的sku 对应的销售属性值   比如:你点进去的是默认的64GB+黑色  你想换一个sku,你点击了白色 就变成了64GB+白色
        Map<String, Object> jsonMap = this.skuService.getSkuSaleAttrrListBySpuId(skuInfo.getSpuId());

        //把map集合转化为字符串
        String strJsonMap = mapper.writeValueAsString(jsonMap);
        //存储      与前端一致
        model.addAttribute("valuesSkuJson",strJsonMap);

        return "item";
    }
}
