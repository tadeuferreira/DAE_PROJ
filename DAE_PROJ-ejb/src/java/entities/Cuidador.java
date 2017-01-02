/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Daniel
 */
@Entity
@NamedQueries({
    @NamedQuery(name= "findAllCuidadores", 
    query = "SELECT e FROM Cuidador e ORDER BY e.name")
})
public class Cuidador extends User implements Serializable {

    @ManyToMany(mappedBy = "cuidadores")
             @JoinTable(name = "CUIDADORES_MATERIALS",
            joinColumns
            = @JoinColumn(name = "CUIDADOR_USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns
            = @JoinColumn(name = "MATERIAL_CODE", referencedColumnName = "CODE"))
    List<Material> materials;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "cuidador")
    List<Utente> utentes;
    
    public Cuidador(){
        this.materials = new ArrayList();
        this.utentes = new ArrayList();
    }
    
    public Cuidador(String username, String password, String name, String email){
        super(username, password, GROUP.Cuidador,name, email);
        this.materials = new LinkedList();
        this.utentes = new LinkedList();
    }

    public void addMaterial(Material material){
        this.materials.add(material);
    }
    
    public void removeMaterial(Material material){
        this.materials.remove(material);
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(LinkedList<Material> materials) {
        this.materials = materials;
    }

    public List<Utente> getUtentes() {
        return utentes;
    }

    public void setUtentes(ArrayList<Utente> utentes) {
        this.utentes = utentes;
    }
      
    public void addUtente(Utente utente){
        this.utentes.add(utente);
    }
    
    public void removeUtente(Utente utente){
        this.utentes.remove(utente);
    }
}
