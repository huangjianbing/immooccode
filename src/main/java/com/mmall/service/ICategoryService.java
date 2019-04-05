package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Author:huangjb
 * Date:2019/3/28
 * Description:
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategory(String categoryName, Integer categoryId);

    ServerResponse<List<Category>> selectCategoryChildernByParentId(Integer categoryId);

    ServerResponse<List<Integer>>selectCategoryAndChildernById(Integer categoryId);
}
