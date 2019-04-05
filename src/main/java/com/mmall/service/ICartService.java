package com.mmall.service;

import com.mmall.common.ServerResponse;

/**
 * Author:huangjb
 * Date:2019/4/5
 * Description:
 */
public interface ICartService {
     ServerResponse addCar(Integer productId, Integer productCount, Integer userId);

    ServerResponse updateCart(Integer productId, Integer productCount, Integer userId);

    ServerResponse batchDeleteProducts(String productIds, Integer userId);

    ServerResponse listCart(Integer userId);

    ServerResponse selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    ServerResponse selectProductCount(Integer userId);
}
