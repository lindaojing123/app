package com.code.service;

import com.code.entity.Admin;

import java.util.Map;

public interface AdminService {
    public Map<String,String> getOneAdmin(Admin admin, String encode);
}
