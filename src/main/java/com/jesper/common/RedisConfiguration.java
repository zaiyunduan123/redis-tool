package com.jesper.common;

import com.jesper.lock.IDistributedLocker;
import com.jesper.lock.LockUtil;
import com.jesper.lock.RedissonDistributedLocker;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 自动配置类
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Value("${redis.host}")
    public String host;

    @Value("${redis.port}")
    public String port;

    /**
     * 将redis连接池注入spring容器
     * @return
     */
    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisProperties.getPoolMaxIdle());
        config.setMaxTotal(redisProperties.getPoolMaxTotal());
        config.setMaxWaitMillis(redisProperties.getPoolMaxWait() * 1000);
        JedisPool jp = new JedisPool(config, redisProperties.getHost(), redisProperties.getPort(),
                redisProperties.getTimeout()*1000, null, 0);
        return jp;
    }


    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     * @return
     */
    @Bean
    IDistributedLocker distributedLocker() {
        Config config = new Config();
       //指定使用单节点部署方式
        StringBuffer sb = new StringBuffer("redis://");
        sb.append(host).append(":").append(port);
        config.useSingleServer().setAddress(sb.toString());
        RedissonClient redissonClient = Redisson.create(config);
        RedissonDistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);
        LockUtil.setLocker(locker);
        return locker;
    }
}
