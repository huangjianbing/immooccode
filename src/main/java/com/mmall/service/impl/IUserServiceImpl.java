package com.mmall.service.impl;

import com.mmall.common.Constant;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:huangjb
 * Date:2019/3/18
 * Description:
 */
@Service("iUserService")
public class IUserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultNum = userMapper.checkUserName(username);
        if(resultNum==0){
            return  ServerResponse.createByErrorMsg("用户名不存在");
        }
        password=MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, password);
        if(user==null){
            return ServerResponse.createByErrorMsg("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功",user);
    }
    @Override
    public ServerResponse<String> checkValid(String str,String type){
        int count=0;
        if(StringUtils.isBlank(str)){
            return ServerResponse.createByErrorMsg("参数错误");
        }
       switch (type){
           case Constant.EMAIL: count=userMapper.checkEmail(str);
           if(count>0){
               return ServerResponse.createByErrorMsg("email 已存在");
           }
               return ServerResponse.createBySuccessMsg("校验成功");
           case Constant.USERNAME: count=userMapper.checkUserName(str);
               if(count>0){
                   return ServerResponse.createByErrorMsg("用户已存在");
               }
               return ServerResponse.createBySuccessMsg("校验成功");
           default:return ServerResponse.createByErrorMsg("参数错误");
       }
    }

    @Override
    public ServerResponse<String> register(User user) {
        //校验用户名和Email
        ServerResponse<String> response = checkValid(user.getEmail(), Constant.EMAIL);
        if(!response.isSuccess()){
            return response;
        }
        response=checkValid(user.getUsername(),Constant.USERNAME);
        if(!response.isSuccess()){
            return response;
        }
        user.setRole(Constant.Role.ROLE_CUSTOM);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int count = userMapper.insert(user);
        if(count==0){
            return ServerResponse.createByErrorMsg("注册失败");
        }
        return ServerResponse.createBySuccessMsg("注册成功");
    }
}
