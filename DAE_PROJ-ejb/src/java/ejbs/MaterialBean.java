/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.MaterialDTO;
import entities.Cuidador;
import entities.Material;
import exceptions.CuidadorAssociatedException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MaterialAssociatedException;
import exceptions.MaterialNotEnrolledException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author brunoalexandredesousahenriques
 */

@Stateless
public class MaterialBean {

    @PersistenceContext
    private EntityManager em;
    
    public void createMaterial(int code, String name, String type, String quantity) 
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        
        try{  
            if(em.find(Material.class,code)!= null){
               throw new EntityAlreadyExistsException("A material whith that code already exists");
            }
            
            Material material = new Material(code, name, type, quantity);
            em.persist(material);
       // EntityDoesNotExistException missing
        }catch (EntityAlreadyExistsException e){
            throw e;
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    
    public void removeMaterial(int code) {
        try{
            Material material = em.find(Material.class, code);
            em.remove(material);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(int code, String name, String type, String quantity) {
        try{
            Material material = em.find(Material.class, code);
            if(material == null){
                return;
            }
            material.setName(name);
            em.merge(material);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<MaterialDTO> getAllMaterials(){
        try{
        List<Material> materials = (List<Material>) em.createNamedQuery("findAllMaterials").getResultList();
        return materialsToDTOs(materials);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    public Material getMaterial(int code){
        try{
            Material material = em.find(Material.class, code);
            return material;
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    MaterialDTO materialToDTO(Material material){
        return new MaterialDTO(
                              material.getCode(),
                              material.getName(),
                              material.getType(),
                              material.getQuantity());
    }
    
    List<MaterialDTO> materialsToDTOs(List<Material> materials){
        List<MaterialDTO> dtos = new ArrayList<>();
        for(Material m : materials){
            dtos.add(materialToDTO(m));
        }
        return dtos;
    }
    
    public void associateMaterialtoCuidador(String username, int materialCode)
            throws EntityDoesNotExistException, CuidadorAssociatedException, MaterialAssociatedException{
        try {

            Cuidador cuidador = em.find(Cuidador.class, username);
            if (cuidador == null) {
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
            }

            Material material = em.find(Material.class, materialCode);
            if (material == null) {
                throw new EntityDoesNotExistException("There is no material with that code.");
            }

            if (cuidador.getMaterials().contains(material)) {
                throw new MaterialAssociatedException("Material is already associated with that cuidador.");
            }

            if (material.getCuidadores().contains(cuidador)) {
                throw new CuidadorAssociatedException("Cuidador is already associated with that material.");
            }

            material.addCuidador(cuidador);
            cuidador.addMaterial(material);

        } catch (EntityDoesNotExistException | CuidadorAssociatedException | MaterialAssociatedException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
 
    
    public List<MaterialDTO> getAssociatedMateriais(String username) throws EntityDoesNotExistException{
        try {
            Cuidador cuidador = em.find(Cuidador.class, username);
            if( cuidador == null){
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
            }            
            List<Material> materials = (List<Material>) cuidador.getMaterials();
            return materialsToDTOs(materials);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollMaterial(String username, int materialCode) 
            throws EntityDoesNotExistException, MaterialNotEnrolledException {
        try {
            Material material = em.find(Material.class, materialCode);
            if(material == null){
                throw new EntityDoesNotExistException("There is no material with that code.");
            }            
            
            Cuidador cuidador = em.find(Cuidador.class, username);
            if(cuidador == null){
                throw new EntityDoesNotExistException("There is no student with that username.");
            }
            
            if(!cuidador.getMaterials().contains(material)){
                throw new MaterialNotEnrolledException();
            }            
            
            cuidador.removeMaterial(material);
            material.removeCuidador(cuidador);

        } catch (EntityDoesNotExistException | MaterialNotEnrolledException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<MaterialDTO> getUnrolledMaterials(String username) throws EntityDoesNotExistException{
        try {
            Cuidador cuidador = em.find(Cuidador.class, username);
            if( cuidador == null){
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
            }            
            List<MaterialDTO> materiais = this.getAllMaterials();
            List<Material> enrolled = em.find(Cuidador.class, username).getMaterials();
            materiais.removeAll(materialsToDTOs(enrolled));
            return materiais;
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    
    
}