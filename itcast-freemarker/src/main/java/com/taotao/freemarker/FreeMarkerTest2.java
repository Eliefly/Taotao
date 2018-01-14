package com.taotao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest2 {

    public static void main(String[] args) throws Exception {
        // 模板 + 数据模型 = 输出
        // 实例化Freemarker
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

        // 获取项目物理路径
        String dir = System.getProperty("user.dir");
        System.out.println(dir);

        // 告诉cfg 类 模板放在哪里
        cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir") + "/itcast-freemarker/src/main/java/ftl"));

        // 模板对象
        Template template = cfg.getTemplate("template2.html");

        // 数据
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("world", "世界你好!");

        // 1 javaBean
        Person person = new Person();
        person.setId(1);
        person.setName("周迅");
        root.put("person", person);

        // 2 List
        List<String> persons = new ArrayList<String>();
        persons.add("范冰冰");
        persons.add("李冰冰");
        persons.add("何灵");
        root.put("persons", persons);

        // 3 Map
        Map<String, Object> mx = new HashMap<String, Object>();
        mx.put("fbb", "范冰冰");
        mx.put("lbb", "李冰冰");
        root.put("mx", mx);

        // 4: List<Map>
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        Map<String, Object> pms1 = new HashMap<String, Object>();
        pms1.put("id1", "范冰冰");
        pms1.put("id2", "李冰冰");
        Map<String, Object> pms2 = new HashMap<String, Object>();
        pms2.put("id1", "曾志伟");
        pms2.put("id2", "何炅");
        maps.add(pms1);
        maps.add(pms2);
        root.put("maps", maps);

        // null
        root.put("val", null);

        // 输出流 最终成文件
        Writer out = new FileWriter(new File("D:\\WorkSpace\\IDEA_WorkSpace\\Taotao\\itcast-freemarker\\src\\main\\java\\views\\result2.html"));

        // 输出
        template.process(root, out);
    }

}
