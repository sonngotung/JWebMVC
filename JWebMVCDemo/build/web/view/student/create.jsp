<%-- 
    Document   : create
    Created on : Nov 15, 2016, 8:59:07 PM
    Author     : sonnt
--%>

<%@page import="com.fpt.mvc.helper.MVCHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../view/validateview"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Student</title>
    </head>
    
         <form action="<%=MVCHelper.getControllerPathFromView(request,"student", "create")%>" method="POST">
        ID <input type="text" name="studentid"/> <br/>
        email <input type="text" name="email"/> <br/>
        Display <input type="text" name="displayname"/> <br/>
        <input type="submit" value="login">
        </form>
    
</html>
