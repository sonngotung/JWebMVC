/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fpt.mvc.controller.GET;
import com.fpt.mvc.controller.POST;
import com.fpt.mvc.controller.PreProcessing;
import com.fpt.mvc.controller.RequestParam;
import java.io.IOException;
import javax.servlet.ServletException;
import model.Student;
import model.StudentList;


/**
 *
 * @author sonnt
 */
public class StudentController extends BaseRequiredLoginController {

    @Override
    protected void preProcessRequest() throws ServletException, IOException {
    }
    
    @GET(action = "create")
    @PreProcessing(method = "checkLogin")
    public void create()
    {
        forwardToView(null);
    }
    
    @POST(action = "create")
    @PreProcessing(method = "checkLogin")
    public void processCreate(
            @RequestParam(attr="username") String username,
            @RequestParam(attr="password") String password,
            @RequestParam(attr="displayname") String displayname
    )
    {
        Student student = new Student(username, password, displayname);
        student.insert();
        StudentList students = new StudentList();
        students.setList(students.getAll());
        forwardToView(getController(),"list",students);
    }
    
    @GET(action = "list")
    @PreProcessing(method = "checkLogin")
    public void list()
    {
        loadStudents();
    }
    
    @POST(action = "list")
    @PreProcessing(method = "checkLogin")
    public void processList()
    {
        loadStudents();
    }
    
    private void loadStudents()
    {
        StudentList students = new StudentList();
        students.setList(students.getAll());
        forwardToView(students);
    }
    
    
}
