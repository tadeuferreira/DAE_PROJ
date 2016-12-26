/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author lztd1
 */
@Entity
@NamedQueries({
    @NamedQuery(name= "findAllAdministrators", 
query = "SELECT a FROM Administrator a ORDER BY a.name")
})
public class Administrator extends User implements Serializable {
    
    public Administrator() {
    }

    public Administrator(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
    
}
