package com.mmall.controller.backEnd;

import com.mmall.common.*;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;

/**
 * Author:huangjb
 * Date:2019/3/30
 * Description:
 */
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private FastDFSClientWrapper dfsClient;
    private static Logger logger= LoggerFactory.getLogger(ProductManageController.class);
    private ServerResponse init(HttpSession session,HttpServletRequest req){
        String sessionName = Session.getSessionName(req);
        if (StringUtils.isBlank(sessionName)) {
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        User user = (User) session.getAttribute(sessionName);
        if (user == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员账号");
        }
        return ServerResponse.createBySuccess(user);
    }

    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse saveProduct(HttpSession session,HttpServletRequest req,Product product){
        ServerResponse init = init(session, req);
        if(init.isSuccess()) {
            if (iUserService.checkAdmin((User) init.getData()).isSuccess()) {
                return iProductService.saveOrUpdateProduct(product);
            } else {
                return ServerResponse.createByErrorMsg("不是管理员，没有相关权限做此操作");
            }
        }
        else {
            return init;
        }
    }
    @RequestMapping(value = "setSaleState.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setSaleState(HttpSession session,HttpServletRequest req,Integer productId, Integer status){
        ServerResponse init = init(session, req);
        if(init.isSuccess()) {
            if (iUserService.checkAdmin((User) init.getData()).isSuccess()) {
                return iProductService.setSaleState(productId,status);
            } else {
                return ServerResponse.createByErrorMsg("不是管理员，没有相关权限做此操作");
            }
        }
        else {
            return init;
        }
    }
    @RequestMapping(value = "prodcutDetail.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setdetail(HttpSession session,HttpServletRequest req,Integer productId, Integer status){
        ServerResponse init = init(session, req);
        if(init.isSuccess()) {
            if (iUserService.checkAdmin((User) init.getData()).isSuccess()) {
                return iProductService.manageProductDetail(productId);
            } else {
                return ServerResponse.createByErrorMsg("不是管理员，没有相关权限做此操作");
            }
        }
        else {
            return init;
        }
    }
    @RequestMapping(value = "productList.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productList(HttpSession session, HttpServletRequest req, @RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum, @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize){
        ServerResponse init = init(session, req);
        if(init.isSuccess()) {
            if (iUserService.checkAdmin((User) init.getData()).isSuccess()) {
                return iProductService.getProductList(pageNum,pageSize);
            } else {
                return ServerResponse.createByErrorMsg("不是管理员，没有相关权限做此操作");
            }
        }
        else {
            return init;
        }
    }
    @RequestMapping(value = "productSearch.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSearch(HttpSession session, HttpServletRequest req,String productName,Integer productId, @RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum, @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize){
        ServerResponse init = init(session, req);
        if(init.isSuccess()) {
            if (iUserService.checkAdmin((User) init.getData()).isSuccess()) {
                return iProductService.productSearch(productName,productId,pageNum,pageSize);
            } else {
                return ServerResponse.createByErrorMsg("不是管理员，没有相关权限做此操作");
            }
        }
        else {
            return init;
        }
    }
    @RequestMapping(value = "uploadImg.do" ,method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request,MultipartFile file) throws Exception {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        Iterator<String> fileNames = req.getFileNames();
        file = req.getFile(fileNames.next());

        String imgUrl = dfsClient.uploadFile(file);
        return imgUrl;
    }
}
