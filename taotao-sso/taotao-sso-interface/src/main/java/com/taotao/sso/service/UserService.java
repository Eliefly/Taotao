package com.taotao.sso.service;

import com.taotao.pojo.User;

/**
 * UserService
 *
 * @author eliefly
 * @date 2018-01-10
 */
public interface UserService {

    /**
     * 检查数据是否有用
     *
     * @param param 要验证的内容
     * @param type  要验证数据的类型. 可选参数1、2、3分别代表username、phone、email.
     * @return true：数据可用，false：数据不可用
     */
    Boolean check(String param, Integer type);

    /**
     * 通过ticker查询用户
     *
     * @param ticket redis保存用户的键数据
     * @return 查询的用户数据
     */
    User queryUserByTicker(String ticket);

    /**
     * 注册用户
     *
     * @param user 用户参数
     */
    void doRegister(User user);

    /**
     * 用户登录
     *
     * @param user 用户参数
     * @return 是否已登录的标识
     */
    String doLogin(User user);
}

