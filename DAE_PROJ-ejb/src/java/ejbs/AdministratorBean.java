/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AdministratorDTO;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.*;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolationException;
/**
 *
 * @author lztd1
 */
@Stateless
public class AdministratorBean {
    
    @PersistenceContext
    private EntityManager em;
    
    public void createAdministrator(String username, String password, String name, String email) 
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        
        try{  
            if(em.find(Administrator.class,username)!= null){
               throw new EntityAlreadyExistsException("A administrator whith that username already exists");
            
            }else if(em.find(Administrator.class,email)!= null){
               throw new EntityAlreadyExistsException("A administrator whith that email already exists");
            }
            
            Administrator administrator = new Administrator(username, password, name, email);
            em.persist(administrator);
       // EntityDoesNotExistException missing
        }catch (EntityAlreadyExistsException e){
            throw e;
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    
    
    public void removeAdministrator(String username) {
        try{
            Administrator administrator = em.find(Administrator.class, username);
            em.remove(administrator);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String email) {
        try{
            Administrator administrator = em.find(Administrator.class, username);
            if(administrator == null){
                return;
            }
            administrator.setName(name);
            em.merge(administrator);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<AdministratorDTO> getAllAdministrators(){
        try{
        List<Administrator> administrators = (List<Administrator>) em.createNamedQuery("findAllAdministrators").getResultList();
        System.out.println(administrators.size()+" asjdsakdskasdkasdskasdksdakasksadk");
        return adminsToDTOs(administrators);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    public AdministratorDTO getAdministrator(String username){
        try{
            Administrator administrator = em.find(Administrator.class, username);
            return adminToDTO(administrator);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    AdministratorDTO adminToDTO(Administrator administrator){
        return new AdministratorDTO(
                              administrator.getUsername(),
                              null,
                              administrator.getName(),
                              administrator.getEmail());
    }
    
    List<AdministratorDTO> adminsToDTOs(List<Administrator> administrators){
        List<AdministratorDTO> dtos = new ArrayList<>();
        for(Administrator a : administrators){
            dtos.add(adminToDTO(a));
        }
        return dtos;
    }
}

