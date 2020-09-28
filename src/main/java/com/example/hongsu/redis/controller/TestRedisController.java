package com.example.hongsu.redis.controller;/* *
 *  @author:WJ
 *  @date: 2020-04-07 15:02
 *  @describe:
 * */

import com.example.hongsu.comm.constant.RedisConstant;
import com.example.hongsu.redis.service.RedisLockService;
import com.example.hongsu.system.entity.UserBean;
import com.example.hongsu.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping(value = "/redis")
public class TestRedisController {


    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("set")
    public boolean redisset(String key, String value){
        UserBean userEntity =new UserBean();
        userEntity.setId(Long.valueOf(1));
        userEntity.setUserName("zhangsan");
        userEntity.setPassword(String.valueOf(123456));

        redisUtil.set("user",userEntity);
        redisUtil.expire("user", RedisConstant.TOKEN_EXPIRE_TIME);
        return true;
    }
    @RequestMapping("get")
    public Object redisget(String key){
        return redisUtil.get(key);
    }

    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,1);
    }
}
