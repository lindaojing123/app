package com.code.service;

import com.code.dao.AdminMapper;
import com.code.entity.Admin;
import com.code.entity.AdminExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    HttpSession session;
    public Map<String,String> getOneAdmin(Admin admin,String encode){
         Map<String,String> map = new HashMap<>();
         AdminExample example = new AdminExample();
         example.createCriteria().andUsernameEqualTo(admin.getUsername());
        //根据管理员用户名查询管理员数据
         Admin admins = adminMapper.selectOneByExample(example);
         String code = (String) session.getAttribute("imageCode");
         if(code.equals(encode)){
             if(admins!=null){
                 if(admins.getStatus().equals("1")){
                     if(admin.getPassword().equals(admins.getPassword())){
                         session.setAttribute("admin",admins);
                         map.put("status","100");
                         map.put("message","成功");
                     }else {
                         map.put("status","101");
                         map.put("message","密码错误");
                     }
                 }else {
                     map.put("status","102");
                     map.put("message","用户已冻结");
                 }
             }else {
                 map.put("status","103");
                 map.put("message","用户不存在");
             }
         }else {
             map.put("status","104");
             map.put("message","验证码错误");
         }
         return map;
    }

}
