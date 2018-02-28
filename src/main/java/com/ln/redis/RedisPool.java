package com.ln.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池
 * @author linan
 *
 */
public class RedisPool {
	
	private static String host="127.0.0.1";
	private static Integer port=6379;
	/**
	 *  从连接池获取链接
	 * @return
	 */
	public static Jedis pool(){
		JedisPoolConfig config=new JedisPoolConfig();
		//最大连接数
		config.setMaxTotal(30);
		//空闲连接数
		config.setMaxIdle(2);
		
		JedisPool pool=new JedisPool(config, host, port);
		Jedis resource = pool.getResource();
		return resource;
	}
	
	public static void main(String[] args) {
		Jedis jedis = pool();
		jedis.set("age", "25");
		System.out.println(jedis.get("age"));
		jedis.close();
	}
}
