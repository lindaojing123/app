package com.code.app;

import com.code.common.CommonResult;
import com.code.po.VideoPO;
import com.code.service.VideoService;
import com.code.util.ImageCodeUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RequestMapping("app")
@RestController
public class InterfaceController {

    @Resource
    VideoService videoService;

    @RequestMapping("getPhoneCode")
    public HashMap<String, Object> getPhoneCode(String phone) {

        HashMap<String, Object> map = new HashMap<>();
        //根据用户输入的手机号给该用户发送手机验证码
        String phoneCode = null;
        try {
            phoneCode = ImageCodeUtil.getSecurityCode();
            map.put("status", "100");
            map.put("message", "发送成功");
            map.put("data", phone);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "104");
            map.put("message", "发送失败");
            map.put("data", null);
        }
        System.out.println("  手机验证码："+phoneCode);
        return map;
    }

    @RequestMapping("queryByReleaseTime")
    public Object queryByReleaseTime(){
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<VideoPO> videoPOS =videoService.queryByReleaseTime();
           return new CommonResult().success(videoPOS);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().faild();
        }
    }
}
