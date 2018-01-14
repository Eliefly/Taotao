package com.taotao.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.*;

/**
 * JsonLib工具类
 *
 * @author eliefly
 * @date 2018-01-12
 */
@SuppressWarnings("unused")
public class JsonLibUtils {

    /**
     * 将List对象序列化为json数组串
     *
     * @param list     list数据
     * @param excludes 忽略的字段. 为null时不忽略任何字段.
     * @param <T>      list元素类型
     * @return JSON文本
     */
    public static <T> String toJSONString(List<T> list, String[] excludes) {

        JsonConfig jsonConfig = new JsonConfig();

        jsonConfig.setExcludes(excludes);

        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);

        return jsonArray.toString();
    }

    /**
     * 将对象序列化为json串
     *
     * @param object   被转化的对象
     * @param excludes 忽略的字段
     * @return JSON文本
     */
    public static String toJSONString(Object object, String[] excludes) {

        JsonConfig jsonConfig = new JsonConfig();

        jsonConfig.setExcludes(excludes);

        JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);

        return jsonObject.toString();
    }

    /**
     * 将对象转换为JSON对象数组
     *
     * @param object Object对象
     * @return JSON数组对象
     */
    public static JSONArray toJSONArray(Object object) {

        return JSONArray.fromObject(object);
    }

    /**
     * 将对象转换为JSON对象
     *
     * @param object Object对象
     * @return JSON对象
     */
    public static JSONObject toJSONObject(Object object) {

        return JSONObject.fromObject(object);
    }

    /***
     * 将对象转换为 HashMap
     * @param object 传入对象
     * @return HashMap对象
     */
    public static HashMap toHashMap(Object object) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        JSONObject jsonObject = JsonLibUtils.toJSONObject(object);
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }

        return data;
    }

    /***
     * 将对象转换为List对象
     * @param object
     * @return
     */
    public static List toArrayList(Object object) {
        List arrayList = new ArrayList();

        JSONArray jsonArray = JSONArray.fromObject(object);

        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();

            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                Object key = keys.next();
                Object value = jsonObject.get(key);
                arrayList.add(value);
            }
        }

        return arrayList;
    }

    /***
     * 将对象转换为Collection对象
     * @param object
     * @return
     */
    public static Collection toCollection(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toCollection(jsonArray);
    }

    /***
     * 将对象转换为List>
     * @param object
     * @return
     */
    public static List<Map<String, Object>> toList(Object object) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray jsonArray = JSONArray.fromObject(object);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Map<String, Object> map = new HashMap<String, Object>();
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object value = jsonObject.get(key);
                map.put((String) key, value);
            }
            list.add(map);
        }
        return list;
    }

    /***
     * 将JSON对象数组转换为传入类型的List
     * @param
     * @param jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
        return JSONArray.toList(jsonArray, objectClass);
    }

    /***
     * 将对象转换为传入类型的List
     * @param
     * @param jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(Object object, Class<T> objectClass) {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toList(jsonArray, objectClass);
    }

    /***
     * 将JSON对象转换为传入类型的对象
     * @param
     * @param jsonObject
     * @param beanClass
     * @return
     */
    public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
        return (T) JSONObject.toBean(jsonObject, beanClass);
    }

    /***
     * 将将对象转换为传入类型的对象
     * @param
     * @param object
     * @param beanClass
     * @return
     */
    public static <T> T toBean(Object object, Class<T> beanClass) {
        JSONObject jsonObject = JSONObject.fromObject(object);

        return (T) JSONObject.toBean(jsonObject, beanClass);
    }

}
