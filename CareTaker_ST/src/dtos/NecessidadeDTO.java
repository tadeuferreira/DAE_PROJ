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
public class NecessidadeDTO implements Serializable{
    
    protected String code;
    protected String name;
    protected String description;
    
    
    public NecessidadeDTO(){
    
    }
     @Override
    public String toString(){
       StringBuilder sb = new StringBuilder();
       sb.append("Code: "+code);
       sb.append(" Name: "+name);
       sb.append(" Description: "+description);
       return sb.toString();
    }


    public NecessidadeDTO(
            int number,
            String name,
            String description
            ) {
        
        this.code = code;
        this.name = name;
        this.description = description;
       
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
