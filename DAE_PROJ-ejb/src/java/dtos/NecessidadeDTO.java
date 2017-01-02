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
    
    protected int number;
    protected String name;
    protected String description;
    
    
    public NecessidadeDTO(){
    
    }

    public NecessidadeDTO(
            int number,
            String name,
            String description
            ) {
        
        this.number = number;
        this.name = name;
        this.description = description;
       
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
