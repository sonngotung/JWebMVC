/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fpt.mvc.controller.BaseController;
import com.fpt.mvc.helper.MVCHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sonnt
 */
public abstract class BaseRequiredLoginController extends BaseController {

    public void checkLogin()
    {
        HttpSession session = request.getSession(true);
        Object obj = session.getAttribute(MVCHelper.SESSION_ACCOUNT_LOGIN);
        if(obj == null){
            String query = request.getQueryString()!=null?request.getQueryString():"";
            String uri = request.getScheme() + "://" +   
             request.getServerName() +       
             ":" +                          
             request.getServerPort() +      
             request.getRequestURI() +     
             "?" + query                          
             ;      
            MVCHelper.setViewBag(request, "url", uri);
            forwardToView("account","login",null);
        }
    }
}
