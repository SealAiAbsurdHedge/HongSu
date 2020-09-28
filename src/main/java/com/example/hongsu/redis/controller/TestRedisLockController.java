package com.example.hongsu.redis.controller;

import antlr.StringUtils;
import com.example.hongsu.redis.service.RedisLockService;
import com.example.hongsu.util.JedisUtil;
import com.example.hongsu.util.SnowFlakeUtil;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping(value = "/redisLock")
public class TestRedisLockController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisLockService redisLockService;


    private final static String TEST_TRY_LOCK = "try_Lock_key";


    @RequestMapping("tryLock")
    public String tryLock(String lockKey, String requestId){
        /*Jedis jedis = new Jedis("122.51.3.44",6379);
        jedis.auth("wj123456");*/
        boolean flag = redisLockService.tryLock(TEST_TRY_LOCK,requestId,100);
        if(flag){
            logger.info("测试分布式加锁------成功！");
            return "success";
        }else {
            logger.info("测试分布式加锁------失败！");
            return "fail";
        }
    }

    @RequestMapping("relLock")
    public String relLock(String lockKey, String requestId){
        boolean flag = redisLockService.relLock(lockKey,requestId);
        if(flag){
            logger.info("测试分布式释放------成功！");
            return "success";
        }else {
            logger.info("测试分布式释放------失败！");
            return "fail";
        }
    }
}
