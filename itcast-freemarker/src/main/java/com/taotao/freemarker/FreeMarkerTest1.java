package com.taotao.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeMarkerTest1 {

    public static void main(String[] args) throws Exception {
        // 模板 + 数据 = 静态页面
        // 创建核心类Configuration
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

        // 设置模板所在的位置
        System.out.println(System.getProperty("user.dir"));
        cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir") + "/itcast-freemarker/src/main/java/ftl"));

        // 获取模板,参数是模板名称
        // 模板类型，官方要求ftl后缀,jsp,html,java,itcast后缀都可以
        Template template = cfg.getTemplate("template1.html");

        // 指定数据模型
        Map<String, Object> root = new HashMap<>(1);
        root.put("hello", "world");

        // 指定输出
        Writer out = new FileWriter(new File("D:\\WorkSpace\\IDEA_WorkSpace\\Taotao\\itcast-freemarker\\src\\main\\java\\views\\result1.html"));

        // 使用模板输出
        template.process(root, out);

        out.close();

    }
}

