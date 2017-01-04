/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Entity
@NamedQueries({
    @NamedQuery(name= "findAllProcedimentos", 
    query = "SELECT e FROM Procedimento e ORDER BY e.name")
})
public class Procedimento implements Serializable {

    @Id
    private String code;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    @OneToOne
    private Necessidade necessidade;
    
    @ManyToOne
    @JoinColumn(name="UTENTE_CODE")   
    Utente utente;

    
    public Procedimento(){

    }
    
    public Procedimento(String code, String name, String description, Necessidade necessidade, Utente utente){
        this.code = code;
        this.name = name;
        this.description= description;
        this.necessidade = necessidade;
        this.utente = utente;
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

    public Necessidade getNecessidade() {
        return necessidade;
    }

    public void setNecessidade(Necessidade necessidade) {
        this.necessidade = necessidade;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    
    
    
    
}
