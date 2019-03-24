package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Author:huangjb
 * Date:2019/3/18
 * Description:
 */
public interface IUserService {

    ServerResponse<User> login(String Username, String password);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse<String> register(User user);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question ,String answer);

    ServerResponse<String> restPasswordNew(String username, String passwordNew, String token);
}
