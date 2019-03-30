package com.mmall.controller.backEnd;

import com.mmall.common.ServerResponse;
import com.mmall.common.Session;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Author:huangjb
 * Date:2019/3/28
 * Description:
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value="addCategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, HttpServletRequest req, String categoryName, Integer parentId) {
        String sessionName = Session.getSessionName(req);
        if (StringUtils.isBlank(sessionName)) {
            return ServerResponse.createBySuccessMsg("用户未登录");
        }
        User user = (User) session.getAttribute(sessionName);
        if (user == null) {
            return ServerResponse.createByErrorMsg("登录过期，请重新登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMsg("不是管理员，没有相关权限做此操作");
        }
    }

    @RequestMapping("updateCategory.do")
    @ResponseBody
    public ServerResponse updateCategory(HttpSession session, HttpServletRequest req, String categoryName,  Integer categoryId) {
        String sessionName = Session.getSessionName(req);
        if (StringUtils.isBlank(sessionName)) {
            return ServerResponse.createBySuccessMsg("用户未登录");
        }
        User user = (User) session.getAttribute(sessionName);
        if (user == null) {
            return ServerResponse.createByErrorMsg("登录过期，请重新登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.updateCategory(categoryName, categoryId);
        } else {
            return ServerResponse.createByErrorMsg("不是管理员，没有相关权限做此操作");
        }
    }
    @RequestMapping("getCategory.do")
    @ResponseBody
    public ServerResponse<List<Category>> getChildrenParallelCateGory(HttpSession session, HttpServletRequest req, @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId) {
        String sessionName = Session.getSessionName(req);
        if (StringUtils.isBlank(sessionName)) {
            return  ServerResponse.createBySuccessMsg("用户未登录");
        }
        User user = (User) session.getAttribute(sessionName);
        if (user == null) {
            return  ServerResponse.createByErrorMsg("登录过期，请重新登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.selectCategoryChildernByParentId(categoryId);
        } else {
            return ServerResponse.createByErrorMsg("不是管理员，没有相关权限做此操作");
        }
    }
    @RequestMapping("getDeepCategory.do")
    @ResponseBody
    public ServerResponse getChildrenAndDeepCateGory(HttpSession session, HttpServletRequest req, @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId) {
        String sessionName = Session.getSessionName(req);
        if (StringUtils.isBlank(sessionName)) {
            return ServerResponse.createBySuccessMsg("用户未登录");
        }
        User user = (User) session.getAttribute(sessionName);
        if (user == null) {
            return ServerResponse.createByErrorMsg("登录过期，请重新登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.selectCategoryAndChildernById(categoryId);
        } else {
            return ServerResponse.createByErrorMsg("不是管理员，没有相关权限做此操作");
        }
    }
}
