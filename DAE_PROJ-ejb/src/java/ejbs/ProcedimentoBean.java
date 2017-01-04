/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.MaterialDTO;
import dtos.NecessidadeDTO;
import dtos.ProcedimentoDTO;
import dtos.UtenteDTO;
import entities.Material;
import entities.Necessidade;
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
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Stateless
@Path("/procedures")
public class ProcedimentoBean {

    @PersistenceContext
    private EntityManager em;
    
    @POST
    @RolesAllowed({"Cuidador"})
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public void createProcedimento(ProcedimentoDTO procedimentoDTO) 
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        
        try{  
            if(em.find(Procedimento.class, procedimentoDTO.getCode())!= null){
               throw new EntityAlreadyExistsException("A procedimento whith that code already exists");
            }
            Necessidade necessidade = em.find(Necessidade.class, procedimentoDTO.getNecessidadeCode());
            if(necessidade == null){
                throw new EntityDoesNotExistException("cant find necessidade");
            }
            
            if(necessidade.getUtente() == null){
                throw new EntityDoesNotExistException("cant find utente");
            }
            Procedimento procedimento = new Procedimento(procedimentoDTO.getCode(),
                    procedimentoDTO.getName(), procedimentoDTO.getDescription(),necessidade,necessidade.getUtente());
            em.persist(procedimento);
       // EntityDoesNotExistException missing
        }catch (EntityAlreadyExistsException e){
            throw e;
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Cuidador"})
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public void updateProcedimento(ProcedimentoDTO procedimentoDTO) 
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        
        try{  
            Procedimento proc = em.find(Procedimento.class, procedimentoDTO.getCode());
            if(proc == null){
               throw new EntityDoesNotExistException("cant find proc");
            }
            Necessidade necessidade = em.find(Necessidade.class, procedimentoDTO.getNecessidadeCode());
            if(necessidade == null){
                throw new EntityDoesNotExistException("cant find necessidade");
            }
            Utente utente = em.find(Utente.class, procedimentoDTO.getUtenteCode());
            if(utente == null){
                throw new EntityDoesNotExistException("cant find utente");
            }
            proc.setName(procedimentoDTO.getName());
            proc.setDescription(procedimentoDTO.getDescription());
            proc.setNecessidade(necessidade);
            proc.setUtente(utente);
            em.merge(proc);
            em.merge(utente);
            em.merge(necessidade);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    @POST
    @RolesAllowed({"Cuidador"})
    @Path("remove")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
     public void removeProcedimento(ProcedimentoDTO procedimentoDTO) 
        throws EntityAlreadyExistsException, EntityDoesNotExistException, MyConstraintViolationException{
        try{
            Procedimento proc = em.find(Procedimento.class, procedimentoDTO.getCode());
            if(proc == null){
               throw new EntityDoesNotExistException("cant find proc");
            }
             Necessidade necessidade = em.find(Necessidade.class, procedimentoDTO.getNecessidadeCode());
            if(necessidade == null){
                throw new EntityDoesNotExistException("cant find necessidade");
            }
            Utente utente = em.find(Utente.class, procedimentoDTO.getUtenteCode());
            if(utente == null){
                throw new EntityDoesNotExistException("cant find utente");
            }
            necessidade.setProcedimento(null);
            utente.removeProcedimento(proc);
            em.remove(proc);
            em.merge(necessidade);
            em.merge(utente);                        
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
     
    @GET
    @RolesAllowed({"Cuidador"})
    @Path("necessity/{code}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public ProcedimentoDTO getProcedimentoNecessidade(@PathParam("code") String code) throws EntityDoesNotExistException {
        try {
         Necessidade necessidade = em.find(Necessidade.class, code);
         if(necessidade == null)
             throw new EntityDoesNotExistException("cant find necessidade");
        return procedimentoToDTO(necessidade.getProcedimento());
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @RolesAllowed({"Cuidador"})
    @Path("patient/{code}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<ProcedimentoDTO> getProcedimentosUtente(@PathParam("code") String code) throws EntityDoesNotExistException {
        List<MaterialDTO> mats = null;
        try {
         Utente utente = em.find(Utente.class, code);
         if(utente == null)
             throw new EntityDoesNotExistException("cant find Utente");
        return procedimentosToDTOs(utente.getProcedimentos());
        } catch (EntityDoesNotExistException e) {
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
    
    public Procedimento getProcedimento(String code){
        try{
            Procedimento procedimento = em.find(Procedimento.class, code);
            return procedimento;
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    } 
    
    ProcedimentoDTO procedimentoToDTO(Procedimento procedimento){
        return new ProcedimentoDTO(
                        procedimento.getCode(),
                        procedimento.getName(),
                        procedimento.getDescription(),                    
                        procedimento.getNecessidade().getCode(),
                        procedimento.getUtente().getCode());
        
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
