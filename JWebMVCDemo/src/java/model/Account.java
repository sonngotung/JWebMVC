/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fpt.mvc.model.ModelEntityInstance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author sonnt
 */
public class Account extends BaseModel implements ModelEntityInstance {
    private String username;
    private String password;
    private String displayName;
    
    public static Account login(String username,String password)
    {
        return new Account(null, null).getAccount(username, password);
    }
    
    public Account getAccount(String username,String password)
    {
        Account account = null;
        try {
            String sql = "SELECT [username] ,[password] FROM [AccountTBL] WHERE [username]=? AND [password] =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                account = new Account(username, password);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
