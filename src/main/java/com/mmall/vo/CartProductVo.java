package com.mmall.vo;

import java.math.BigDecimal;

/**
 * Author:huangjb
 * Date:2019/4/5
 * Description:
 */
public class CartProductVo {

     private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer productChecked;

    private String mainImg;

    private String productName;

    private String productSubTitle;

    private BigDecimal productprice;

    private BigDecimal productTotalPrice;

    private Integer productStatus;

    private Integer productStak;

    private String limitqQantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public BigDecimal getProductprice() {
        return productprice;
    }

    public void setProductprice(BigDecimal productprice) {
        this.productprice = productprice;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getProductStak() {
        return productStak;
    }

    public void setProductStak(Integer productStak) {
        this.productStak = productStak;
    }

    public String getLimitqQantity() {
        return limitqQantity;
    }

    public void setLimitqQantity(String limitqQantity) {
        this.limitqQantity = limitqQantity;
    }
}
