package com.code;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.code.dao.AdminMapper;
import com.code.dao.FeedbackMapper;
import com.code.dao.UserMapper;
import com.code.dao.VideoMapper;
import com.code.entity.Admin;
import com.code.entity.AdminExample;
import com.code.entity.Feedback;
import com.code.entity.Video;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

@SpringBootTest
class CodeApplicationTests {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private VideoMapper videoMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void test01(){
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI4G81me9myzWo6nn57omC";
        String accessKeySecret = "tpi1egpELXfnhbOenNJHliR3Grfwkn";

        String bucketName = "nasa-2021";
        String objectName = "photo/1612407319001-逗.jpg";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

// 关闭OSSClient。
        ossClient.shutdown();

    }

    @Test
    public void upload(){
// Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G81me9myzWo6nn57omC";
        String accessKeySecret = "tpi1egpELXfnhbOenNJHliR3Grfwkn";
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String bucketName = "2008-test980";
        String objectName = "upload12.jpg";
        String file = "/Users/mac/Desktop/star.jpg";
// 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(file));

// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);

// 上传文件。
        PutObjectResult result = ossClient.putObject(putObjectRequest);
// 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void createBucket(){
// Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G81me9myzWo6nn57omC";
        String accessKeySecret = "tpi1egpELXfnhbOenNJHliR3Grfwkn";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 创建CreateBucketRequest对象。
        CreateBucketRequest createBucketRequest = new CreateBucketRequest("20212test");

// 如果创建存储空间的同时需要指定存储类型以及数据容灾类型, 可以参考以下代码。
// 此处以设置存储空间的存储类型为标准存储为例。
// createBucketRequest.setStorageClass(StorageClass.Standard);
// 默认情况下，数据容灾类型为本地冗余存储，即DataRedundancyType.LRS。如果需要设置数据容灾类型为同城冗余存储，请替换为DataRedundancyType.ZRS。
// createBucketRequest.setDataRedundancyType(DataRedundancyType.ZRS)

// 创建存储空间。
        ossClient.createBucket(createBucketRequest);

// 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void queryBucket(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G81me9myzWo6nn57omC";
        String accessKeySecret = "tpi1egpELXfnhbOenNJHliR3Grfwkn";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 列举存储空间。
        List<Bucket> buckets = ossClient.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(" - " + bucket.getName());
        }

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
