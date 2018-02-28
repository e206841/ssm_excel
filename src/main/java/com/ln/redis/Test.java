package com.ln.redis;

import redis.clients.jedis.Jedis;

public class Test {
	public static void main(String[] args) {
		Jedis jedis=new Jedis("127.0.0.1",6379);
		jedis.set("user","linan");
		String user = jedis.get("user");
		System.out.println(user);
		jedis.close();
	}
}
