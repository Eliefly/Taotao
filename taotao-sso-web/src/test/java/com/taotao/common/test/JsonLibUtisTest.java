package com.taotao.common.test;

import com.taotao.common.util.JsonLibUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.User;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * JsonLibUtisTest
 *
 * @author eliefly
 * @date 2018-01-12
 */
public class JsonLibUtisTest {

    /**
     * object
     */
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

    /**
     * list
     */
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

    /**
     * map --> jsonStr
     */
    @Test
    public void test03() {

        HashMap<String, Object> map = new HashMap<>();

        map.put("name", "张三");
        map.put("age", 30);
        map.put("city", "深圳");

        String json = JsonLibUtils.toJSONString(map, new String[]{"city"});

        System.out.println(json);

    }

    @Test
    public void test04() {

        String str = "{\"id\":1,\"username\":\"\",\"email\":\"\",\"created\":{\"time\":1515851024726,\"minutes\":43," +
                "\"seconds\":44,\"hours\":21,\"month\":0,\"year\":118,\"timezoneOffset\":-480,\"day\":6,\"date\":13}," +
                "\"password\":\"123456\"}";

//        HashMap map = JsonLibUtils.toHashMap(str);
        User user = JsonLibUtils.toBean(str, User.class);
//
//        System.out.println(map);
//        System.out.println(map.get("id"));

        System.out.println(user);

    }

}
