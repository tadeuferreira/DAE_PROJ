/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author lztd1
 */
@Entity
@Table(name="USERS")
public class User implements Serializable {
    
   @Id
   protected String username;
   
   @NotNull
   protected String password;
   protected String name;
   
   @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
                      +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
                      +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
    message = "{invalid.email}")
   protected String email;
    
   
   public User(){
       
   }
   
   public User(String username, String password, String name, String email){
       this.username = username;
       this.password = password;
       this.name = name;
       this.email = email;
   }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
}