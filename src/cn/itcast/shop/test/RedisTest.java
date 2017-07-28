package cn.itcast.shop.test;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import cn.itcast.shop.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;

public class RedisTest {

	@Test
	public void test1() {

		// 获取配置文件信息
		Properties properties = new Properties();
		try {
			properties.load(RedisTest.class.getResourceAsStream("/redis.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = properties.getProperty("url");
		int port = Integer.parseInt(properties.getProperty("port"));
		int maxIdle = Integer.parseInt(properties.getProperty("maxIdle"));
		int minIdle = Integer.parseInt(properties.getProperty("minIdle"));
		int maxTotal = Integer.parseInt(properties.getProperty("maxTotal"));
		System.out.println(url);
		System.out.println(port);
		System.out.println(maxIdle);
		System.out.println(minIdle);
		System.out.println(maxTotal);
		// 创建连接池配置对象
		// JedisPoolConfig poolConfig = new JedisPoolConfig();
		// poolConfig.setMaxIdle(30);
		// poolConfig.setMinIdle(10);
		// poolConfig.setMaxTotal(50);

		// 创建连接池对象
		// JedisPool pool = new JedisPool("localhost", 6379);

		// 从连接池获取资源
		// Jedis jedis = pool.getResource();

		// 操作数据库
		// jedis.set("username", "nick");
	}

	@Test
	public void test2() {
		Jedis jedis = JedisPoolUtils.getJedis();
		System.out.println(jedis.get("username"));
	}
}
