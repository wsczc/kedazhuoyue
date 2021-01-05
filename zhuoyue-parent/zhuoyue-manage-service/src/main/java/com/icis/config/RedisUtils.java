package com.icis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//Redis配置类
@Configuration
public class RedisUtils {
    @Value("${redis.MaxTotal}")
    private Integer maxTotal;
    @Value("${redis.MinIdle}")
    private Integer minIdle;
    @Value("${redis.MaxWaitMillis}")
    private Long maxWaitMills;
    @Value("${redis.TestOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${redis.BlockWhenExhausted}")
    private Boolean blockWhenExhausted;
    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private Integer redisport;


    //JedisPool 操作redis的连接池
    //创建一个连接池
    private static JedisPool jedisPool;
    //初始化连接池
    @Bean
    public JedisPool getJedisPool() {
        //创建一个配置类
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //配置
        jedisPoolConfig.setMaxTotal(200);
        jedisPoolConfig.setMinIdle(20);
        jedisPoolConfig.setMaxWaitMillis(10000);
        //检测获得的连接是否可用
        jedisPoolConfig.setTestOnBorrow(true);
        //当连接池没有连接可用的时候等待
        jedisPoolConfig.setBlockWhenExhausted(true);

        jedisPool=new JedisPool(jedisPoolConfig,"192.168.231.133",6379);
        return jedisPool;

    }
    //定义方法  根据连接池获得连接 因为连接很多 所以不能把Jedis直接放进spring工厂

    /*public Jedis getJedis(){
        Jedis jedis=null;
        if (jedisPool!=null){
            //获得连接
            jedis=jedisPool.getResource();
        }

        return jedis;
    }*/
}
