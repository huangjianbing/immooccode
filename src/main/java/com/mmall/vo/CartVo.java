package com.mmall.vo;


import java.math.BigDecimal;
import java.util.List;

/**
 * Author:huangjb
 * Date:2019/4/5
 * Description:
 */
public class CartVo {
    private List<CartProductVo> cartProductVoList;
    private BigDecimal totalprice;
    private Boolean allChecked;
    private String hostImg;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getHostImg() {
        return hostImg;
    }

    public void setHostImg(String hostImg) {
        this.hostImg = hostImg;
    }
}
