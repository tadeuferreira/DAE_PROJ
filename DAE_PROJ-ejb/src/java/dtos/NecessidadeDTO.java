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
@XmlRootElement(name = "Necessity")
@XmlAccessorType(XmlAccessType.FIELD)
public class NecessidadeDTO implements Serializable{
    
    protected String code;
    protected String name;
    protected String description;
    
    
    public NecessidadeDTO(){
    
    }

    public NecessidadeDTO(
            String code,
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

    public void setCode(String number) {
        this.code = number;
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
