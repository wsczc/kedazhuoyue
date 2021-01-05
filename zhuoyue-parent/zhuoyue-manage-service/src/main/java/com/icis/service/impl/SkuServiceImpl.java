package com.icis.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icis.bean.SkuImage;
import com.icis.bean.SkuInfo;
import com.icis.bean.SkuSaleAttrValue;
import com.icis.bean.SpuSaleAttr;
import com.icis.consts.RedisKeyConsts;
import com.icis.mapper.SkuImageMapper;
import com.icis.mapper.SkuInfoMapper;
import com.icis.mapper.SkuSaleAttrValueMapper;
import com.icis.mapper.SpuSaleAttrMapper;
import com.icis.service.SkuService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service//放到注册中心
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    //注入Redis工具类
    @Autowired
    private JedisPool jedisPool;
    //定义一个 ObjectMapper 转化json
    private static  final ObjectMapper MAPPER=new ObjectMapper();

    //根据skuId查询sku基本信息
    //存入缓存
    @Override
    public SkuInfo getSkuBySkuId(String skuId) {
        return getSkuInfoFromDbRedission(skuId);
    }

    //根据skuId查询sku基本信息
    //从数据库获得商品详情
    private SkuInfo getSkuInfoFromDb(String skuId) {
        //skuInfo 没有图片信息
        //获得图片信息
        List<SkuImage> skuimages = getSkuImgByskuId(skuId);
        //设置图片信息
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);
        skuInfo.setSkuImageList(skuimages);
        return skuInfo;
    }
    //加上分布式锁  获得skuinfo  解决缓存击穿
    private SkuInfo getSkuInfoFromDbRedission(String skuId){

        //业务代码
        SkuInfo skuInfo=null;
        Jedis jedis=null;
        RLock myLock=null;
        try {
            //1.创建一个配置对象
            Config config=new Config();
            //2.配置redis服务器地址  单机   如果是集群的,就配置集群的
            config.useSingleServer().setAddress("redis://192.168.231.133:6379");
            //3.创建client对象
            RedissonClient redissonClient = Redisson.create(config);
            //4.创建锁
             myLock = redissonClient.getLock("myLock");
            //5.加锁 获得锁的线程处理时间是10秒 在10秒期间 其他线程等待
            myLock.lock(10, TimeUnit.SECONDS);

            //skuInfo 没有图片信息   快捷键ctrl+alt+m 把代码块抽取为一个方法 就是getSkuInfoFromDb
            //鲜活的一个redis连接
            jedis = this.jedisPool.getResource();
            //定义一个key                    sku        +skuID          +      info
            String skukey= RedisKeyConsts.SKUKEY_PREFIX+skuId+RedisKeyConsts.SKUKEY_SUFFIX;
        /*思路
        //1.先从缓存中根据key 获得对应的value(存储的就是商品的json格式)
        //2.如果有数据  从缓存中获得数据  转化为对应的对象
        //3.如果没有  就从数据库中查询  放入缓存*/
            //从redis中获得数据
            String skuJson = jedis.get(skukey);

            //判断是否有值
            if (skuJson!=null && skuJson.length()>0){
                System.out.println("从缓存中取");
                //从redis中取数据   把json格式数据  转化为  java对象
                skuInfo = MAPPER.readValue(skuJson, SkuInfo.class);
                return  skuInfo;
            }else {
                System.out.println("从数据库取");
                //从数据库里查询
                skuInfo = getSkuInfoFromDb(skuId);
                //判断是否有对应的key  解决缓存穿透
                if (skuInfo==null){
                    jedis.setex(skukey,RedisKeyConsts.NOTFINDSKUKEY_TIMEOUT,null);
                    //返回一个错误页  别直接返回null
                    return skuInfo;

                }
                //存入缓存
                //转化为json格式
                String jsonSku = MAPPER.writeValueAsString(skuInfo);
                //存入缓存 设置过期时间
                jedis.setex(skukey,RedisKeyConsts.SKUKEY_TIMEOUT,jsonSku);
                return skuInfo;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关流
            //判断
            if (jedis!=null){
                jedis.close();
            }
            if (myLock!=null){
                //释放锁
                myLock.unlock();

            }

        }
        return getSkuInfoFromDb(skuId);

    }

    //根据skuId查询Sku所属图片列表
    @Override
    public List<SkuImage> getSkuImgByskuId(String skuId) {
        SkuImage skuImage=new SkuImage();
        skuImage.setSkuId(skuId);
        return this.skuImageMapper.select(skuImage);
    }

    //根据skuId和spuid查询销售属性和销售属性值
    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(String skuId,String spuId) {
        return this.spuSaleAttrMapper.getSpusaleAttrListSku(skuId,spuId);
    }

    //  根据spu_id 61  查询其所有的sku 对应的销售属性值
    // Map(key,value)  key:销售属性值的Id组合;value:skuId   "113|115":61
    @Override
    public Map<String, Object> getSkuSaleAttrrListBySpuId(String spuId) {
        //定义一个map集合
        Map<String,Object> jsonMap=new HashMap<>();
        //根据spuId查询数据
      List<SkuSaleAttrValue>  saleAttrValueList= this.skuSaleAttrValueMapper.getSkuSaleAttrrListBySpuId(spuId);
       // System.out.println("数据:"+saleAttrValueList);
        //把list集合中的 销售属性值id  拼接为 map的key,skuId 拼接为 map的value
        //1.什么时候开始拼接?什么时候结束?    --skuId改变就重新拼接
        //定义一个key
        String key="";
        for (int i = 0; i < saleAttrValueList.size(); i++) {
            //获得当前对象
            SkuSaleAttrValue skuSaleAttrValue = saleAttrValueList.get(i);
            //判断key  长度 大于0时候 才拼接 |
            if (key.length()>0){
                //拼接 key  后面加上 |
                key=key+"|";
            }
            key=key+skuSaleAttrValue.getSaleAttrValueId();
            //结束拼接
                                                     //当当前对象的skuId不等于下一个对象的skuID
            if ((i+1)==saleAttrValueList.size() || !skuSaleAttrValue.getSkuId().equals(saleAttrValueList.get(i+1).getSkuId())){
                //把对应的key 和 value存入到map集合中
                jsonMap.put(key,skuSaleAttrValue.getSkuId());
                //置空key  下一轮拼接
                key="";
            }
        }

        //System.out.println(jsonMap);
        return jsonMap;
    }
}
