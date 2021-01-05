package com.icis.consts;
//存储Redis  key  操作的常量
public class RedisKeyConsts {
    //前缀
    public static final String SKUKEY_PREFIX="sku:";
    //后缀
    public static final String SKUKEY_SUFFIX=":info";
    //这是一个key  的声明周期
    public static final int SKUKEY_TIMEOUT=24*60*60;
    public static final int NOTFINDSKUKEY_TIMEOUT=1/100;

}
