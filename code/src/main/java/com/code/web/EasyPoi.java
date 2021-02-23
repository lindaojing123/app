package com.code.web;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.code.dao.UserMapper;
import com.code.entity.Feedback;
import com.code.entity.User;
import com.code.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("easyPoi")
public class EasyPoi {
    @Resource
    UserMapper userMapper;

    @RequestMapping("export")
    @ResponseBody
    public Map<String,Object> export(){
        HashMap<String,Object> map = new HashMap<>();

        //查询数据数据
        List<User> users = userMapper.selectAll();
        System.out.println(users.get(0));
        //设置导出的参数  参数：Excel大标题.工作表名
        ExportParams exportParams = new ExportParams("用户反馈信息统计", "反馈1");

        //设置工作表参数   参数：导出的参数对象，导出的实体类对象，要导出的集合数据
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("/Users/mac/Documents/jdk/excel/user.xls")));
            map.put("status","100");
            map.put("message","导出成功");
        } catch (IOException e) {
            map.put("status","400");
            map.put("message","导出失败");
            e.printStackTrace();
        }
        return map;
    }
}
