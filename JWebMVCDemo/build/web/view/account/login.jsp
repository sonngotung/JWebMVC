<%-- 
    Document   : login
    Created on : Nov 15, 2016, 4:57:15 PM
    Author     : sonnt
--%>

<%@page import="com.fpt.mvc.helper.MVCHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../view/validateview"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <form action="<%=MVCHelper.getControllerPathFromView(request,"account", "login")%>" method="POST">
        Username <input type="text" name="username"/> <br/>
        Pass <input type="password" name="password"/> <br/>
        <%String url = MVCHelper.getViewBag(request, "url", String.class);%>
        <input type="hidden" name="url" value="<%=url!=null?url:""%>" />
        <input type="submit" value="login">
        </form>
    </body>
</html>
