package com.jesper.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 锁的使用接口实例化
 */
public class RedissonDistributedLocker implements IDistributedLocker {

    private RedissonClient redissonClient;

    @Override
    public void lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
    }

    @Override
    public void deleteLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.delete();
    }

    @Override
    public boolean isExistLock(String lockKey) {
        return redissonClient.getLock(lockKey).isExists();
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}
