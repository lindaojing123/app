package com.code.web;

import com.code.entity.Category;
import com.code.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("getCategory")
    @ResponseBody
    public Map<String,Object> getCategory(Integer page, Integer rows){
        Map<String,Object> categoryMap = new HashMap<>();
        List<Category> categoryList = categoryService.getCategory(page,rows);
        categoryMap.put("rows",categoryList);
        categoryMap.put("page",page);
        categoryMap.put("records",categoryService.records(null));
        categoryMap.put("total",categoryService.total(rows,null));
        return categoryMap;
    }
    @RequestMapping("get2Category")
    @ResponseBody
    public Map<String, Object> get2Category(Integer page, Integer rows,String parentId){
        Map<String,Object> category2Map = new HashMap<>();
        List<Category> categoryList = categoryService.get2Category(page,rows,parentId);
        category2Map.put("rows",categoryList);
        category2Map.put("page",page);
        category2Map.put("records",categoryService.records(parentId));
        category2Map.put("total",categoryService.total(rows,parentId));
        return category2Map;
    }

    @ResponseBody
    @RequestMapping("edit")
    public void edit(Category category, String oper){
        if(oper.equals("add")){
           categoryService.addCate(category);
        }if(oper.equals("edit")){
            categoryService.updateCate(category);
        }if(oper.equals("del")){
            categoryService.deleteCate(category);
        }
    }
    @ResponseBody
    @RequestMapping("findAll")
    public String findAll(){
        return categoryService.find2Category();
    }
}
