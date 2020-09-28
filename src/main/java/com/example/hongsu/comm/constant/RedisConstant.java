package com.example.hongsu.comm.constant;

/**
 * @author: huangxutao
 * @date: 2019-06-14
 * @description: 缓存常量
 */
public interface RedisConstant {

	/** Token缓存国企时间时间：1分钟 */
	public static final int  TOKEN_EXPIRE_TIME  = 60000;

	/**
	 * 缓存用户信息
	 */
	public static final String SYS_USERS_CACHE = "sys:cache:user";

	/**
	 * 测试缓存key
	 */
	public static final String TEST_DEMO_CACHE = "test:hongsu";

}
