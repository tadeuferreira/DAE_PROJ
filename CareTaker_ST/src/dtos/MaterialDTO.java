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
@XmlRootElement(name = "Material")
@XmlAccessorType(XmlAccessType.FIELD)
public class MaterialDTO implements Serializable {
     
    protected String code;
    protected String name;
    protected String type;
    protected String description;
            
    public MaterialDTO(){
    
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
       sb.append("Code: "+code);
       sb.append(" Name: "+name);
       sb.append(" Type: "+type);
       return sb.toString();
    }
    

    public MaterialDTO(
            String code,
            
            String name,
            String type,
            String description) {
        
        this.code = code;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
