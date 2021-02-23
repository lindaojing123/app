package com.code.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    private String id;
    private String adminName;
    private Date optionTime;
    private String methodName;
    private String status;
}
