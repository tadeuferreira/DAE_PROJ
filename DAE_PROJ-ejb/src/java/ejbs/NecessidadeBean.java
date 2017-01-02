/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.NecessidadeDTO;
import entities.Necessidade;
import entities.Utente;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.NoSuchAlgorithmException;
import exceptions.NecessidadeNotEnrolledException;
import exceptions.UtenteAssociatedException;
import exceptions.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Stateless
public class NecessidadeBean implements Serializable{

    @PersistenceContext
    private EntityManager em;
    
    public void createNecessidade(int number, String name, String description, String code) 
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        System.out.println("createNecessidade");
        try{  
            if(em.find(Necessidade.class,number)!= null){
               throw new EntityAlreadyExistsException("A necessidade whith that number already exists");
            }
            Utente utente = em.find(Utente.class, code);
            if (utente == null) {
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }
            Necessidade necessidade = new Necessidade(number, name, description, utente);
            utente.addNecessidade(necessidade);
            em.persist(necessidade);
            em.merge(utente);
        }catch (EntityAlreadyExistsException e){
            throw e;
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    
    public void removeNecessidade(int number) {
        try{
            Necessidade necessidade = em.find(Necessidade.class, number);
            em.remove(necessidade);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(int number, String name, String description) {
        try{
            Necessidade necessidade = em.find(Necessidade.class, number);
            if(necessidade == null){
                return;
            }
            necessidade.setName(name);
            necessidade.setDescription(description);
            em.merge(necessidade);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<NecessidadeDTO> getAllNecessidades(){
        try{
        List<Necessidade> necessidades = (List<Necessidade>) em.createNamedQuery("findAllNecessidades").getResultList();
        return necessidadesToDTOs(necessidades);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    public Necessidade getNecessidade(int number){
        try{
            Necessidade necessidade = em.find(Necessidade.class, number);
            return necessidade;
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    NecessidadeDTO necessidadeToDTO(Necessidade necessidade){
        return new NecessidadeDTO(
                              necessidade.getNumber(),
                              necessidade.getName(),
                              necessidade.getDescription()
                              );
    }
    
    List<NecessidadeDTO> necessidadesToDTOs(List<Necessidade> necessidades){
        List<NecessidadeDTO> dtos = new ArrayList<>();
        for(Necessidade n : necessidades){
            dtos.add(necessidadeToDTO(n));
        }
        System.out.println("nec to dto");
        return dtos;
    }
    /*
    public void associateNecessidadeToUtente(String code, int number)
            throws EntityDoesNotExistException, UtenteAssociatedException, NoSuchAlgorithmException{
        try {
               System.out.println("2.1");
            Utente utente = em.find(Utente.class, code);
            if (utente == null) {
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }
 System.out.println("2.2");
            Necessidade necessidade = em.find(Necessidade.class, number);
            if (necessidade == null) {
                throw new EntityDoesNotExistException("There is no necessidade with that number.");
            }
             System.out.println("2.3");

            if (utente.getNecessidades().contains(necessidade)) {
                throw new NoSuchAlgorithmException("Necessidade is already associated to that utente.");
            }
             System.out.println("2.4");

            if (necessidade.getUtentes().contains(utente)) {
                throw new UtenteAssociatedException("Utente is already associated to that necessidade.");
            }
 System.out.println("2.5");
            necessidade.addUtente(utente);
             System.out.println("2.6");
            utente.addNecessidade(necessidade);
             System.out.println("2.7");

        } catch (EntityDoesNotExistException | UtenteAssociatedException | NoSuchAlgorithmException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
 */
    
    public List<NecessidadeDTO> getAssociatedNecessidades(String code) throws EntityDoesNotExistException{
        try {
            Utente utente = em.find(Utente.class, code);
            if( utente == null){
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }   
            System.out.println("utent found");
            List<Necessidade> necessidades = (List<Necessidade>) utente.getNecessidades();
            return necessidadesToDTOs(necessidades);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    /*
    public void unrollNecessidade(String code, int number) 
            throws EntityDoesNotExistException, NecessidadeNotEnrolledException {
        try {
            Necessidade necessidade = em.find(Necessidade.class, number);
            if(necessidade == null){
                throw new EntityDoesNotExistException("There is no necessidade with that number.");
            }            
            
            Utente utente = em.find(Utente.class, code);
            if(utente == null){
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }
            
            if(!utente.getNecessidades().contains(necessidade)){
                throw new NecessidadeNotEnrolledException();
            }            
            
            utente.removeNecessidade(necessidade);
            necessidade.removeUtente(utente);

        } catch (EntityDoesNotExistException | NecessidadeNotEnrolledException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<NecessidadeDTO> getUnrolledNecessidades(String code) throws EntityDoesNotExistException{
        try {
            Utente utente = em.find(Utente.class, code);
            if( utente == null){
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }            
            List<Necessidade> necessidades = (List<Necessidade>) em.createNamedQuery("findAllNecessidades").getResultList();
            List<Necessidade> enrolled = em.find(Utente.class, code).getNecessidades();
            necessidades.removeAll(enrolled);
            
            return necessidadesToDTOs(necessidades);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }*/
}
