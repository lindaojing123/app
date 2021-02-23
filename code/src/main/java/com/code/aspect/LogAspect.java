package com.code.aspect;

import com.code.annotation.AddLog;
import com.code.dao.LogMapper;
import com.code.entity.Admin;
import com.code.entity.Log;
import com.code.util.UUIDUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Configuration//这是个交给Spring工厂管理的配置类
public class LogAspect {

    @Resource
    HttpSession session;
    @Resource
    LogMapper logMapper;

    @Around("@annotation(com.code.annotation.AddLog)")
    public Object addLog(ProceedingJoinPoint joinPoint){
        //谁  时间  操作  是否成功
        Admin admin = (Admin) session.getAttribute("admin");
        Date date = new Date();
        //获取切面切到方法的名字
        String methodName = joinPoint.getSignature().getName();

        //获取切面切到的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取方法上的注解
        AddLog addLog = method.getAnnotation(AddLog.class);
        //获取注解上的属性值
        String description = addLog.description();
        //拼接操作方法
        String optionMethod=methodName+" ( "+description+" ) ";

        String message = "";
        Object result = null;
        try {
            joinPoint.proceed();
            message = "success";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            message="false";
        }
        Log log = new Log(UUIDUtil.getUUID(),admin.getUsername(),date,optionMethod,message);
        System.out.println("log:"+log);
        logMapper.insertSelective(log);

        return result;
    }
}
