package com.jesper;

import com.jesper.common.RedisUtil;
import com.jesper.redis.PrefixKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by jiangyunxiong on 2018/6/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Application.class)
public class Helloworld {

    @Autowired
    private RedisUtil redisUtil;

    public static PrefixKey prefixKey = new PrefixKey(3600*24 *2,"board");

    @Test
    public void test() {
        redisUtil.set(prefixKey,"q","jesper");
        redisUtil.lock("123");
        int i = 1 + 3;
        int j = 2 + 4;
        redisUtil.unlock("123");
        String result = redisUtil.get(prefixKey,"q",String.class);
        System.out.println(result);
    }
}
