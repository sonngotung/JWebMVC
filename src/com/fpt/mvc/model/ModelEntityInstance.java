/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.mvc.model;

/**
 *
 * @author sonnt
 */
public interface ModelEntityInstance extends BaseModel {
    //implement anything needed here
    public  void insert();
    public  void update();
    public  void delete();
            
}
