package com.taotao.mapper;

/**
 * TestMapper
 *
 * @author eliefly
 * @version 2018-01-05
 */
public interface TestMapper {

    /**
     * 从数据库中获取当前的时间
     *
     * @return 当前的时间
     */
    String queryNow();

}
