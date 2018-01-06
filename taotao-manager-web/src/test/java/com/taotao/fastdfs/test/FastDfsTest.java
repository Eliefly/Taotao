package com.taotao.fastdfs.test;

import com.taotao.web.util.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * 上传图片测试
 *
 * @author eliefly
 * @create 2018-01-06 17:11
 */
public class FastDfsTest {

    @Test
    public void uploadpicFastTest() throws Exception {
        //1.创建配置文件 配置连接服务端的ip地址和端口

        //2.加载配置文件
        ClientGlobal.init("D:\\WorkSpace\\IDEA_WorkSpace\\Taotao\\taotao-manager-web\\src\\main\\resources\\resource\\fastdsf_conf.conf");

        //3.创建trackerClient 对象 直接new
        TrackerClient trackerClient = new TrackerClient();
        //4.创建trackerServer对象   根据trackerClient 获取
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建StorageServer 对象  赋予null
        StorageServer storageServer = null;
        //6.创建StorageClient 对象
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //7.上传图片
        //参数1：本地文件的路径
        //参数2：文件的扩展名（不带"."）
        //参数3：元数据  图片的高度，宽度 大小等.可以一个Null
        String[] upload_file = storageClient.upload_file("C:\\Users\\eliefly\\Desktop\\bbb.jpg", "jpg", null);

        for (String string : upload_file) {
            System.out.println(string);
        }

    }

    @Test
    public void testFastClient() throws Exception {
        FastDFSClient client = new FastDFSClient("D:\\WorkSpace\\IDEA_WorkSpace\\Taotao" +
                "\\taotao-manager-web\\src\\main\\resources\\resource\\fastdsf_conf.conf");
        String uploadFile = client.uploadFile("C:\\Users\\eliefly\\Desktop\\bbb.jpg", "jpg");
        System.out.println(uploadFile);
    }

}
