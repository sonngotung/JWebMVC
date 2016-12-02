/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.mvc.controller;

import com.fpt.mvc.helper.MVCHelper;
import com.fpt.mvc.model.BaseModel;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sonnt
 * @param <T>
 */
public abstract class BaseController extends HttpServlet {

    public HttpServletRequest request;
    public HttpServletResponse response;

    protected void baseProcessRequest(HttpServletRequest request, HttpServletResponse response, String method)
            throws ServletException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        request.setAttribute("isValidCall", new Object());
        this.request = request;
        this.response = response;
        preProcessRequest();
        String currentAction = getAction();

        Method[] methods = this.getClass().getMethods();
        for (Method met : methods) {
            Annotation[] annotations = met.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(GET.class)
                        && method.equals("GET")) {
                    if (((GET) annotation).action().equals(currentAction)) {
                        checkPreProcessingAction(met);
                        met.setAccessible(true);
                        Object[] params = requestMapping(met);
                        met.invoke(this,params);
                        return;
                    }
                } else if (annotation.annotationType().equals(POST.class)
                        && method.equals("POST")) {
                    if (((POST) annotation).action().equals(currentAction)) {
                        checkPreProcessingAction(met);
                        met.setAccessible(true);
                        Object[] params = requestMapping(met);
                        met.invoke(this,params);
                        return;
                    }
                }
            }
        }
        throw new ServletException("Request is not valid");
    }

    private Object[] requestMapping(Method met) throws ServletException
    {
        ArrayList<Object> params = new ArrayList<>();
        
        ArrayList<String> paramNames = new ArrayList<>();
        Annotation[][] annotations = met.getParameterAnnotations();
        for (Annotation[] ann : annotations) {
            if(ann.length >0)
            {
                Annotation realAnn = ann[0];
                if(realAnn.annotationType().equals(RequestParam.class))
                {
                    paramNames.add(((RequestParam) realAnn).attr());
                }
            }
        }
        
        Parameter[] parameters = met.getParameters();
        for (int i=0;i< parameters.length;i++) {
            Parameter p = parameters[i];
            String value = request.getParameter(paramNames.get(i));
            params.add(convert(value,p.getType()));
       }
        return params.toArray();
    }
    
    private <O> O convert(Object input,Class<O> otype)
    {
        return (O)input;
    }
    
    private void checkPreProcessingAction(Method met) {
        try {
            Annotation[] annotations = met.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(PreProcessing.class)) {
                    String methodAttr = ((PreProcessing) annotation).method();
                    Method[] methods = this.getClass().getMethods();
                    for (Method involkedMethod : methods) {
                        if (involkedMethod.getName().equals(methodAttr)) {
                            involkedMethod.setAccessible(true);
                            involkedMethod.invoke(this);
                            return;
                        }
                    }
                }
            }

        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected abstract void preProcessRequest()
            throws ServletException, IOException;

    public String getAction() {
        String[] parts = request.getRequestURI().split("/");
        return parts[parts.length - 1].split("[?]")[0];
    }

    public String getController() {
        String[] parts = request.getRequestURI().split("/");
        return parts[parts.length - 2];
    }

    public void forwardToView(
            BaseModel model) {
        String controller = getController();
        String action = getAction();
        if (controller.length() == 0) {
            return;
        }
        forwardToView(controller, action, model);
    }

    public void forwardToView(
            String controller,
            String action,
            BaseModel model) {

        try {
            MVCHelper.setModel(request, model);
            request.getRequestDispatcher("../view/" + controller + "/" + action + ".jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            baseProcessRequest(request, response, MVCHelper.REQUEST_METHOD_GET);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            baseProcessRequest(request, response, MVCHelper.REQUEST_METHOD_POST);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
