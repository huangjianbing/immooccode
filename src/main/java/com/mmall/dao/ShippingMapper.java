package com.mmall.dao;

import com.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int batchDel(@Param("userId") Integer userId,@Param("ids") List<String> ids);

    int update(Shipping shipping);

    Shipping selectByUserIdAndShippingId(@Param("userId")Integer userId, @Param("shippingId") Integer shippingId);

    List<Shipping> selectByUserId(Integer userId);
}