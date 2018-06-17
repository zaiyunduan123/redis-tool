package com.jesper.lock;

import java.util.concurrent.TimeUnit;

/**
 * redis锁工具类
 */
public class RedisLockUtil {
    private static IDistributedLocker redissLock;

    /**
     * 初始化redisson客户端
     * @param locker
     */
    public static void setLocker(IDistributedLocker locker) {
        redissLock = locker;
    }

    /**
     * 上锁
     * @param lockKey
     */
    public static void lock(String lockKey) {
        redissLock.lock(lockKey);
    }

    /**
     * 释放锁
     * @param lockKey
     */
    public static void unlock(String lockKey) {
        redissLock.unlock(lockKey);
    }

    /**
     * 删除锁
     * @param lockKey
     */
    public static void deleteLock(String lockKey) {
        redissLock.deleteLock(lockKey);
    }

    /**
     * 带超时的锁
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    public static void lock(String lockKey, int timeout) {
        redissLock.lock(lockKey, timeout);
    }

    /**
     * 带超时的锁
     * @param lockKey
     * @param unit 时间单位
     * @param timeout 超时时间
     */
    public static void lock(String lockKey, TimeUnit unit , int timeout) {
        redissLock.lock(lockKey, unit, timeout);
    }

    /**
     * 是否已经存在锁
     * @param lockKey
     * @return
     */
    public static boolean isExistLock(String lockKey){
        return redissLock.isExistLock(lockKey);
    }
}
