package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Author:huangjb
 * Date:2019/3/28
 * Description:
 */
@Service("iCategoryService")
public class ICategoryServiceImpl implements ICategoryService {

    private Logger logger= LoggerFactory.getLogger(ICategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if(StringUtils.isBlank(categoryName)|| parentId==null){
            return ServerResponse.createByErrorMsg("参数异常，请检查对应的参数");
        }
        if(parentId!=0){
            Category cate = categoryMapper.selectByPrimaryKey(parentId);
            if(cate==null){
                return ServerResponse.createByErrorMsg("参数异常，请检查对应的参数");
            }
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int rowCount=categoryMapper.insert(category);
        if(rowCount>0){
            return ServerResponse.createBySuccessMsg("添加商品目类成功");
        }
        return ServerResponse.createByErrorMsg("添加商品失败");
    }

    @Override
    public ServerResponse updateCategory(String categoryName, Integer categoryId) {
        if(StringUtils.isBlank(categoryName)|| categoryId==null){
            return ServerResponse.createByErrorMsg("参数异常，请检查对应的参数");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int rowCount=categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount>0){
            return ServerResponse.createBySuccessMsg("更新商品目类成功");
        }
        return ServerResponse.createByErrorMsg("更新商品失败");
    }

    @Override
    public ServerResponse<List<Category>> selectCategoryChildernByParentId(Integer categoryId) {
        List<Category> List = categoryMapper.selectCategoryChildernByParentId(categoryId);
        if(CollectionUtils.isEmpty(List)){
            logger.info("当前分类的子分类为空");
        }
        return ServerResponse.createBySuccess(List);
    }

    @Override
    public ServerResponse selectCategoryAndChildernById(Integer categoryId) {
        Set<Category>categorySet= Sets.newHashSet();
        findChildrenCategory(categorySet,categoryId);
        List<Integer> catergoryList= Lists.newArrayList();
        if(null!=categoryId){
            for (Category category:categorySet){
                catergoryList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(catergoryList);
    }

    private Set<Category> findChildrenCategory(Set<Category>categorySet ,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null){
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildernByParentId(categoryId);
        for (Category categoryItem :categoryList){
            findChildrenCategory(categorySet ,categoryItem.getId());
        }
        return categorySet;
    }
}
