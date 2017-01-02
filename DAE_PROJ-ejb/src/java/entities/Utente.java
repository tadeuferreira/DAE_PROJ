/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
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
    
    List<Necessidade> necessidades;
    
    List<Cuidador> cuidadores;
    
    List<Procedimento> procedimentos;
    
    
    
    public Utente(){
      this.necessidades = new LinkedList();
      this.procedimentos = new LinkedList();
    }
    
    public Utente(String code, String name){
       this.code = code;
       this.name = name;
       this.necessidades = new LinkedList();
       this.cuidadores = new LinkedList();
       this.procedimentos = new LinkedList();
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

    public void setNecessidades(LinkedList<Necessidade> necessidades) {
        this.necessidades = necessidades;
    }

    public List<Cuidador> getCuidadores() {
        return cuidadores;
    }

    public void setCuidadores(LinkedList<Cuidador> cuidadores) {
        this.cuidadores = cuidadores;
    }
    
    public void addCuidador(Cuidador cuidador){
        this.cuidadores.add(cuidador);
    }
    
    public void removeCuidador(Cuidador cuidador){
        this.cuidadores.remove(cuidador);
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
