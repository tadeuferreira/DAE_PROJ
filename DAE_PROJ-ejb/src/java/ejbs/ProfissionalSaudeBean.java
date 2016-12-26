/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ProfissionalSaudeDTO;
import entities.ProfissionalSaude;
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
public class ProfissionalSaudeBean {

    @PersistenceContext
    private EntityManager em;
    
    public void createProfissionalSaude(String username, String password, String name, String email)  
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        try{  
            if(em.find(ProfissionalSaude.class,username)!= null){
               throw new EntityAlreadyExistsException("A profissional de saude whith that username already exists");
            
            }else if(em.find(ProfissionalSaude.class,email)!= null){
               throw new EntityAlreadyExistsException("A profissional de saude whith that email already exists");
            }
            ProfissionalSaude profissional = new ProfissionalSaude(username, password, name, email);
            em.persist(profissional);
       
        // EntityDoesNotExistException missing
        }catch (EntityAlreadyExistsException e){
            throw e;
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    
    public void removeProfissionalSaude(String username) {
        try{
            ProfissionalSaude profissional = em.find(ProfissionalSaude.class, username);
            em.remove(profissional);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String email) {
        try{
            ProfissionalSaude profissional = em.find(ProfissionalSaude.class, username);
            if(profissional == null){
                return;
            }
            profissional.setName(name);
            em.merge(profissional);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<ProfissionalSaudeDTO> getAllProfissionaisSaude(){
        try{
        List<ProfissionalSaude> profissionais = (List<ProfissionalSaude>) em.createNamedQuery("findAllProfissionaisSaude").getResultList();
        return profissionalsToDTOs(profissionais);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    public ProfissionalSaudeDTO getProfissional(String username){
        try{
            ProfissionalSaude profissional = em.find(ProfissionalSaude.class, username);
            return profissionalToDTO(profissional);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    ProfissionalSaudeDTO profissionalToDTO(ProfissionalSaude profissional){
        return new ProfissionalSaudeDTO(
                              profissional.getUsername(),
                              null,
                              profissional.getName(),
                              profissional.getEmail());
    }
    
    List<ProfissionalSaudeDTO> profissionalsToDTOs(List<ProfissionalSaude> profissionals){
        List<ProfissionalSaudeDTO> dtos = new ArrayList<>();
        for(ProfissionalSaude p : profissionals){
            dtos.add(profissionalToDTO(p));
        }
        return dtos;
    }
    
}
