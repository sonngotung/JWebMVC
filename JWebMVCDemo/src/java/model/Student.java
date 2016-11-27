/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import com.fpt.mvc.model.ModelEntityInstance;
import java.util.ArrayList;


/**
 *
 * @author sonnt
 */
public class Student extends BaseModel implements com.fpt.mvc.model.ModelEntityInstance{
    private String studentID;
    private String email;
    private String displayName;
    
    public Student(String username, String password,String displayname) {
        this.studentID = username;
        this.email = password;
        this.displayName = displayname;
    }
    
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }



    @Override
    public void insert() {
    }

    @Override
    public void update() {
    }

    @Override
    public void delete() {
    }

   
    
    
}
