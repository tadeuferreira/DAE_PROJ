/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.MaterialDTO;
import entities.Material;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
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
    
    public void createMaterial(String code, String name, String type, String quantity) 
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
    
    
    public void removeMaterial(String code) {
        try{
            Material material = em.find(Material.class, code);
            em.remove(material);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String code, String name, String type, String quantity) {
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
    
    public Material getMaterial(String code){
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
}
