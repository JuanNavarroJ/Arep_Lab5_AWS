/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arep.Handler;

import java.lang.reflect.Method;

/**
 * Clase Handler
 * @author Juan David
 */
public class Handler {
    private Method metodo;
    
    public Handler(Method metodo){
        this.metodo=metodo;
    }
    
    public String procesar(){
        try{
             return (String) metodo.invoke(null,null);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        } 
    }
}
