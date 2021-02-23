package com.code.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="yx_admin")
public class Admin implements Serializable {
    @Id
    private Integer id;

    private String username;

    private String password;

    private String status;

    private String salt;

}