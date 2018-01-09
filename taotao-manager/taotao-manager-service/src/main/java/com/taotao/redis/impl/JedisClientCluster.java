package com.taotao.redis.impl;

import com.taotao.redis.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/**
 * 集群版redis操作
 * 关闭 jedisCluster 连接(在程序执行完之后,才能关闭,
 * 它的内部已经封装了连接池，所以与spring整合后，不需要关闭)
 *
 * @author eliefly
 * @date 2018-01-09
 */
public class JedisClientCluster implements RedisOperator {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public void del(String key) {
        jedisCluster.del(key);
    }

    @Override
    public void expire(String key, Integer seconds) {
        jedisCluster.expire(key, seconds);
    }

    @Override
    public void set(String key, String value, Integer seconds) {
        jedisCluster.set(key, value);
        jedisCluster.expire(key, seconds);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

}
