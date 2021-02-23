package com.code.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Excel(name = "ID")
    private String id;

    @Excel(name = "电话")
    private String phone;

    @Excel(name = "微信")
    private String weChat;

    @Excel(name = "照片",type = 2,width = 50,height = 60,orderNum = "5")
    @Column(name = "head_img")
    private String headImg;

    @Excel(name = "用户名")
    private String username;

    @Excel(name="签名")
    private String sign;

    @Excel(name="状态")
    private String status;

    @Excel(name = "创建时间",width = 20,format = "yyyy年MM月dd日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_time")
    private Date createTime;

}