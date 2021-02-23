package com.code.service;

import com.code.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getCategory(Integer page, Integer rows);
    public Integer records(String parentId);
    public Integer total(Integer rows,String parentId);

    List<Category> get2Category(Integer page, Integer rows, String parentId);
    public void addCate(Category category);
    public void updateCate(Category category);
    public void deleteCate(Category category);

    String find2Category();
}
