/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.mvc.dao;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonnt
 * @param <T>
 */
public class SQLServerDBContext extends DBContext {

    public SQLServerDBContext(String server,
            String instance,
            String port,
            String dbName,
            String username,
            String password
    ) {
        try {
            instance = instance == null?"":"/"+instance;
            port = port == null?"":":"+port;
            String url = "jdbc:sqlserver://" + server+instance+port+ ";databaseName="+dbName+";";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SQLServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getTableName(Class<? extends DBEntity> type)
    {
        for (Annotation annotation : type.getDeclaredAnnotations()) {
            if(annotation.annotationType().equals(DBTable.class))
            {
                return ((DBTable)annotation).name();
            }
        }
        return null;
    }
    private <T extends DBEntity> HashMap<DBField,Object> matchAttrAndData(T entity,Class<T> type)
    {
        HashMap<DBField,Object> params = new HashMap<>();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Annotation[] annotations = field.getAnnotations();
            System.out.println(field.getName());
            for (Annotation annotation : annotations) {
                if(annotation.annotationType().equals(DBField.class))
                {
                    try {
                        params.putIfAbsent((DBField) annotation,field.get(entity));
                        break;
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(SQLServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }
        return params;
    }
    
    
    @Override
    public void insert(DBEntity entity) {
        try {
            Class type = entity.getClass();
            String tableName = getTableName(type);
            HashMap<DBField, Object> params = matchAttrAndData(entity,type);
            String column = "";
            String questionmark = "";
            for (Map.Entry<DBField, Object> entry : params.entrySet()) {
                DBField key = entry.getKey();
                if(key.autoGenerate())
                {
                    continue;
                }
                column += key.name()+",";
                questionmark += "?,";
            }
            column = column.substring(0, column.length()-1);
            questionmark = questionmark.substring(0, questionmark.length()-1);
            String sql = "INSERT INTO "+ tableName + "("+ column + ") VALUES (" + questionmark + ")";
            PreparedStatement statement = connection.prepareCall(sql);
            int index =1;
            for (Map.Entry<DBField, Object> entry : params.entrySet()) {
                DBField key = entry.getKey();
                if(key.autoGenerate())
                {
                    continue;
                }
                Object value = entry.getValue();
                addParamValue(statement, index, value, key.type());
                index++;
            }
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void update(DBEntity entity) {
        try {
            Class type = entity.getClass();
            String tableName = getTableName(type);
            
            HashMap<DBField, Object> params = matchAttrAndData(entity,type);
            HashMap<DBField, Object> updatedParams = new HashMap<>();
            HashMap<DBField, Object> idParams = new HashMap<>();
            
            String sqlSetPart = "";
            String sqlWhereClause = " 1=1 ";
            for (Map.Entry<DBField, Object> entry : params.entrySet()) {
                DBField key = entry.getKey();
                Object value = entry.getValue();
                if(!key.id())
                {
                    sqlSetPart+= key.name() +"= ?,";
                    updatedParams.put(key, value);
                }
                else
                {
                    sqlWhereClause+=" AND "+ key.name() +"= ?,";
                    idParams.put(key, value);
                }
            }
            sqlSetPart = sqlSetPart.substring(0, sqlSetPart.length()-1);
            sqlWhereClause =   sqlWhereClause.substring(0, sqlWhereClause.length()-1);
            String sql = "UPDATE "+ tableName + " SET  "+sqlSetPart + 
                    " WHERE " + sqlWhereClause;
            PreparedStatement statement = connection.prepareStatement(sql);
            int index =1;
            for (Map.Entry<DBField, Object> entry : updatedParams.entrySet()) {
                DBField key = entry.getKey();
                Object value = entry.getValue();
                addParamValue(statement, index, value, key.type());
                index++;
            }
            
            for (Map.Entry<DBField, Object> entry : idParams.entrySet()) {
                DBField key = entry.getKey();
                Object value = entry.getValue();
                addParamValue(statement, index, value, key.type());
                index++;
            }
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(DBEntity entity) {
        try {
            Class type = entity.getClass();
            String tableName = getTableName(type);
            HashMap<DBField, Object> params = matchAttrAndData(entity,type);
            HashMap<DBField, Object> idParams = new HashMap<>();
            String sqlWhereClause = " 1=1 ";
            for (Map.Entry<DBField, Object> entry : params.entrySet()) {
                DBField key = entry.getKey();
                Object value = entry.getValue();
                if(key.id())
                {
                    sqlWhereClause+=" AND "+ key.name() +"= ?,";
                    idParams.put(key, value);
                }
            }
            sqlWhereClause = sqlWhereClause.substring(0, sqlWhereClause.length()-1);
            String sql = "DELETE "+ tableName + " WHERE " + sqlWhereClause;
            PreparedStatement statement = connection.prepareStatement(sql);
            int index =1;
            for (Map.Entry<DBField, Object> entry : idParams.entrySet()) {
                DBField key = entry.getKey();
                Object value = entry.getValue();
                addParamValue(statement, index, value, key.type());
                index++;
            }
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public <T extends DBEntity> ArrayList<T> getAll(Class<T> type) {
        ArrayList<T> list = new ArrayList<>();
        try {
            
            String tableName = getTableName(type);
            HashMap<DBField, Object> params = null;
            params = matchAttrAndData(type.newInstance(),type);
            String columns = "";
            for (Map.Entry<DBField, Object> entry : params.entrySet()) {
                DBField key = entry.getKey();
                columns += key.name()+",";
            }
            columns = columns.substring(0,columns.length()-1);
            String sql = "SELECT "+ columns + " FROM " + tableName ;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                T entity = type.newInstance();
                doORM(rs, entity);
                list.add(entity);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(SQLServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void getOne(DBEntity entity) {
        try {
            Class type = entity.getClass();
            String tableName = getTableName(type);
            HashMap<DBField, Object> params = matchAttrAndData(entity,type);
            HashMap<DBField, Object> keyParams = new HashMap<>();
            String columns = "";
            String whereClause = " 1=1 ";
            for (Map.Entry<DBField, Object> entry : params.entrySet()) {
                DBField key = entry.getKey();
                Object value = entry.getValue();
                if(key.id())
                    keyParams.put(key, value);
                columns += key.name()+",";
            }
            columns = columns.substring(0,columns.length()-1);
            
            for (Map.Entry<DBField, Object> entry : keyParams.entrySet()) {
                DBField key = entry.getKey();
                whereClause += " AND " + key.name()+" = ? ";
            }
            
            String sql = "SELECT "+ columns + " FROM " + tableName + " WHERE " + whereClause ;
            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 1;
            for (Map.Entry<DBField, Object> entry : keyParams.entrySet()) {
                DBField key = entry.getKey();
                Object value = entry.getValue();
                addParamValue(statement, index, value, key.type());
                index++;
            }
            ResultSet rs = statement.executeQuery();
            if(rs.next())
                doORM(rs, entity);
        } catch (SQLException ex) {
            Logger.getLogger(SQLServerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
