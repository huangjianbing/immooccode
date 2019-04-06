package com.mmall.controller.portal;

import com.mmall.common.Constant;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.common.Session;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Author:huangjb
 * Date:2019/3/18
 * Description:
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

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
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse rep){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(response.getData().getUsername(),response.getData());
            Session.addCookie(rep,response.getData().getUsername());
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

    @RequestMapping(value = "getUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session, HttpServletRequest req){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户信息");
        }
        User user= (User) session.getAttribute(sessionName);
        if (null==user){
            return ServerResponse.createByErrorMsg("登录过期,无法获取当前用户信息");
        }
        return ServerResponse.createBySuccess(user);
    }

    @RequestMapping(value = "forgetGerQuestion.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGerQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    @RequestMapping(value = "forgetCheckAnswer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username ,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }
    @RequestMapping(value = "forgetRestPasswordNew.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetRestPasswordNew(String username,String passwordNew,String token){
        return iUserService.restPasswordNew(username,passwordNew,token);
    }

    @RequestMapping(value = "restPasswordNew.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String>restPasswordNew(HttpSession session,String passwordOld,String passwordNew,HttpServletRequest req){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户信息");
        }
        User user = (User) session.getAttribute(sessionName);
        if(user==null){
            return ServerResponse.createByErrorMsg("登录过期,无法获取当前用户信息");
        }
        return iUserService.restPassword(passwordOld,passwordNew,user);
    }

    @RequestMapping(value = "updateInformation.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User>updateInformation(HttpSession session,User user,HttpServletRequest req,HttpServletResponse rep){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户信息");
        }
        User current_user = (User) session.getAttribute(sessionName);
        if(current_user==null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        user.setId(current_user.getId());
        user.setUsername(current_user.getUsername());
        ServerResponse<User> response=iUserService.updateInformation(user);
        if(response.isSuccess()){
            session.setAttribute(response.getData().getUsername(),response.getData());
            Session.addCookie(rep,response.getData().getUsername());
        }
        return response;
    }
    @RequestMapping(value = "getInformation.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session,HttpServletRequest req){
        String sessionName = Session.getSessionName(req);
        if(StringUtils.isBlank(sessionName)){
            return ServerResponse.createByErrorMsg("用户未登录,无法获取当前用户信息");
        }
        User current_user = (User) session.getAttribute(sessionName);
        if(current_user==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,需要强制登录status");
        }
        return iUserService.getInformation(current_user.getId());
    }
}
