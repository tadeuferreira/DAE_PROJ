/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.MaterialDTO;
import dtos.ProcedimentoDTO;
import entities.Procedimento;
import entities.Utente;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.ProcedimentoAssociatedException;
import exceptions.UtenteAssociatedException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Path;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Stateless
public class ProcedimentoBean {

    @PersistenceContext
    private EntityManager em;
    
    
    public void createProcedimento(int procCode, String nameProc, String descriptionProc, String utente, String necessidade) 
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        
        try{  
            if(em.find(Procedimento.class,procCode)!= null){
               throw new EntityAlreadyExistsException("A procedimento whith that code already exists");
            }
         
            Procedimento procedimento = new Procedimento(procCode, nameProc, descriptionProc, utente, necessidade);
            em.persist(procedimento);
            enrollProcedimentoToUtente(procCode,utente);
       // EntityDoesNotExistException missing
        }catch (EntityAlreadyExistsException e){
            throw e;
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    
    public void enrollProcedimentoToUtente(int procCode, String code)
            throws EntityDoesNotExistException, UtenteAssociatedException, ProcedimentoAssociatedException{
        try {

            Utente utente = em.find(Utente.class, code);
            if (utente == null) {
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }

            Procedimento procedimento = em.find(Procedimento.class, procCode);
            if (procedimento == null) {
                throw new EntityDoesNotExistException("There is no procedimento with that code.");
            }

            if (utente.getProcedimentos().contains(procedimento)) {
                throw new ProcedimentoAssociatedException("Procedimento is already associated to that utente.");
            }

            if (procedimento.getUtentes().contains(utente)) {
                throw new UtenteAssociatedException("Utente is already associated to that procediemnto.");
            }

            procedimento.addUtente(utente);
            utente.addProcedimento(procedimento);

        } catch (EntityDoesNotExistException | UtenteAssociatedException | ProcedimentoAssociatedException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
    
    public List<ProcedimentoDTO> getAllProcedimentos(){
        try{
        List<Procedimento> procedimentos = (List<Procedimento>) em.createNamedQuery("findAllProcedimentos").getResultList();
        return procedimentosToDTOs(procedimentos);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }  
    
    public Procedimento getProcedimento(int procCode){
        try{
            Procedimento procedimento = em.find(Procedimento.class, procCode);
            return procedimento;
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    ProcedimentoDTO procedimentoToDTO(Procedimento procedimento){
        return new ProcedimentoDTO(
                        procedimento.getProcCode(),
                        procedimento.getNameProc(),
                        procedimento.getDescriptionProc(),
                        procedimento.getUtente(),
                        procedimento.getNecessidade());
        
    }
    
    List<ProcedimentoDTO> procedimentosToDTOs(List<Procedimento> procedimentos){
        List<ProcedimentoDTO> dtos = new ArrayList<>();
        for(Procedimento p : procedimentos){
            dtos.add(procedimentoToDTO(p));
        }
        return dtos;
    }
    
    public List<ProcedimentoDTO> getEnrolledProcedimentosOfUtente(String code) throws EntityDoesNotExistException{
        try {
            Utente utente = em.find(Utente.class, code);
            if( utente == null){
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }            
            List<Procedimento> procedimentos = (List<Procedimento>) utente.getProcedimentos();
            return procedimentosToDTOs(procedimentos);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
     public List<ProcedimentoDTO> getUnrolledProcedimentos(String code) throws EntityDoesNotExistException{
        try {
            Utente utente = em.find(Utente.class, code);
            if( utente == null){
                throw new EntityDoesNotExistException("There is no utente with that code.");
            }            
            List<Procedimento> procedimentos = (List<Procedimento>) em.createNamedQuery("findAllProcedimentos").getResultList();
            List<Procedimento> enrolled = em.find(Utente.class, code).getProcedimentos();
            procedimentos.removeAll(enrolled);
            
            return procedimentosToDTOs(procedimentos);
        } catch (EntityDoesNotExistException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
}
