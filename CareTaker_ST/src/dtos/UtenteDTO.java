/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@XmlRootElement(name = "Patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class UtenteDTO implements Serializable{
    
    protected String code;
    protected String name;    
    
    public UtenteDTO() {
    }    
     @Override
    public String toString(){
       StringBuilder sb = new StringBuilder();
       sb.append("Code: "+code);
       sb.append(" Name: "+name);
       return sb.toString();
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
