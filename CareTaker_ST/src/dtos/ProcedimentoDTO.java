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
@XmlRootElement(name = "Procedure")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcedimentoDTO implements Serializable{
    
  
    private String code;
    private String name;
    private String description;
    private String necessidadeCode;
    private String utenteCode;
    
    
    
    public ProcedimentoDTO(){
        
    }
    
    public ProcedimentoDTO(String code, String name, String description, String necessidadeCode, String utenteCode){
      this.code = code;
      this.name = name;
      this.description = description;
      this.necessidadeCode = necessidadeCode;
      this.utenteCode = utenteCode;
    }

    @Override
    public String toString() {
     StringBuilder sb = new StringBuilder();
       sb.append("Code: "+code);
       sb.append(" Name: "+name);
       sb.append(" Description: "+description);
       sb.append(" Patient Code: "+ utenteCode);
       sb.append(" Necessity Code: "+ necessidadeCode);
       return sb.toString();    
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

    public String getNecessidadeCode() {
        return necessidadeCode;
    }

    public void setNecessidadeCode(String necessidadeCode) {
        this.necessidadeCode = necessidadeCode;
    }

    public String getUtenteCode() {
        return utenteCode;
    }

    public void setUtenteCode(String utenteCode) {
        this.utenteCode = utenteCode;
    }

    
   
}
