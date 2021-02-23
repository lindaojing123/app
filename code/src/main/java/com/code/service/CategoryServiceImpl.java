package com.code.service;

import com.code.dao.CategoryMapper;
import com.code.entity.Category;
import com.code.entity.CategoryExample;
import com.code.util.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Resource
    CategoryMapper categoryMapper;
    @Override
    public List<Category> getCategory(Integer page, Integer rows) {
         return categoryMapper.selectCategoryByPage((page-1)*rows,rows);
    }

    public Integer records(String parentId){
        if(parentId==null){
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdIsNull();
            Integer records = categoryMapper.selectCountByExample(example);
            return records;
        }else{
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(parentId);
            Integer records = categoryMapper.selectCountByExample(example);
            return records;
        }

    }

    @Override
    public Integer total(Integer rows,String parentId) {
        /**
         * 计算总页数： 信息总条数/ 每页展示的条数
         */
        Integer records = records(parentId);
        Integer total = null;
        if (records%rows==0) {
            total = records/rows;
        } else {
            total = records/rows+1;
        }
        return total;
    }

    @Override
    public List<Category> get2Category(Integer page, Integer rows, String parentId) {
        return categoryMapper.select2Category((page-1)*rows,rows,parentId);
    }

    @Override
    public void addCate(Category category) {
        category.setId(UUIDUtil.getUUID());
        categoryMapper.insertSelective(category);
    }

    @Override
    public void updateCate(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void deleteCate(Category category) {
        categoryMapper.deleteByPrimaryKey(category);
    }

    @Override
    public String find2Category() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelsEqualTo(2);
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        StringBuilder sbu = new StringBuilder("<select>");
        for (Category category:categoryList) {
            sbu.append("<option value='"+category.getId()+"'>"+category.getCateName()+"</option>");
        }
        sbu.append("</select>");
        System.out.println(sbu.toString());
        return sbu.toString();
    }
}
