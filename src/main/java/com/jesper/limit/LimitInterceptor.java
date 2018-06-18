package com.jesper.limit;

import com.alibaba.fastjson.JSON;
import com.jesper.common.RedisUtil;
import com.jesper.prefix.PrefixKey;
import com.jesper.result.CodeMsg;
import com.jesper.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by jiangyunxiong on 2018/6/18.
 */
public class LimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            RedisLimit accessLimit = hm.getMethodAnnotation(RedisLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            String key = request.getRequestURI();
            PrefixKey pk = PrefixKey.with(seconds, "limit");
            Integer count = redisUtil.get(pk, key, Integer.class);
            if (count == null) {
                redisUtil.set(pk, key, 1);
            } else if (count < maxCount) {
                redisUtil.incr(pk, key);
            } else {
                render(response, CodeMsg.LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
