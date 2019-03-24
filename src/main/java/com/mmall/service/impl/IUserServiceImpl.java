package com.mmall.service.impl;

import com.mmall.common.Constant;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    @Override
    public ServerResponse<String> selectQuestion(String username){
        ServerResponse<String> checkValid = checkValid(username, Constant.USERNAME);
        if(checkValid.isSuccess()){
            return ServerResponse.createByErrorMsg("用户名不存在");
        }
        String question=userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNoneBlank(question)){
            return ServerResponse.createBySuccessMsg(question);
        }
        return ServerResponse.createByErrorMsg("找回密码的答案是空的");
    }
    @Override
    public ServerResponse<String> checkAnswer(String username,String question ,String answer){
        int count=userMapper.checkAnswer(username,question,answer);
        if (count>0){
            String forgetToken= UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccessMsg(forgetToken);
        }
        return ServerResponse.createByErrorMsg("问题答案不正确");
    }

    @Override
    public ServerResponse<String> restPasswordNew(String username, String passwordNew, String token) {
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMsg("参数错误，需要传递token");
        }
        if(checkValid(username,Constant.USERNAME).isSuccess()){
            return ServerResponse.createByErrorMsg("该用户不存在");
        }
        String cache = TokenCache.getCache(TokenCache.TOKEN_PREFIX + username);
        if(StringUtils.isBlank(cache)){
            return ServerResponse.createByErrorMsg("token 无效或过期");
        }
        if(StringUtils.equals(cache,token)){
            passwordNew=MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount=userMapper.restPasswordNew(username,passwordNew);
            if(rowCount>0){
                return ServerResponse.createBySuccessMsg("修改密码成功");
            }
        }
        else{
            return ServerResponse.createByErrorMsg("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMsg("重置密码失败");
    }

    @Override
    public ServerResponse<String> restPassword(String passwordOld, String passwordNew, User user) {
        int count=userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(count==0){
            return ServerResponse.createByErrorMsg("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        count = userMapper.updateByPrimaryKeySelective(user);
        if (count>0){
            return ServerResponse.createBySuccessMsg("重置密码成功");
        }
        return ServerResponse.createByErrorMsg("重置密码失败");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        int count=userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(count>0){
            return ServerResponse.createByErrorMsg("email已存在，请更换email在更新信息");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setEmail(user.getEmail());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setPhone(user.getPhone());
        int rowCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(rowCount>0){
            updateUser.setUsername(user.getUsername());
            return  ServerResponse.createBySuccess("用户更新成功",updateUser);
        }
        return ServerResponse.createByErrorMsg("更新失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user=userMapper.selectByPrimaryKey(id);
        if(user==null){
            return ServerResponse.createBySuccessMsg("用户不存在");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }
}
