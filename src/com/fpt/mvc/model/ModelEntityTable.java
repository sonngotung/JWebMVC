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
 * @param <T>
 */
public interface ModelEntityTable<T extends ModelEntityInstance> extends BaseModel {
    public abstract ArrayList<T> getAll();
    public abstract int Count();
}
