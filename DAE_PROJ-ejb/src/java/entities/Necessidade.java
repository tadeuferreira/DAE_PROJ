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
    @NamedQuery(name= "findAllNecessidades", 
    query = "SELECT e FROM Necessidade e ORDER BY e.name")
})
public class Necessidade implements Serializable {

    @Id
    int number;
    @NotNull
    String name;
    @NotNull
    String description;
    
    LinkedList<Material> materiais;
    LinkedList<Utente> utentes;
    
    public Necessidade(){
        this.materiais = new LinkedList();
    }
    
    public Necessidade(int number, String name, String description){
        this.number = number;
        this.name = name;
        this.description = description;
        this.materiais = new LinkedList();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public LinkedList<Utente> getUtentes() {
        return utentes;
    }

    public void setUtentes(LinkedList<Utente> utentes) {
        this.utentes = utentes;
    }
    
    public void addUtente(Utente utente){
        this.utentes.addLast(utente);
    }
    
    public void removeUtente(Utente utente){
        this.utentes.remove(utente);
    }

    public void addMaterial(Material material){
        this.materiais.addLast(material);
    }
    
    public void removeMaterial(Material material){
        this.materiais.remove(material);
    }

    
}
