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
public class Necessidade implements Serializable {

    @Id
    int code;
    @NotNull
    String name;
    @NotNull
    String description;
    
    LinkedList<Material> materiais;
    
    public Necessidade(){
        this.materiais = new LinkedList();
    }
    
    public Necessidade(int code, String name, String description){
        this.code = code;
        this.name = name;
        this.description = description;
        this.materiais = new LinkedList();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LinkedList<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(LinkedList<Material> materiais) {
        this.materiais = materiais;
    }
    
    
    
    
}
