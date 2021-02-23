package com.code.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;


import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.regex.Pattern;

public class AliyunOSSUtils {
    /*
    * 文件上传
    * 参数：
    *   headImg(MultipartFile): 文件
    *   bucketName(String): 存储空间名
    *   objectName(String): 文件名
    * */
    public static OSS getOssClient(){

        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI4G81me9myzWo6nn57omC";
        String accessKeySecret = "tpi1egpELXfnhbOenNJHliR3Grfwkn";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
        return ossClient;
    }

    /*
     * 文件上传
     * 参数：
     *   headImg(MultipartFile): 文件
     *   bucketName(String): 存储空间名
     *   objectName(String): 文件名
     * */
    public static void uploadFile(MultipartFile headImg,String bucketName, String objectName){

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 上传Byte数组。
        byte[] bytes = null;
        try {
            //将MultipartFile 类型的headImg文件转为字节数组
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static URL interceptPhoto(String bucketName,String objectName){
        OSS ossClient = getOssClient();
        // 设置视频截帧操作。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        // 关闭OSSClient。
        ossClient.shutdown();

        return signedUrl;
    }

    public static void uploadFileByNet(String bucketName,String objectName) throws IOException {
       // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 上传网络流。
        InputStream inputStream = interceptPhoto(bucketName,objectName).openStream();
        String coverName ="cover/"+ objectName.split(Pattern.quote("/"))[1].split("\\.")[0]+".jpg";
        ossClient.putObject(bucketName, coverName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
