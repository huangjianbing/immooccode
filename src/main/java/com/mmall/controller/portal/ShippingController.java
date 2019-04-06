package com.mmall.controller.portal;

import com.mmall.common.ServerResponse;
import com.mmall.common.Session;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.ShippingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Author:huangjb
 * Date:2019/4/6
 * Description:
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    private ShippingService shippingService;

    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(HttpServletRequest req , HttpSession session, Shipping shipping){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户信息");
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorMsg("登录过期,无法获取当前用户信息");
        }
        else{
            return shippingService.add(user.getId(),shipping);
        }
    }
    @RequestMapping(value = "del.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse del(HttpServletRequest req , HttpSession session, String shippingIds){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户信息");
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorMsg("登录过期,无法获取当前用户信息");
        }
        else{
            return shippingService.del(user.getId(),shippingIds);
        }
    }
    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(HttpServletRequest req , HttpSession session, Shipping shipping){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户信息");
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorMsg("登录过期,无法获取当前用户信息");
        }
        else{
            return shippingService.update(user.getId(),shipping);
        }
    }
    @RequestMapping(value = "select.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse select(HttpServletRequest req , HttpSession session,Integer shippingId){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户信息");
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorMsg("登录过期,无法获取当前用户信息");
        }
        else{
            return shippingService.select(user.getId(),shippingId);
        }
    }

    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(HttpServletRequest req , HttpSession session,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户信息");
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorMsg("登录过期,无法获取当前用户信息");
        }
        else{
            return shippingService.list(pageNum,pageSize,user.getId());
        }
    }
}
