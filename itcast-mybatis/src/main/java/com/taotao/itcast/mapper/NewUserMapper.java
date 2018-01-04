package com.taotao.itcast.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.itcast.pojo.User;

import java.util.List;
import java.util.Map;

public interface NewUserMapper extends Mapper<User> {

    /**
     * 分页查询用户
     * @return
     */
    List<User> queryUserByPage(Map<String, Integer> param);

}
