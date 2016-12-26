/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author brunoalexandredesousahenriques
 */
public class UtenteDTO implements Serializable{
    
    protected String code;
    protected String name;    
    
    public UtenteDTO() {
    }    
    
    public UtenteDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
     public void reset() {
        setCode(null);
        setName(null);
        
    }    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     
     
}
