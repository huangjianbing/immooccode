package com.mmall.common;

import com.mmall.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:huangjb
 * Date:2019/3/28
 * Description:
 */
public class Session {

    private static Logger logger= LoggerFactory.getLogger(Session.class);

    public static void addCookie(HttpServletResponse response, String value)  {
        Cookie cookie = null;
        try {
            cookie = new Cookie(Constant.COOKIENMAE, URLEncoder.encode(value, "UTF-8"));
            cookie.setPath("/");
        } catch (UnsupportedEncodingException e) {
            logger.error("创建cookie异常：",e.getMessage());
        }
        cookie.setMaxAge(24*60*60);
        response.addCookie(cookie);
    }
    public static String  getSessionName(HttpServletRequest request)  {
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(Constant.COOKIENMAE)){
            Cookie cookie = (Cookie)cookieMap.get(Constant.COOKIENMAE);
            try {
                return  URLDecoder.decode(cookie.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("获取cookie异常：",e.getMessage());
            }
        }
            return null;

    }
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
