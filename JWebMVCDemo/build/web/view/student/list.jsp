<%-- 
    Document   : list
    Created on : Nov 14, 2016, 10:30:47 PM
    Author     : sonnt
--%>

<%@page import="model.StudentList"%>
<%@page import="com.fpt.mvc.helper.MVCHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../../view/validateview"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List of Student</title>
    </head>
    <body>
        <% ArrayList<Student> students = MVCHelper.getModel(request, StudentList.class).getList(); %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>display name</th>
            </tr>
            <%for(Student s : students){%>
            <tr>
                <td><%=s.getStudentID()%></td>
                <td><%=s.getEmail()%></td>
                <td><%=s.getDisplayName()%></td>
            </tr>  
            <%}%>
        </table>
        <a href="<%=MVCHelper.getControllerPathFromView(request,"student", "create")%>">Create</a>
    </body>
</html>
