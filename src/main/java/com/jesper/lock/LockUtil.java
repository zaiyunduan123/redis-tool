package com.jesper.lock;

import java.util.concurrent.TimeUnit;

/**
 * redis锁工具类
 */
public class LockUtil {
    private static IDistributedLocker redissLock;

    /**
     * 初始化redisson客户端
     * @param locker
     */
    public static void setLocker(IDistributedLocker locker) {
        redissLock = locker;
    }


    public static void lock(String lockKey) {
        redissLock.lock(lockKey);
    }


    public static void unlock(String lockKey) {
        redissLock.unlock(lockKey);
    }


    public static void deleteLock(String lockKey) {
        redissLock.deleteLock(lockKey);
    }


    public static void lock(String lockKey, int timeout) {
        redissLock.lock(lockKey, timeout);
    }


    public static void lock(String lockKey, TimeUnit unit , int timeout) {
        redissLock.lock(lockKey, unit, timeout);
    }


    public static boolean isExistLock(String lockKey){
        return redissLock.isExistLock(lockKey);
    }
}
