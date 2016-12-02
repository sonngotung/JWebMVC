/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.mvc.helper;

import com.fpt.mvc.model.BaseModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sonnt
 */
public class MVCHelper {
    
    public static final String GET_MODEL_ATTRIBUTE = "model"; 
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
    public static final String COOKIE_LOGIN_USER = "COOKIE_LOGIN_USER";
    public static final String COOKIE_LOGIN_PASS = "COOKIE_LOGIN_PASS";
    public static final String SESSION_ACCOUNT_LOGIN = "SESSION_ACCOUNT_LOGIN";
    
    public static String getControllerPathFromView(HttpServletRequest request,String controller,String action)
    {
        return "../"+controller+"/"+action;
    }
    
    public static <T extends BaseModel> T getModel(HttpServletRequest request,Class<T> type)
    {
        return (T)request.getAttribute(GET_MODEL_ATTRIBUTE);
    }
    
    public static <T extends BaseModel> void setModel(HttpServletRequest request,T model)
    {
       request.setAttribute(GET_MODEL_ATTRIBUTE,model);
    }
    
    public static <T> T getViewBag(HttpServletRequest request,String key,Class<T> type)
    {
        return (T)request.getAttribute(key);
    }
    
    public static <T> void setViewBag(HttpServletRequest request,String key,T model)
    {
       request.setAttribute(key,model);
    }
    
}
