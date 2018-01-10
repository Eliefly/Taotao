package com.taotao.redis.impl;

import com.taotao.redis.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 单机版redis操作
 *
 * @author eliefly
 * @date 2018-01-09
 */
public class JedisClientPool implements RedisOperator {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void set(String key, String value) {

        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        jedis.close();
    }

    @Override
    public String get(String key) {

        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    @Override
    public void del(String key) {

        Jedis jedis = jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }

    @Override
    public void expire(String key, Integer seconds) {

        Jedis jedis = jedisPool.getResource();
        jedis.expire(key, seconds);
        jedis.close();
    }

    @Override
    public void set(String key, String value, Integer seconds) {

        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        expire(key, seconds);
        jedis.close();
    }

    @Override
    public Long incr(String key) {

        Jedis jedis = jedisPool.getResource();
        Long value = jedis.incr(key);
        jedis.close();
        return value;
    }
}
