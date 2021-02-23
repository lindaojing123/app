package com.code;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.code.dao.FeedbackMapper;
import com.code.entity.Feedback;
import com.code.entity.Student;
import com.code.entity.Teacher;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class EasyPoiTest {
    @Resource
    FeedbackMapper feedbackMapper;

    @Test
    void testEasyPoiExport() {

        //查询数据数据
        List<Feedback> feedbacks = feedbackMapper.selectAll();

        //设置导出的参数  参数：Excel大标题.工作表名
        ExportParams exportParams = new ExportParams("用户反馈信息统计", "反馈1");

        //设置工作表参数   参数：导出的参数对象，导出的实体类对象，要导出的集合数据
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Feedback.class, feedbacks);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("/Users/mac/Desktop/2008EasyPoi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEasyPoiExportOnetoMany() {

        //查询数据数据
        List<Feedback> feedbacks = feedbackMapper.selectAll();

        //查询教师集合
        ArrayList<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("12", "小陈", feedbacks));
        teachers.add(new Teacher("13", "小张", feedbacks));


        //设置导出的参数  参数：Excel大标题.工作表名
        ExportParams exportParams = new ExportParams("教师学生反馈信息统计", "反馈1");

        //设置工作表参数   参数：导出的参数对象，导出的实体类对象，要导出的集合数据
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Teacher.class, teachers);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("/Users/mac/Desktop/2008EasyPoiOneToMany.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEasyPoiImport() {
        //常见导入对象参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1);  //标题占几行
        params.setHeadRows(1);  //表头占几行

        try {
            //导入
            List<Feedback> feedbacks = ExcelImportUtil.importExcel(new FileInputStream(new File("/Users/mac/Desktop/2008EasyPoi.xls")), Feedback.class, params);
            //遍历数据
            feedbacks.forEach(feedback -> System.out.println(feedback));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEasyPoiImportOnetoMany() {
        //常见导入对象参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1);  //标题占几行
        params.setHeadRows(2);  //表头占几行

        try {
            //导入
            List<Teacher> teacherList = ExcelImportUtil.importExcel(new FileInputStream(new File("/Users/mac/Desktop/2008EasyPoiOneToMany.xls")), Teacher.class, params);
            //遍历数据
            teacherList.forEach(teacher -> System.out.println(teacher));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEasyPoiExportPhoto() {
        ArrayList<Student> students = new ArrayList<>();

        students.add(new Student("4","小王","https://nasa-2021.oss-cn-hangzhou.aliyuncs.com/photo/1612599643518-star.jpg",21,new Date()));

        //设置导出的参数  参数：Excel大标题.工作表名
        ExportParams exportParams = new ExportParams("学生信息统计", "学生1");

        //设置工作表参数   参数：导出的参数对象，导出的实体类对象，要导出的集合数据
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,Student.class, students);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("/Users/mac/Documents/jdk/excel/2008EasyPoiPhoto.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}