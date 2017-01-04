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
@XmlRootElement(name = "Caretaker")
@XmlAccessorType(XmlAccessType.FIELD)
public class CuidadorDTO extends UserDTO implements Serializable {
     
    public CuidadorDTO(){
    
    }

    public CuidadorDTO(
            String username,
            String password,
            String name,
            String email) {
        
        super(username, password, name, email);
        
    }
    
    @Override
    public void reset() {
        super.reset();
        
    }
}

