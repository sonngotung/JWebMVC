/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fpt.mvc.model.ModelEntityTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonnt
 */
public class StudentList extends BaseModel implements ModelEntityTable<model.Student> {
    
    private ArrayList<Student> list;

    public ArrayList<Student> getList() {
        return list;
    }

    public void setList(ArrayList<Student> list) {
        this.list = list;
    }
    
    @Override
    public ArrayList<Student> getAll() {
        
        ArrayList<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT [studentID] ,[name] ,[displayName] FROM [StudentTBL]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
               Student student = new Student(
                       rs.getString("studentID")
                       , rs.getString("name")
                       , rs.getString("displayName"));
               students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return students;
    }

    @Override
    public int Count() {
        return getAll().size();
    }
    
    
    
}
