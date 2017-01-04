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
    private String code;
    @NotNull
    private String name;  
    @NotNull
    private String type;
    @NotNull
    private String descripion;
    
    @ManyToMany
    @JoinTable(name = "MATERIAL_CUIDADOR",
            joinColumns
            = @JoinColumn(name = "MATERIAL_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns
            = @JoinColumn(name = "CUIDADOR_USERNAME", referencedColumnName = "USERNAME"))
    List<Cuidador> cuidadores;
    
    @ManyToMany
    @JoinTable(name = "MATERIAL_NECESSIDADE",
            joinColumns
            = @JoinColumn(name = "MATERIAL_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns
            = @JoinColumn(name = "NECESSIDADE_CODE", referencedColumnName = "CODE"))
    List<Necessidade> necessidades;
    
    public Material(){
        this.cuidadores = new ArrayList();
        this.necessidades = new ArrayList();
    }
    
    public Material(String code, String name, String type, String descripion){
        this.code = code;
        this.name = name;
        this.type = type;
        this.descripion = descripion;
        
        this.cuidadores = new ArrayList();
        this.necessidades = new ArrayList();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return descripion;
    }

    public void setDescription(String descripion) {
        this.descripion = descripion;
    }

    public void addCuidador(Cuidador cuidador){
        this.cuidadores.add(cuidador);
    }
    
    public void removeCuidador(Cuidador cuidador){
        this.cuidadores.remove(cuidador);
    }
    
    public void addNecessidade(Necessidade necessidade){
        this.necessidades.add(necessidade);
    }
    
    public void removeNecessidade(Necessidade necessidade){
        this.necessidades.remove(necessidade);
    }

    public List<Cuidador> getCuidadores() {
        return cuidadores;
    }

    public void setCuidadores(ArrayList<Cuidador> cuidadores) {
        this.cuidadores = cuidadores;
    }

    public List<Necessidade> getNecessidades() {
        return necessidades;
    }

    public void setNecessidades(ArrayList<Necessidade> necessidades) {
        this.necessidades = necessidades;
    }
    
    
}
