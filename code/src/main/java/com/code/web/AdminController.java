package com.code.web;

import com.code.entity.Admin;
import com.code.service.AdminService;
import com.code.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Map;

@RequestMapping("admin")
@Controller
public class AdminController {

    @Resource
    AdminService adminService;

    @RequestMapping("getImageCode")
    public void getImageCode(HttpServletResponse response, HttpServletRequest request){
        HttpSession session = request.getSession();
        String randomCode = ImageCodeUtil.getSecurityCode();
        session.setAttribute("imageCode",randomCode);
        //根据随机产生的验证码创建图片
        BufferedImage image = ImageCodeUtil.createImage(randomCode);
        try{
            //将验证码响应到前台
            ImageIO.write(image,"png",response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("login")
    @ResponseBody
    public Map login(Admin admin,String enCode){
         return adminService.getOneAdmin(admin,enCode);
    }


    @RequestMapping("logOut")
    public String logOut(HttpServletRequest request){
        request.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}
