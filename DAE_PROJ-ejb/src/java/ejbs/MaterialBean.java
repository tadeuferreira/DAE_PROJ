/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.MaterialDTO;
import dtos.NecessidadeDTO;
import entities.Cuidador;
import entities.Material;
import entities.Necessidade;
import exceptions.CuidadorAssociatedException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MaterialAssociatedException;
import exceptions.MaterialNotEnrolledException;
import exceptions.MyConstraintViolationException;
import exceptions.NecessidadeAssociatedException;
import exceptions.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author brunoalexandredesousahenriques
 */

@Stateless
@Path("/materials")
public class MaterialBean implements Serializable{

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
    @GET
    @RolesAllowed({"Cuidador"})
    @Path("caretaker/{username}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<MaterialDTO> getCuidadorMateriais(@PathParam("username") String username) throws EntityDoesNotExistException {
        List<MaterialDTO> mats = null;
        try {
         Cuidador cuidador = em.find(Cuidador.class, username);
         if(cuidador == null)
             throw new EntityDoesNotExistException("cant find cuidador");
         List<Material> mt = cuidador.getMaterials();
         mats = materialsToDTOs(mt);
        return mats;
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    @GET
    @RolesAllowed({"Cuidador"})
    @Path("necessity/{code}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<MaterialDTO> getNecessidadeMateriais(@PathParam("code") int code) throws EntityDoesNotExistException {
        List<MaterialDTO> mats = null;
        try {
         Necessidade necessidade = em.find(Necessidade.class, code);
         if(necessidade == null)
             throw new EntityDoesNotExistException("cant find necessidade");
         List<Material> mt = necessidade.getMateriais();
         mats = materialsToDTOs(mt);
        return mats;
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
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
    
    public MaterialDTO getMaterial(int code){
        try{
            Material material = em.find(Material.class, code);
            return materialToDTO(material);
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
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
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
            List<Material> materials = (List<Material>) em.createNamedQuery("findAllMaterials").getResultList();
            List<Material> enrolled = em.find(Cuidador.class, username).getMaterials();
            materials.removeAll(enrolled);
            
            return materialsToDTOs(materials);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
    
    public void associateMaterialToNecessidade(int number, int code)
            throws EntityDoesNotExistException, NecessidadeAssociatedException, MaterialAssociatedException{
        try {

            Necessidade necessidade = em.find(Necessidade.class, number);
            if (necessidade == null) {
                throw new EntityDoesNotExistException("There is no necessidade with that number.");
            }

            Material material = em.find(Material.class, code);
            if (material == null) {
                throw new EntityDoesNotExistException("There is no material with that code.");
            }

            
                if (necessidade.getMateriais().contains(material)) {
                    throw new MaterialAssociatedException("Material is already associated with that necessidade.");
                }
            
                if (material.getNecessidades().contains(necessidade)) {
                    throw new NecessidadeAssociatedException("Necessidade is already associated with that material.");
                }
            
            
            material.addNecessidade(necessidade);
            necessidade.addMaterial(material);

        } catch (EntityDoesNotExistException | NecessidadeAssociatedException | MaterialAssociatedException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
 /*public List<MaterialDTO> getAssociatedMateriaisOfNecessidade(List<NecessidadeDTO> neces) throws EntityDoesNotExistException{
        try {
            List<Material> materials = new ArrayList<>();
            for(NecessidadeDTO n: neces){
                  Necessidade necessidade = em.find(Necessidade.class, n.getNumber());
            if( necessidade == null){
                throw new EntityDoesNotExistException("There is no necessidade with that number.");
            }            
            List<Material> aux = (List<Material>) necessidade.getMateriais();
             for(Material m: aux){
                 materials.add(m);
             }
            }      
            return materialsToDTOs(materials);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }*/
    
    public List<MaterialDTO> getAssociatedMateriaisOfNecessidade(int number) throws EntityDoesNotExistException{
        try {
            Necessidade necessidade = em.find(Necessidade.class, number);
            if( necessidade == null){
                throw new EntityDoesNotExistException("There is no necessidade with that number.");
            }            
            List<Material> materials = (List<Material>) necessidade.getMateriais();
            return materialsToDTOs(materials);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollMaterialOfNecessidade(int number, int code) 
            throws EntityDoesNotExistException, MaterialNotEnrolledException {
        try {
            Material material = em.find(Material.class, code);
            if(material == null){
                throw new EntityDoesNotExistException("There is no material with that code.");
            }            
            
            Necessidade necessidade = em.find(Necessidade.class, number);
            if(necessidade == null){
                throw new EntityDoesNotExistException("There is no necessidade with that number.");
            }
            
            if(!necessidade.getMateriais().contains(material)){
                throw new MaterialNotEnrolledException();
            }            
            
            necessidade.removeMaterial(material);
            material.removeNecessidade(necessidade);

        } catch (EntityDoesNotExistException | MaterialNotEnrolledException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<MaterialDTO> getUnrolledMaterialsOfNecessidade(int number) throws EntityDoesNotExistException{
        try {
            Necessidade necessidade = em.find(Necessidade.class, number);
            if( necessidade == null){
                throw new EntityDoesNotExistException("There is no necessidade with that number.");
            }            
            List<Material> materials = (List<Material>) em.createNamedQuery("findAllMaterials").getResultList();
            List<Material> enrolled = necessidade.getMateriais();
            materials.removeAll(enrolled);
            
            return materialsToDTOs(materials);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
