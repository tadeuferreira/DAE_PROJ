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
    @NamedQuery(name= "findAllMaterials", 
    query = "SELECT e FROM Material e ORDER BY e.name")
})
public class Material implements Serializable {

    @Id
    private int code;
    @NotNull
    private String name;  
    @NotNull
    private String type;
    private String quantity;
    
    LinkedList<Cuidador> cuidadores;
    
    public Material(){
        this.cuidadores = new LinkedList();
    }
    
    public Material(int code, String name, String type, String quantity){
        this.code = code;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void addCuidador(Cuidador cuidador){
        this.cuidadores.addLast(cuidador);
    }
    
    public void removeCuidador(Cuidador cuidador){
        this.cuidadores.remove(cuidador);
    }

    public LinkedList<Cuidador> getCuidadores() {
        return cuidadores;
    }

    public void setCuidadores(LinkedList<Cuidador> cuidadores) {
        this.cuidadores = cuidadores;
    }
    
    
}
