/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
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

    LinkedList<Cuidador> cuidadores;
    
    public ProfissionalSaude() {
        this.cuidadores = new LinkedList();
    }

    public ProfissionalSaude(String username, String password, String name, String email) {
        super(username, password,GROUP.Professional, name, email);
       this.cuidadores = new LinkedList();
    }

    public LinkedList<Cuidador> getCuidadores() {
        return cuidadores;
    }

    public void setCuidadores(LinkedList<Cuidador> cuidadores) {
        this.cuidadores = cuidadores;
    }
    
     public void addCuidador(Cuidador cuidador){
        this.cuidadores.addLast(cuidador);
    }
    
    public void removeCuidador(Cuidador cuidador){
        this.cuidadores.addLast(cuidador);
    }
}
