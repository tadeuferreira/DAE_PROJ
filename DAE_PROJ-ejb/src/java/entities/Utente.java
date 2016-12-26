/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Entity
@NamedQueries({
    @NamedQuery(name= "findAllUtentes", 
    query = "SELECT e FROM Utente e ORDER BY e.name")
})
public class Utente implements Serializable {

    @Id
    private String code;
    
    @NotNull
    private String name;
    
    LinkedList<Necessidade> necessidades;
    
    LinkedList<Cuidador> cuidadores;
    
    public Utente(){
      this.necessidades = new LinkedList();  
    }
    
    public Utente(String code, String name){
       this.code = code;
       this.name = name;
       this.necessidades = new LinkedList();
       this.cuidadores = new LinkedList();
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

    public LinkedList<Necessidade> getNecessidades() {
        return necessidades;
    }

    public void setNecessidades(LinkedList<Necessidade> necessidades) {
        this.necessidades = necessidades;
    }

    public LinkedList<Cuidador> getCuidadores() {
        return cuidadores;
    }

    public void setCuidadores(LinkedList<Cuidador> cuidadores) {
        this.cuidadores = cuidadores;
    }
    
    
}
