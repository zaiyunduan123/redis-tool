package com.jesper.redis;

/**
 * Created by jiangyunxiong on 2018/6/18.
 */
public class PrefixKey extends BasePrefix {

    /**
     * 防止被外面实例化
     */
    public PrefixKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
