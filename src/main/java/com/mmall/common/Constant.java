package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Author:huangjb
 * Date:2019/3/18
 * Description:
 */
public class Constant {
    public static final String CURRENT_USER="current_user";
    public static final String EMAIL="email";
    public static final String USERNAME="username";
    public static final String COOKIENMAE="immoc";
    public interface Role{
        int ROLE_CUSTOM=0;
        int ROLE_ADMIN=1;
    }
    public interface ProductListOrderBy{
        Set<String > PRICE_ASC_DESC= Sets.newHashSet("pricr_desc","price_asc");
    }
    public interface Cart{
        //被勾选
       Integer checked=1;

       Integer unChecked=0;

       String LIMT_NUM_SUCESS="LIMT_NUM_SUCESS";

        String LIMT_NUM_FAIL="LIMT_NUM_FAIL";
    }
    public enum  ProductSaleStatus{
        ON_SALE(1,"商品在售");
        private Integer code;
        private String desc;

        ProductSaleStatus(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
