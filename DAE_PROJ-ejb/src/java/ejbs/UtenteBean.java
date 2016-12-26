/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.UtenteDTO;
import entities.Utente;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Stateless
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
}
