package com.taotao.redis;

/**
 * Redis操作结果
 *
 * @author eliefly
 * @date 2018-01-09
 */
public interface RedisOperator {

    /**
     * 保存
     *
     * @param key 键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 根据key查询
     *
     * @param key 键
     * @return 值
     */
    String get(String key);

    /**
     * 删除
     *
     * @param key 键
     */
    void del(String key);

    /**
     * 根据key设置生存时间
     *
     * @param key 键
     * @param seconds 生存时间
     */
    void expire(String key, Integer seconds);

    /**
     * 保存并设置生存时间
     * @param key 键
     * @param value 值
     * @param seconds 生存时间
     */
    void set(String key, String value, Integer seconds);

    /**
     * value加一
     *
     * @param key 生存时间
     * @return 值
     */
    Long incr(String key);

}
