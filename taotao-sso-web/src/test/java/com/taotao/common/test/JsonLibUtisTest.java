package com.taotao.common.test;

import com.taotao.common.util.JsonLibUtils;
import com.taotao.pojo.User;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Date;

/**
 * JsonLibUtisTest
 *
 * @author eliefly
 * @date 2018-01-12
 */
public class JsonLibUtisTest {

    @Test
    public void test01() {

        User user = new User();

        user.setId(1L);
        user.setPassword("123456");
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        user.setPhone("1233333333");

        // String jsonStr = JsonLibUtils.toJSONString(user, null);
        String jsonStr = JsonLibUtils.toJSONString(user, new String[]{"updated", "phone"});

        System.out.println(jsonStr);
    }

    @Test
    public void test02() {

        User user1 = new User();

        user1.setId(1L);
        user1.setPassword("12343356");
        user1.setCreated(new Date());
        user1.setUpdated(user1.getCreated());
        user1.setPhone("1233333333");

        User user2 = new User();

        user2.setId(2L);
        user2.setPassword("12343356");
        user2.setCreated(new Date());
        user2.setUpdated(user1.getCreated());
        user2.setPhone("1233333333");

        ArrayList<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);

//        String jsonStr = JsonLibUtils.toJSONString(users, null);
        String jsonStr = JsonLibUtils.toJSONString(users, new String[]{"created", "updated"});

        System.out.println(jsonStr);
    }

}
