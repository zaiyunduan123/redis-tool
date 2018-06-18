package com.jesper;

import com.jesper.common.RedisUtil;
import com.jesper.limit.RedisLimit;
import com.jesper.prefix.PrefixKey;
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

    @Test
    @RedisLimit(seconds = 5, maxCount = 5)
    public void test() {
        PrefixKey pk = PrefixKey.with(3600, "jyxc");
        redisUtil.set(pk, "q", "jesper");
        redisUtil.lock("123");
        int i = 1 + 3;
        int j = 2 + 4;
        redisUtil.unlock("123");
        String result = redisUtil.get(pk, "q", String.class);
        System.out.println(result);
    }
}
