package com.jesper.lock;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/1/5.
 *
 * 锁分配接口
 */
public interface IDistributedLocker {
    void lock(String lockKey);

    void unlock(String lockKey);

    void lock(String lockKey, int timeout);

    void lock(String lockKey, TimeUnit unit, int timeout);

    void deleteLock(String lockKey);

    boolean isExistLock(String lockKey);
}
