/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import dtos.UserDTO;
import java.io.Serializable;

/**
 *
 * @author brunoalexandredesousahenriques
 */
public class AdministratorDTO extends UserDTO implements Serializable {
     
    public AdministratorDTO(){
    
    }

    public AdministratorDTO(
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
