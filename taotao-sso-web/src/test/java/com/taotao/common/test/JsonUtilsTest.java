package com.taotao.common.test;

import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.Cart;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * JsonUtilsTest
 *
 * @author eliefly
 * @date 2018-01-15
 */
public class JsonUtilsTest {

    @Test
    public void test01() throws UnsupportedEncodingException {

        String jsonStr = "%5B%7B%22created%22%3A1515998954701%2C%22updated%22%3A1515998954701%2C%22id%22%3Anull%2C%22userId%22%3Anull%2C%22itemId%22%3A975641%2C%22itemTitle%22%3A%22%E8%8B%B9%E6%9E%9C%28Apple%29+iPhone+5s+%28A1533%29+16GB+%E9%87%91%E8%89%B2+%E7%94%B5%E4%BF%A13G%E6%89%8B%E6%9C%BA%22%2C%22itemImage%22%3A%22http%3A%2F%2Fimage.taotao.com%2Fjd%2F44ae81a7421c4eee8026a17755db9362.jpg%22%2C%22itemPrice%22%3A409900.0%2C%22num%22%3A2%7D%5D";

        String decode = URLDecoder.decode(jsonStr, "utf-8");

        System.out.println(decode);

        List<Cart> cartList = JsonUtils.jsonToList(decode, Cart.class);

        System.out.println(cartList);

    }

}
