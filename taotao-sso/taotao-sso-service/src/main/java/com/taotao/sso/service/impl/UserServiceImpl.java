package com.taotao.sso.service.impl;

import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.UserMapper;
import com.taotao.pojo.User;
import com.taotao.redis.impl.JedisClientCluster;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("${SSO_TICKET_KEY}")
    private String ssoTicketKey;

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

        String key = ssoTicketKey + ticket;

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
}
