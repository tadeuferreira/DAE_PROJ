/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Entity
@NamedQueries({
    @NamedQuery(name= "findAllProfissionaisSaude", 
    query = "SELECT e FROM ProfissionalSaude e ORDER BY e.name")
})
public class ProfissionalSaude extends User implements Serializable {

    public ProfissionalSaude() {
    }

    public ProfissionalSaude(String username, String password, String name, String email) {
        super(username, password,GROUP.Professional, name, email);
     
    }
}
