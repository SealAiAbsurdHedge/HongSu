package com.example.hongsu.redis.service;

import com.example.hongsu.util.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;


@Service
public class RedisLockService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Long RELEASE_SUCCESS = 1L;

    @Autowired
    JedisPool jedisPool;
    @Autowired
    JedisUtil jedisUtil;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public  boolean tryLock(String lockKey, String requestId, int expireTime) {
        SetParams setParams = new SetParams();
        setParams.ex(expireTime);
        setParams.nx();
        String result = jedisUtil.action(jedis -> jedis.set(lockKey, requestId, setParams));
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean relLock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedisUtil.action(jedis -> jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId)));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

}
