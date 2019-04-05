package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Constant;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * Author:huangjb
 * Date:2019/4/5
 * Description:
 */
@Service("iCartService")
public class ICatrServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse listCart(Integer userId) {
        return ServerResponse.createBySuccess(this.getLimitCar(userId));
    }

    @Override
    public ServerResponse addCar(Integer productId, Integer productCount,Integer userId){
        if(productId==null||userId==null||productCount==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGA_ARGUMENT.getCode(),ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        Cart cart=cartMapper.selectByProductIdAndUserId(userId,productId);
        if(cart==null){
            Cart newCart= new Cart();
            newCart.setProductId(productId);
            newCart.setUserId(userId);
            newCart.setChecked(Constant.Cart.checked);
            newCart.setQuantity(productCount);
            cartMapper.insert(newCart);
        }
        else{
            productCount=cart.getQuantity()+productCount;
            cart.setQuantity(productCount);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.listCart(userId);
    }

    @Override
    public ServerResponse batchDeleteProducts(String productIds, Integer userId) {
        if(StringUtils.isBlank(productIds)||userId==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGA_ARGUMENT.getCode(),ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        cartMapper.batchDeleteProducts(productIdList,userId);
        return this.listCart(userId);
    }

    @Override
    public ServerResponse updateCart(Integer productId, Integer productCount, Integer userId) {
        if(productCount==null||productCount==null||userId==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGA_ARGUMENT.getCode(),ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectByProductIdAndUserId(userId, productId);
        if(cart!=null){
            cart.setQuantity(productCount);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.listCart(userId);
    }

    @Override
    public ServerResponse selectOrUnSelect(Integer userId, Integer productId, Integer checked) {
        cartMapper.selectOrUnSelect(userId,productId,checked);
        return this.listCart(userId);
    }

    @Override
    public ServerResponse selectProductCount(Integer userId) {
        int count=cartMapper.selectProductCount(userId);
        return ServerResponse.createBySuccess(count);
    }

    private CartVo getLimitCar(Integer userId){
        CartVo cartVo= new CartVo();
        List<CartProductVo> cartProductVoList= Lists.newArrayList();
        List<Cart> cartList=cartMapper.selectByUserId(userId);
        BigDecimal totalPrice=new BigDecimal("0");
        if(!CollectionUtils.isEmpty(cartList)){
            for(Cart cartItem:cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductId(cartItem.getProductId());
                Product product = productMapper.selectByPrimaryKey(cartProductVo.getProductId());
                if(product!=null){
                    cartProductVo.setMainImg(product.getMainImage());
                    cartProductVo.setProductStak(product.getStock());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductSubTitle(product.getSubtitle());
                    cartProductVo.setProductprice(product.getPrice());
                    Integer limitBuyLimt=0;
                    if(cartItem.getQuantity()<=product.getStock()){
                        limitBuyLimt=cartItem.getQuantity();
                        cartProductVo.setLimitqQantity(Constant.Cart.LIMT_NUM_SUCESS);
                    }
                    else{
                        limitBuyLimt=product.getStock();
                        cartProductVo.setLimitqQantity(Constant.Cart.LIMT_NUM_FAIL);
                        Cart updateCar = new Cart();
                        updateCar.setId(cartItem.getId());
                        updateCar.setQuantity(limitBuyLimt);
                        cartMapper.updateByPrimaryKeySelective(updateCar);
                    }
                    cartProductVo.setQuantity(limitBuyLimt);
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),limitBuyLimt.doubleValue()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                if(cartProductVo.getProductChecked()==Constant.Cart.checked){
                    totalPrice=BigDecimalUtil.add(totalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setTotalprice(totalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(getAllchecked(userId));
        return cartVo;
    }
    private Boolean getAllchecked(Integer userId){
        if(userId==null){
            return false;
        }
        int rowCount=cartMapper.selectCatCheckedStatusByUserId(userId);
        return rowCount==0;
    }

}
