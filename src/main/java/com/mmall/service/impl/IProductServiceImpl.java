package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.Constant;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:huangjb
 * Date:2019/3/30
 * Description:
 */
@Service("iProductService")
public class IProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if(product!=null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String [] imgs=product.getSubImages().split(",");
                if(imgs.length>0){
                    product.setMainImage(imgs[0]);
                }
            }
            if(product.getId()!=null){
                int rowCount=productMapper.updateByPrimaryKeySelective(product);
                if(rowCount>0){
                    return ServerResponse.createBySuccessMsg("更新产品成功");
                }
                else {
                    return ServerResponse.createByErrorMsg("更新产品失败");
                }
            }
            else {
                int rowCount=productMapper.insert(product);
                if(rowCount>0){
                    return ServerResponse.createBySuccessMsg("添加产品成功");
                }
                else {
                    return ServerResponse.createByErrorMsg("添加产品失败");
                }
            }
        }else
        {
            return ServerResponse.createByErrorMsg("更新或添加产品参数异常");
        }
    }

    @Override
    public ServerResponse setSaleState(Integer productId, Integer status) {
        if(productId==null ||status==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGA_ARGUMENT.getCode(),ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount=productMapper.updateByPrimaryKeySelective(product);
        if(rowCount>0){
           return ServerResponse.createByErrorMsg("修改产品销售状态成功");
        }
        else{
           return ServerResponse.createByErrorMsg("修改产品销售状态失败");
        }
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if(productId==null ){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGA_ARGUMENT.getCode(),ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null){
           return ServerResponse.createByErrorMsg("商品已下架或被删除");
        }
        ProductDetailVo productDetailVo=assembleProductDetail(product);

        return ServerResponse.createBySuccess(productDetailVo);
    }
    private ProductDetailVo assembleProductDetail(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category==null){
            productDetailVo.setParentCategoryId(0);
            productDetailVo.setCategoryName(product.getName());
        }
        else {
            productDetailVo.setParentCategoryId(category.getParentId());
            productDetailVo.setCategoryName(category.getName());
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    @Override
    public ServerResponse getProductList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList=productMapper.selectList();
        ArrayList<ProductListVo> products = new ArrayList<>();
        for(Product productItem: productList){
            ProductListVo productListVo=assembleProductList(productItem);
            products.add(productListVo);
        }
        PageInfo pageResult=new PageInfo(productList);
        pageResult.setList(products);
        return ServerResponse.createBySuccess(pageResult);
    }
    private ProductListVo assembleProductList(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setStock(product.getStock());
        productListVo.setName(product.getName());
        productListVo.setSubtitle(product.getSubtitle());
        return productListVo;
    }

    @Override
    public ServerResponse productSearch(String productName, Integer productId, Integer pageNum, Integer pageSize) {
         PageHelper.startPage(pageNum,pageSize);
         if(StringUtils.isNotBlank(productName)) {
             productName = new StringBuilder().append("%").append(productName).append("%").toString();
         }
        ArrayList<ProductListVo> productListVoList = new ArrayList<>();
         List<Product> productList=productMapper.selectByProductNameAndProductId(productName,productId);
         for (Product productItem:productList){
             ProductListVo productListVo = assembleProductList(productItem);
             productListVoList.add(productListVo);
         }
         PageInfo pageInfo=new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse portalProductDetail(Integer productId){

        if(productId==null ){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGA_ARGUMENT.getCode(),ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByErrorMsg("商品已下架或被删除");
        }
        if(product.getStatus()!= Constant.ProductSaleStatus.ON_SALE.getCode()){
            return ServerResponse.createByErrorMsg("商品已下架");
        }
        ProductDetailVo productDetailVo=assembleProductDetail(product);

        return ServerResponse.createBySuccess(productDetailVo);
    }
}
