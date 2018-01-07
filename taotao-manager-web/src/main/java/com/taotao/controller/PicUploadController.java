package com.taotao.controller;

import com.taotao.common.pojo.PicUploadResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.web.util.FastDFSClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图片上传控制器
 *
 * @author eliefly
 * @date 2018-01-06 21:59
 */
@Controller
@RequestMapping("pic/upload")
public class PicUploadController {

    @Value("${TAOTAO_IMAGE_SERVER_URL}")
    private String TAOTAO_IMAGE_SERVER_URL;

    private static String[] TYPE = {".jpg", ".jpeg", ".png", ".bmp", ".gif"};

    // 火狐浏览器上传不成功
    // 因为富文本编辑器的上传组件对浏览器的兼容不好，不能使用json的响应, 返回结果使用 JsonUtils 转化

    /**
     * 图片上唇
     *
     * @param uploadFile
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile uploadFile) throws Exception {

        // 声明标志
        boolean flag = false;

        // 初始化返回和数据, 初始化上传失败
        PicUploadResult picUploadResult = new PicUploadResult();
        picUploadResult.setError(1);

        // 校验后缀
        for (String type : TYPE) {
            String oName = uploadFile.getOriginalFilename();
            if (StringUtils.endsWithIgnoreCase(oName, type)) {
                flag = true;
                break;
            }
        }
        // 校验失败, 直接返回
        if (!flag) {
            return JsonUtils.objectToJson(picUploadResult);
        }

        // 重置标志位
        flag = false;
        try {
            BufferedImage image = ImageIO.read(uploadFile.getInputStream());

            if (image != null) {
                picUploadResult.setHeight(String.valueOf(image.getHeight()));
                picUploadResult.setWidth(String.valueOf(image.getWidth()));
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 校验成功, 上传图片
        if (flag) {
            // 获取图片扩展名
            String extName = StringUtils.substringAfterLast(uploadFile.getOriginalFilename(), ".");
            // 使用 FastDFSClient 上传图片
            FastDFSClient client = new FastDFSClient("classpath:resource/fastdsf_conf.conf");
            String str = client.uploadFile(uploadFile.getBytes(), extName);
            String url = TAOTAO_IMAGE_SERVER_URL + str;
            picUploadResult.setUrl(url);
            picUploadResult.setError(0);
        }

        return JsonUtils.objectToJson(picUploadResult);

    }

}
