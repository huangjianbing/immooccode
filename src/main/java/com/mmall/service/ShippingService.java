package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping; /**
 * Author:huangjb
 * Date:2019/4/6
 * Description:
 */
public interface ShippingService {
    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse del(Integer userId, String shippingIds);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse select(Integer userId, Integer shippingId);

    ServerResponse list(Integer pageNum, Integer pageSize, Integer userId);
}
