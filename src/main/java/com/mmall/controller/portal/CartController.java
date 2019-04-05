package com.mmall.controller.portal;

import com.mmall.common.Constant;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.common.Session;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Author:huangjb
 * Date:2019/4/5
 * Description:
 */
@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping(value ="addCart.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCar(HttpServletRequest req, HttpSession session, Integer productId, Integer productCount){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.addCar(productId,productCount,user.getId());
    }

    @RequestMapping(value ="updateCart.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateCart(HttpServletRequest req, HttpSession session, Integer productId, Integer productCount){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.updateCart(productId,productCount,user.getId());
    }
    @RequestMapping(value ="deleteCart.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteCart(HttpServletRequest req, HttpSession session, String productIds){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.batchDeleteProducts(productIds,user.getId());
    }
    @RequestMapping(value ="list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse listCart(HttpServletRequest req, HttpSession session){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.listCart(user.getId());
    }
    @RequestMapping(value ="selectAll.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectAll(HttpServletRequest req, HttpSession session){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),null, Constant.Cart.checked);
    }
    @RequestMapping(value ="unSelectAll.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse unSelectAll(HttpServletRequest req, HttpSession session){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),null, Constant.Cart.unChecked);
    }

    @RequestMapping(value ="selectOne.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectOne(HttpServletRequest req, HttpSession session,Integer productId){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),productId, Constant.Cart.checked);
    }
    @RequestMapping(value ="unSelectOne.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse unSelectOne(HttpServletRequest req, HttpSession session,Integer productId){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),productId, Constant.Cart.unChecked);
    }

    @RequestMapping(value ="selectProductCount.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectProductCount(HttpServletRequest req, HttpSession session){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createBySuccess(0);
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.selectProductCount(user.getId());
    }
}
