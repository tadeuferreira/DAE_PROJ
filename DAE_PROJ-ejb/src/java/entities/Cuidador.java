/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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

    LinkedList<Material> materials;
    LinkedList<ProfissionalSaude> profissionais;
    LinkedList<Utente> utentes;
    
    public Cuidador(){
        this.materials = new LinkedList();
        this.profissionais = new LinkedList();
        this.utentes = new LinkedList();
    }
    
    public Cuidador(String username, String password, String name, String email){
        super(username, password, GROUP.Cuidador,name, email);
        this.materials = new LinkedList();
        this.profissionais = new LinkedList();
        this.utentes = new LinkedList();
    }

    public void addMaterial(Material material){
        this.materials.addLast(material);
    }
    
    public void removeMaterial(Material material){
        this.materials.addLast(material);
    }

    public LinkedList<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(LinkedList<Material> materials) {
        this.materials = materials;
    }

    public LinkedList<ProfissionalSaude> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(LinkedList<ProfissionalSaude> profissionais) {
        this.profissionais = profissionais;
    }
    
    public void addProfissional(ProfissionalSaude profissional){
        this.profissionais.addLast(profissional);
    }
    
    public void removeProfissional(ProfissionalSaude profissional){
        this.profissionais.remove(profissional);
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
}
