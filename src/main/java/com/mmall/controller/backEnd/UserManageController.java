package com.mmall.controller.backEnd;

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
 * Date:2019/3/24
 * Description:
 */
@Controller
@RequestMapping("/manage/user/")
public class UserManageController {
    @Autowired
    private IUserService iUserService;


    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(HttpSession session,String username,String password){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            if(response.getData().getRole()==Constant.Role.ROLE_ADMIN){
                session.setAttribute(Constant.CURRENT_USER,response.getData());
                return response;
            }
            else{
                return ServerResponse.createByErrorMsg("不是管理员，不能登录");
            }
        }
        return response;
    }
}
