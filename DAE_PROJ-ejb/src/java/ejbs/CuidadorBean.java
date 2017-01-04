/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.CuidadorDTO;
import dtos.MaterialDTO;
import entities.Cuidador;
import entities.Material;
import exceptions.CuidadorAssociatedException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MaterialAssociatedException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Daniel
 */

@Stateless
@Path("/caretakers")
public class CuidadorBean {

    @PersistenceContext
    private EntityManager em;
    
    public void create(String username, String password, String name, String email) 
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        
        try{  
            if(em.find(Cuidador.class,username)!= null){
               throw new EntityAlreadyExistsException("A Cuidador whith that username already exists");
            
            }else if(em.find(Cuidador.class,email)!= null){
               throw new EntityAlreadyExistsException("A Cuidador whith that email already exists");
            }
            
            Cuidador cuidador = new Cuidador(username, password, name, email);
            em.persist(cuidador);
       // EntityDoesNotExistException missing
        }catch (EntityAlreadyExistsException e){
            throw e;
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    
    public void remove(String username) throws EntityDoesNotExistException {
        try {
            Cuidador cuidador = em.find(Cuidador.class, username);
            if (cuidador == null) {
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
            }
            
            em.remove(cuidador);
        
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String email) 
        throws EntityDoesNotExistException, MyConstraintViolationException{
        try {
            Cuidador cuidador = em.find(Cuidador.class, username);
            if (cuidador == null) {
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
            }

            cuidador.setPassword(password);
            cuidador.setName(name);
            cuidador.setEmail(email);
            em.merge(cuidador);
            
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<CuidadorDTO> getAllCuidadores(){
        try{
        List<Cuidador> cuidadores = (List<Cuidador>) em.createNamedQuery("findAllCuidadores").getResultList();
        return cuidadoresToDTOs(cuidadores);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    public CuidadorDTO getCuidador(String username){
        try{
            Cuidador cuidador = em.find(Cuidador.class, username);
            return cuidadorToDTO(cuidador);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    @GET
    @RolesAllowed({"Cuidador"})
    @Path("caretaker/{username}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public CuidadorDTO getCuidadorUSER(@PathParam("username") String username) throws EntityDoesNotExistException {
        try{
            Cuidador cuidador = em.find(Cuidador.class, username);
            if(cuidador == null)
                throw new EntityDoesNotExistException("cant find cuidador");
            return cuidadorToDTO(cuidador);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    CuidadorDTO cuidadorToDTO(Cuidador cuidador){
        return new CuidadorDTO(
                              cuidador.getUsername(),
                              null,
                              cuidador.getName(),
                              cuidador.getEmail());
    }
    
    List<CuidadorDTO> cuidadoresToDTOs(List<Cuidador> cuidadores){
        List<CuidadorDTO> dtos = new ArrayList<>();
        for(Cuidador c : cuidadores){
            dtos.add(cuidadorToDTO(c));
        }
        return dtos;
    }  
}

