package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * Author:huangjb
 * Date:2019/3/30
 * Description:
 */
public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleState(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse getProductList(Integer pageNum, Integer pageSize);

    ServerResponse productSearch(String productName, Integer productId, Integer pageNum, Integer pageSize);

    ServerResponse portalProductDetail(Integer productId);

    ServerResponse portalProductSearch(String keyWord,Integer categoryId,String orderBy,Integer pageNum,Integer pageSize );
}
