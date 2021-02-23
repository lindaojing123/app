package com.code.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Date;

@Table(name="yx_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Excel(name="ID")
    @Id
    private String id;
    @Excel(name="标题")
    private String title;

    @Excel(name="内容",width = 20)
    private String content;

    @Excel(name="用户ID")
    @Column(name="user_id")
    private String userId;


    @Excel(name="创建时间",width = 20,format = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name="create_time")
    private Date createTime;
}