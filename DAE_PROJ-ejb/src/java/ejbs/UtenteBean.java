/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import exceptions.*;
import dtos.UtenteDTO;
import entities.Cuidador;
import entities.Utente;
import exceptions.CuidadorAssociatedException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.UtenteAssociatedException;
import exceptions.UtenteNotEnrolledException;
import exceptions.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Stateless
@Path("/patients")
public class UtenteBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    
    public void createUtente(String code, String name) 
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        
        try{  
            if(em.find(Utente.class,code)!= null){
               throw new EntityAlreadyExistsException("A administrator whith that username already exists"); 
            }
            
            Utente utente = new Utente(code, name);
            em.persist(utente);
       // EntityDoesNotExistException missing
        }catch (EntityAlreadyExistsException e){
            throw e;
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    
    
    public void removeUtente(String code) {
        try{
            Utente utente = em.find(Utente.class, code);
            em.remove(utente);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String code,String name) {
        try{
            Utente utente = em.find(Utente.class, code);
            if(utente == null){
                return;
            }
            utente.setName(name);
            em.merge(utente);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<UtenteDTO> getAllUtentes(){
        try{
        List<Utente> utentes = (List<Utente>) em.createNamedQuery("findAllUtentes").getResultList();
        return utentesToDTOs(utentes);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    public Utente getUtente(String code){
        try{
            Utente utente = em.find(Utente.class, code);
            return utente;
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    UtenteDTO utenteToDTO(Utente utente){
        return new UtenteDTO(
                              utente.getCode(),
                              utente.getName()
                              );
    }
    
    List<UtenteDTO> utentesToDTOs(List<Utente> utentes){
        List<UtenteDTO> dtos = new ArrayList<>();
        for(Utente u : utentes){
            dtos.add(utenteToDTO(u));
        }
        return dtos;
    }
    
    public void enrollUtenteToCuidador(String username, String code)
            throws EntityDoesNotExistException, CuidadorAssociatedException, UtenteAssociatedException{
        try {
            Cuidador cuidador = em.find(Cuidador.class, username);
            if (cuidador == null) {
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
            }

            Utente utente = em.find(Utente.class, code);
            if (utente == null) {
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }

            if(cuidador.getUtentes() != null){
               if (cuidador.getUtentes().contains(utente)) {
                throw new UtenteAssociatedException("Utente is already associated to that cuidador.");
            } 
            }
            

           if(utente.getCuidador() != null)  {
            if (utente.getCuidador().getUsername() == cuidador.getUsername()) {
                throw new CuidadorAssociatedException("Cuidador is already associated to that utente.");
            }   
           }
            

            utente.setCuidador(cuidador);
            cuidador.addUtente(utente);

        } catch (EntityDoesNotExistException | CuidadorAssociatedException | UtenteAssociatedException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
 
    
    public List<UtenteDTO> getEnrolledUtentes(String username) throws EntityDoesNotExistException{
        try {
            Cuidador cuidador = em.find(Cuidador.class, username);
            if( cuidador == null){
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
            }            
            List<Utente> utentes = (List<Utente>) cuidador.getUtentes();
            return utentesToDTOs(utentes);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollUtente(String username, String code) 
            throws EntityDoesNotExistException, UtenteNotEnrolledException {
        try {
            Utente utente = em.find(Utente.class, code);
            if(utente == null){
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }            
            
            Cuidador cuidador = em.find(Cuidador.class, username);
            if(cuidador == null){
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
            }
            
            if(!cuidador.getUtentes().contains(utente)){
                throw new UtenteNotEnrolledException();
            }            
            
            cuidador.removeUtente(utente);
            utente.setCuidador(null);

        } catch (EntityDoesNotExistException | UtenteNotEnrolledException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<UtenteDTO> getUnrolledUtentes(String username) throws EntityDoesNotExistException{
        try {
            Cuidador cuidador = em.find(Cuidador.class, username);
            if( cuidador == null){
                throw new EntityDoesNotExistException("There is no cuidador with that username.");
            }            
            List<Utente> utentes = (List<Utente>) em.createNamedQuery("findAllUtentes").getResultList();
            List<Utente> enrolled = em.find(Cuidador.class, username).getUtentes();
            utentes.removeAll(enrolled);
            
            return utentesToDTOs(utentes);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    @GET
    @RolesAllowed({"Cuidador"})
    @Path("caretaker/{username}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<UtenteDTO> getAllMyPatients(@PathParam("username") String username) throws EntityDoesNotExistException {
        List<UtenteDTO> utentes = null;
        System.out.println(username);
        try {
         Cuidador cuidador = em.find(Cuidador.class, username);
         if(cuidador == null)
             throw new EntityDoesNotExistException("cant find cuidador");
         List<Utente> uts = cuidador.getUtentes();
            System.out.println(uts == null);
            System.out.println(uts.isEmpty());
         utentes = utentesToDTOs(uts);
        return utentes;
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
