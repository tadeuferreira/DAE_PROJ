/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lztd1
 */
@Entity
@Table(name="USERS_GROUPS")
public class UserGroup implements Serializable {
    
    public static enum GROUP {
        Administrator, Professional, Cuidador;
    }
    
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name="GROUP_NAME")
    private GROUP groupName;
    @Id
    @OneToOne
    @JoinColumn(name = "USERNAME")
    private User user;
    
    public UserGroup(GROUP groupName, User user) {
        this.groupName = groupName;
        this.user = user;
    }
     public UserGroup() {

    }
    
    
    public GROUP getGroupName() {
        return groupName;
    }
    
    public void setGroupName(GROUP groupName) {
        this.groupName = groupName;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    
    
    
}
