package cn.itcast.shop.utils;

import java.io.IOException;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {

	private static JedisPool pool = null;

	static {
		// 获取配置文件信息
		Properties properties = new Properties();
		try {
			properties.load(
					JedisPoolUtils.class.getClassLoader().getResourceAsStream("redis.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
		config.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
		config.setMinIdle(Integer.parseInt(properties.getProperty("maxIdle")));
		pool = new JedisPool(config, properties.getProperty("url"),
				Integer.parseInt(properties.getProperty("port")));
	}

	// 获取连接资源对象
	public static Jedis getJedis() {
		return pool.getResource();
	}
}
