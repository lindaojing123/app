package com.code.web;

import com.code.entity.User;
import com.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("selectAllUser")
    @ResponseBody
    public String selectAllUser(){
        String userStr = userService.selectAllUser();
        return userStr;
    }

    @RequestMapping("findAll")
    @ResponseBody
    public Map<String,Object> findAll(Integer page, Integer rows){
        List<User> users = userService.findAll(page,rows);
        Map<String, Object> map = new HashMap<>();
        map.put("rows", users);
        map.put("page",page);
        map.put("total", userService.total(rows));
        map.put("records",userService.records() );
        return map;
    }

    @RequestMapping("updateStatus")
    @ResponseBody
    public void updateStatus(User user){
         userService.updateStatus(user);
    }

    /*
     * 1.添加数据入库
     * 2.修改 添加的返回值 返回一个数据id   返回到页面上afterSubmit:function(data)
     * 3.执行文件上传   携带添加的id
     * 4.根据id修改头像路径
     * */
    @RequestMapping("edit")
    @ResponseBody
    public String edit(String oper, User user) {
        System.out.println("edit："+user);
        String id=null;
        if(oper.equals("add")){
            id = userService.saveUser(user);
        }if(oper.equals("edit")){
            userService.updateUserHeadImgAliyun(user);
            id=user.getId();
        }if(oper.equals("del")){
            userService.deleteUserHeadImgAliyun(user);
        }
        return id;
    }

    @RequestMapping("uploadUserHeadImg")
    @ResponseBody
    public void uploadUserHeadImg(MultipartFile headImg, String id) {
        userService.uploadUserHeadImgAliyun(headImg,id);
    }

}
