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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Entity
@NamedQueries({
    @NamedQuery(name= "findAllProcedimentos", 
    query = "SELECT e FROM Procedimento e ORDER BY e.nameProc")
})
public class Procedimento implements Serializable {

    @Id
    private int procCode;
    @NotNull
    private String nameProc;
    @NotNull
    private String descriptionProc;
    @NotNull
    private String necessidade;
    @NotNull
    private String utente;
    
    LinkedList<Utente> utentes;
    LinkedList<Cuidador> cuidadores;
    LinkedList<Material> materiais;
    LinkedList<Necessidade> necessidades;
    
    public Procedimento(){
        this.utentes = new LinkedList();
        this.cuidadores = new LinkedList();
        this.materiais = new LinkedList();
        this.necessidades = new LinkedList();

    }
    
    public Procedimento(int procCode, String nameProc, String descriptionProc, String utente, String necessidade){
        this.procCode = procCode;
        this.nameProc = nameProc;
        this.descriptionProc = descriptionProc;
        
        
        this.utente = utente;
        this.necessidade = necessidade;
        
        this.utentes = new LinkedList();
        this.cuidadores = new LinkedList();
        this.materiais = new LinkedList();
        this.necessidades = new LinkedList();
    }

    public int getProcCode() {
        return procCode;
    }

    public void setProcCode(int procCode) {
        this.procCode = procCode;
    }

    public String getNameProc() {
        return nameProc;
    }

    public void setNameProc(String nameProc) {
        this.nameProc = nameProc;
    }

    public String getDescriptionProc() {
        return descriptionProc;
    }

    public void setDescriptionProc(String descriptionProc) {
        this.descriptionProc = descriptionProc;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public LinkedList<Utente> getUtentes() {
        return utentes;
    }

    public void setUtentes(LinkedList<Utente> utentes) {
        this.utentes = utentes;
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
        this.cuidadores.remove(cuidador);
    } 
    
     public void addUtente(Utente utente){
        this.utentes.addLast(utente);
    }
    
    public void removeUtente(Utente utente){
        this.utentes.remove(utente);
    }

    public LinkedList<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(LinkedList<Material> materiais) {
        this.materiais = materiais;
    }
    
    public void addMaterial(Material material){
        this.materiais.addLast(material);
    }
    
    public void removeMaterial(Material material){
        this.materiais.remove(material);
    } 

    public LinkedList<Necessidade> getNecessidades() {
        return necessidades;
    }

    public void setNecessidades(LinkedList<Necessidade> necessidades) {
        this.necessidades = necessidades;
    }
    
    
    public void addNecessidade(Necessidade necessidade){
        this.necessidades.addLast(necessidade);
    }
    
    public void removeNecessidade(Necessidade necessidade){
        this.necessidades.remove(necessidade);
    } 

    public String getNecessidade() {
        return necessidade;
    }

    public void setNecessidade(String necessidade) {
        this.necessidade = necessidade;
    }
    
    
    
}
