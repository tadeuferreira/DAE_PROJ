/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Entity
@NamedQueries({
    @NamedQuery(name= "findAllNecessidades", 
    query = "SELECT e FROM Necessidade e ORDER BY e.name")
})
public class Necessidade implements Serializable {

    @Id
    String code;
    @NotNull
    String name;
    @NotNull
    String description;
    
    @ManyToMany(mappedBy="necessidades")
    List<Material> materiais;
    
    @ManyToOne
    @JoinColumn(name="UTENTE_CODE")           
    Utente utente;
    
    public Necessidade(){
        this.materiais = new ArrayList();
    }
    
    public Necessidade(String code, String name, String description, Utente utente){
        this.code = code;
        this.name = name;
        this.description = description;
        this.utente = utente;
        this.materiais = new ArrayList();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(ArrayList<Material> materiais) {
        this.materiais = materiais;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void addMaterial(Material material){
        this.materiais.add(material);
    }
    
    public void removeMaterial(Material material){
        this.materiais.remove(material);
    }

    
}
