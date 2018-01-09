package com.taotao.jdedis.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;

/**
 * JedisClusterTest
 *
 * @author eliefly
 * @date 2018-01-08
 */
public class JedisClusterTest {

    @Test
    public void test01() {

        HashSet<HostAndPort> nodes = new HashSet<>();

        nodes.add(new HostAndPort("192.168.56.101", 7001));
        nodes.add(new HostAndPort("192.168.56.101", 7002));
        nodes.add(new HostAndPort("192.168.56.101", 7003));
        nodes.add(new HostAndPort("192.168.56.101", 7004));
        nodes.add(new HostAndPort("192.168.56.101", 7005));
        nodes.add(new HostAndPort("192.168.56.101", 7006));

        // 创建集群连接
        JedisCluster jedisCluster = new JedisCluster(nodes);

        // 操作redis数据库和普通jedis一样
        String key = "jedisCluster";
//        jedisCluster.set(key, "hello jedis cluster!");
        String value = jedisCluster.get(key);
        System.out.println(key + ":" + value);

        // 关闭 jedisCluster 连接(在程序执行完之后,才能关闭,他的内部已经封装了连接池)
        jedisCluster.close();

    }

}
