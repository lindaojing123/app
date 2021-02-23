package com.code;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

@SpringBootTest
class GoEasyTests {

    @Test
    void testds() {

        //创建GoEasy对象  参数：设置请求地区,应用里的Appkey  应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-2fe4f036e0dd49f69fbe0e87dea4ab92");

        //发布消息  参数：通道名称，发送内容
        goEasy.publish("2008channel", "Hello, GoEasy! 2008channel");
    }

    @Test
    void testSendData() {

        Random random = new Random();

        //获取随机数 随机数的取值在 0<=count<n
        //int count = random.nextInt(n);

        for (int i = 0; i < 20; i++) {

            System.out.println(i);

            HashMap<String, Object> map = new HashMap<>();

            map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
            map.put("boys",Arrays.asList(random.nextInt(100), random.nextInt(100), random.nextInt(500), random.nextInt(300), random.nextInt(200),random.nextInt(100)));
            map.put("girls",Arrays.asList(random.nextInt(300), random.nextInt(400), random.nextInt(100), random.nextInt(300), random.nextInt(200),random.nextInt(100)));

            //将map转为json字符串
            String jsonmap = JSON.toJSONString(map);

            //创建GoEasy对象  参数：设置请求地区,应用里的Appkey  应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-2fe4f036e0dd49f69fbe0e87dea4ab92");

            //发布消息  参数：通道名称，发送内容
            goEasy.publish("2008channel", jsonmap);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
