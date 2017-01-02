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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    
    @OneToMany(cascade= CascadeType.REMOVE , mappedBy="utente")
    List<Necessidade> necessidades;
    
    @ManyToOne
    @JoinColumn(name="CUIDADOR_USERNAME")   
    Cuidador cuidador;
    
    List<Procedimento> procedimentos;
    
    
    
    public Utente(){
      this.necessidades = new ArrayList();
      this.procedimentos = new ArrayList();
    }
    
    public Utente(String code, String name){
       this.code = code;
       this.name = name;
       this.necessidades = new ArrayList();
       this.procedimentos = new ArrayList();
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

    public List<Necessidade> getNecessidades() {
        return necessidades;
    }

    public void setNecessidades(ArrayList<Necessidade> necessidades) {
        this.necessidades = necessidades;
    }

    public Cuidador getCuidador() {
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador) {
        this.cuidador = cuidador;
    }

    
    public void addProcedimento(Procedimento procedimento){
        this.procedimentos.add(procedimento);
    }
    
    public void removeProcedimento(Procedimento procedimento){
        this.procedimentos.remove(procedimento);
    }
    
    
    public void addNecessidade(Necessidade necessidade){
        this.necessidades.add(necessidade);
    }
    
    public void removeNecessidade(Necessidade necessidade){
        this.necessidades.remove(necessidade);
    }

    public List<Procedimento> getProcedimentos() {
        return procedimentos;
    }

    public void setProcedimentos(LinkedList<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
    }
    
    
}
