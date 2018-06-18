package com.jesper.common;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

}
