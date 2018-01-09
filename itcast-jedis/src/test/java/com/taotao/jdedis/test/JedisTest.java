package com.taotao.jdedis.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis 使用回顾
 *
 * @author eliefly
 * @date 2018-01-08
 */
public class JedisTest {

    @Test
    public void test01() {
        Jedis jedis = new Jedis("192.168.56.101", 6379);

        String pong = jedis.ping();
        System.out.println(pong);

        String setResult = jedis.set("akey", "aaa");

        System.out.println(setResult);

        String result = jedis.get("akey");

        System.out.println(result);

        jedis.close();

    }

    @Test
    public void test02() {

        JedisPool jedisPool = new JedisPool("192.168.56.101", 6379);

        Jedis jedis = jedisPool.getResource();

        String pong = jedis.ping();

        System.out.println(pong);

        String setResult = jedis.set("bkey", "bbb");

        System.out.println(setResult);

        String result = jedis.get("bkey");

        System.out.println(result);

        jedis.close();

        jedisPool.close();

    }

}
