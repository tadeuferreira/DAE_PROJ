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
import javax.validation.constraints.NotNull;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Entity
public class Utente implements Serializable {

    @Id
    private int code;
    
    @NotNull
    private String name;
    
    LinkedList<Necessidade> necessidades;
    
    LinkedList<Cuidador> cuidadores;
    
    public Utente(){
      this.necessidades = new LinkedList();  
    }
    
    public Utente(int code, String name){
       this.code = code;
       this.name = name;
       this.necessidades = new LinkedList();
       this.cuidadores = new LinkedList();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
