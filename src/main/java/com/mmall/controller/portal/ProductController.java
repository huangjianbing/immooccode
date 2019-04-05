package com.mmall.controller.portal;

import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:huangjb
 * Date:2019/4/2
 * Description:
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse detail(Integer productId){
        return iProductService.portalProductDetail(productId);
    }
    @RequestMapping(value = "productSerach.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSerach(@RequestParam(value = "keyWord",required = false) String keyWord,
                                        @RequestParam(value ="categoryId",required = false) Integer categoryId,
                                        @RequestParam(value ="orderBy",required = false) String orderBy,
                                        @RequestParam(value ="pageNum",defaultValue ="1")Integer pageNum,
                                        @RequestParam(value ="pageSize",defaultValue ="10")Integer pageSize){
        return iProductService.portalProductSearch(keyWord,categoryId,orderBy,pageNum,pageSize);
    }
}
