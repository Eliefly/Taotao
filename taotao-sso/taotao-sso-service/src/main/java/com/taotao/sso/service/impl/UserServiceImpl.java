package com.taotao.sso.service.impl;

import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.UserMapper;
import com.taotao.pojo.User;
import com.taotao.redis.impl.JedisClientCluster;
import com.taotao.sso.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * UserServiceImpl
 *
 * @author eliefly
 * @date 2018-01-10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisClientCluster jedisClientCluster;

    @Value("${SSO_TAOTAO_TICKET_KEY}")
    private String ssoTaoTaoTicketKey;

    @Value("${SSO_TAOTAO_TICKET_INCR}")
    private String ssoTaotaoTicketIncr;

    @Override
    public Boolean check(String param, Integer type) {

        User user = new User();

        switch (type) {
            case 1:
                user.setUsername(param);
                break;
            case 2:
                user.setPhone(param);
                break;
            case 3:
                user.setEmail(param);
                break;
            default:
                break;
        }
        // 如果找到一个就说明已被使用
        User findUser = userMapper.selectOne(user);

        if (findUser != null) {
            return false;
        }

        return true;
    }

    @Override
    public User queryUserByTicker(String ticket) {

        User user = null;

        String key = ssoTaoTaoTicketKey + ticket;

        String jsonStr = jedisClientCluster.get(key);

        // 如果不为空这表示已登录
        if (StringUtils.isNotBlank(jsonStr)) {

            // 如果用户要检查登录，说说用户是活动状态，这时要重置用户登录有效时间
            jedisClientCluster.expire(key, 60 * 60);
            // 把json串转化为对象
            user = JsonUtils.jsonToPojo(jsonStr, User.class);

        }

        return user;
    }

    @Override
    public void doRegister(User user) {

        user.setCreated(new Date());
        user.setUpdated(user.getCreated());

        // 需要给用户密码进行加密，保证密码安全，我们使用MD5加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        // 保存用户
        userMapper.insert(user);

    }

    @Override
    public String doLogin(User user) {

        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        User loginUser = userMapper.selectOne(user);

        try {
            if (loginUser != null) {

                // 生成唯一数ticket,可是使用redis的唯一数+"_"+用户id (SSO_TAOTAO_TICKET_KEY_12_9)
                String ticket = "" + jedisClientCluster.incr(ssoTaotaoTicketIncr) + "_" + loginUser.getId();

                // 把ticket和用户数据放到redis中,模拟session，原来的session有效时间是半小时
                jedisClientCluster.set(ssoTaoTaoTicketKey + ticket, JsonUtils.objectToJson(loginUser), 60 * 30);

                return ticket;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果查询用户为空
        return null;
    }
}
