package com.mmall.dao;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByProductIdAndUserId(@Param(value = "userId") Integer userId,@Param(value = "productId") Integer productId);

    List<Cart> selectByUserId(Integer userId);

    int selectCatCheckedStatusByUserId(Integer userId);

    int  batchDeleteProducts(@Param(value = "productIdList")List<String> productIdList, @Param(value = "userId")Integer userId);

    int selectOrUnSelect(@Param(value = "userId")Integer userId, @Param(value = "productId")Integer productId,@Param(value = "checked")Integer checked);

    int selectProductCount(Integer userId);
}