package cn.itcast.shop.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {

	@Test
	public void test1() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.set("username", "nick");
	}
}
