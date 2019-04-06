package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.ShippingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author:huangjb
 * Date:2019/4/6
 * Description:
 */
@Service("shippingService")
public class ShippingServiceImpl implements ShippingService
{
    @Autowired
    private ShippingMapper shippingMapper;
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if(rowCount>0){
            Map map= Maps.newHashMap();
            map.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("添加地址成功",map);
        }
        return  ServerResponse.createByErrorMsg("添加地址失败");
    }

    @Override
    public ServerResponse del(Integer userId, String shippingIds) {
        if(StringUtils.isBlank(shippingIds)){
            return  ServerResponse.createByErrorMsg("参数异常");
        }
        List<String> ids = Splitter.on(",").splitToList(shippingIds);
        int rowCount=shippingMapper.batchDel(userId,ids);
        if(rowCount>0){
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return  ServerResponse.createByErrorMsg("删除地址失败");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.update(shipping);
        if(rowCount>0){
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return  ServerResponse.createByErrorMsg("更新地址失败");
    }

    @Override
    public ServerResponse select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByUserIdAndShippingId(userId,shippingId);
        if(shipping==null){
            return ServerResponse.createByErrorMsg("数据异常");
        }
        return  ServerResponse.createBySuccess(shipping);
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize, Integer userId) {
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageHelper.startPage(pageNum,pageSize);
        PageInfo pageInfo=new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
