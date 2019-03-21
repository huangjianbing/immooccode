package com.mmall.controller.portal;

import com.mmall.common.Constant;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Author:huangjb
 * Date:2019/3/18
 * Description:
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;
    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(Constant.CURRENT_USER,response.getData());
        }
        return response;
    }
    @RequestMapping(value = "loginOut.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> loginOut(HttpSession session){
        session.removeAttribute(Constant.CURRENT_USER);
        return  ServerResponse.createBySuccess();
    }
    @RequestMapping(value = "checkValid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }
    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }
}
