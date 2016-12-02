/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.mvc.model;

import java.util.ArrayList;

/**
 *
 * @author sonnt
 */
public abstract class ListModel<T extends BaseModel> implements BaseModel{
    private ArrayList<T> data;

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
    
}
