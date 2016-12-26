/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AdministratorDTO;
import dtos.CuidadorDTO;
import dtos.ProfissionalSaudeDTO;
import ejbs.AdministratorBean;
import ejbs.CuidadorBean;
import ejbs.ProfissionalSaudeBean;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.persistence.EntityExistsException;

/**
 *
 * @author brunoalexandredesousahenriques
 */
@Named("administratorManager")
@ManagedBean
@SessionScoped
public class AdministratorManager implements Serializable {
  
    @EJB
    private AdministratorBean administratorBean;
    
    @EJB
    private ProfissionalSaudeBean profissionalSaudeBean;
    
    @EJB
    private CuidadorBean cuidadorBean;
    
    private AdministratorDTO newAdmin;
    private AdministratorDTO currentAdmin;
    
    private String newProfissionalSaudeUsername;
    private String newProfissionalSaudePassword;
    private String newProfissionalSaudeEmail; 
    private String newProfissionalSaudeName; 
    
    private static final Logger LOGGER = Logger.getLogger("welcome");
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    private CuidadorDTO currentCuidador;
    private CuidadorDTO newCuidador;
    
    private UIComponent component;
    
    public AdministratorManager() {
        this.newCuidador = new CuidadorDTO();
        this.newAdmin = new AdministratorDTO();
    }
    
    ///////CUIDADOR///////
    public String createCuidador() {
        try {
            cuidadorBean.create(
                    newCuidador.getUsername(),
                    newCuidador.getPassword(),
                    newCuidador.getName(),
                    newCuidador.getEmail());
            newCuidador.reset();
            return "index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | EntityDoesNotExistException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }

    public List<CuidadorDTO> getAllCuidadores() {
        try {
            return cuidadorBean.getAllCuidadores();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public String updateCuidador() {
        try {
            cuidadorBean.update(
                    currentCuidador.getUsername(),
                    currentCuidador.getPassword(),
                    currentCuidador.getName(),
                    currentCuidador.getEmail());
            return "index?faces-redirect=true";
        } catch (EntityDoesNotExistException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "prof_cuidadores_update";
    }

    public void removeCuidador(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("cuidadorUsername");
            String id = param.getValue().toString();
            cuidadorBean.remove(id);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    
    
    
    
    public String createAdministrator() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            administratorBean.createAdministrator(newAdmin.getUsername(), newAdmin.getPassword(), newAdmin.getName(), newAdmin.getEmail());
            clearNewAdministrator();
            return "index?faces-redirect=true";
        }catch (EntityExistsException | EntityDoesNotExistException e){
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            
        }catch(Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
     public List<AdministratorDTO> getAllAdministrators(){
        try{
            System.out.println("GetAllAdmins");
            return administratorBean.getAllAdministrators();
        }catch (Exception e){
             LOGGER.warning("Error: problem in method getAllAdministrators");
        }
        return null;
    }
    
     
    private void clearNewAdministrator(){
     newAdmin = new AdministratorDTO();  
    }
    
     public String updateAdministrator(){
        try{
            administratorBean.update(
                    currentAdmin.getUsername(), 
                    currentAdmin.getPassword(),
                    currentAdmin.getName(), 
                    currentAdmin.getEmail());
            return "index?faces-redirect=true";
        
        }catch(Exception e){
            logger.warning("Problem uptading student in method updateAdministrator()");
        }
        return "admin_admins_update";
    }
     
    public void removeAdministrator(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("adminUsername");
            String username = param.getValue().toString();
            administratorBean.removeAdministrator(username);
        }catch(Exception e){
            logger.warning("Problem removing student in method removeStudent()");
        }
    }
    
    public String createProfissionalSaude() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            profissionalSaudeBean.createProfissionalSaude(newProfissionalSaudeUsername, newProfissionalSaudePassword, newProfissionalSaudeName, newProfissionalSaudeEmail);
            clearNewProfissionalSaude();
            return "index?faces-redirect=true";
        }catch (EntityExistsException | EntityDoesNotExistException e){
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            
        }catch(Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
     public List<ProfissionalSaudeDTO> getAllProfessionaisSaude(){
        try{
            return profissionalSaudeBean.getAllProfissionaisSaude();
        }catch (Exception e){
             LOGGER.warning("Error: problem in method getAllProfissionaisSaude");
        }
        return null;
    }
    
     
    private void clearNewProfissionalSaude(){
        setNewProfissionalSaudeUsername(null);
        setNewProfissionalSaudePassword(null);
        setNewProfissionalSaudeEmail(null);
        setNewProfissionalSaudeName(null);
       
    }
     
    public void removeProfissionalSaude(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteProfissionalSaudeId");
            String id = param.getValue().toString();
            profissionalSaudeBean.removeProfissionalSaude(id);
        }catch(Exception e){
            logger.warning("Problem removing student in method removeProfissionalSaude()");
        }
    }
    
    public void setNewProfissionalSaudeUsername(String newProfissionalSaudeUsername) {
        this.newProfissionalSaudeUsername = newProfissionalSaudeUsername;
    }
    
    public String getNewProfissionalSaudePassword() {
        return newProfissionalSaudePassword;
    }

    public void setNewProfissionalSaudePassword(String newProfissionalSaudePassword) {
        this.newProfissionalSaudePassword = newProfissionalSaudePassword;
    }
    
    public String getNewProfissionalSaudeEmail() {
        return newProfissionalSaudeEmail;
    }

    public void setNewProfissionalSaudeEmail(String newProfissionalSaudeEmail) {
        this.newProfissionalSaudeEmail = newProfissionalSaudeEmail;
    }
    
    public String getNewProfissionalSaudeName() {
        return newProfissionalSaudeName;
    }

    public void setNewProfissionalSaudeName(String newProfissionalSaudeName) {
        this.newProfissionalSaudeName = newProfissionalSaudeName;
    }

    public AdministratorDTO getNewAdmin() {
        return newAdmin;
    }

    public void setNewAdmin(AdministratorDTO newAdmin) {
        this.newAdmin = newAdmin;
    }

    public AdministratorDTO getCurrentAdmin() {
        return currentAdmin;
    }

    public void setCurrentAdmin(AdministratorDTO currentAdmin) {
        this.currentAdmin = currentAdmin;
    }
    
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
    
     public void validateUsername(FacesContext context, UIComponent toValidate, Object value) {
            try {
                //Your validation code goes here
                String username = (String) value;
                //If the validation fails
                    if (username.startsWith("xpto")) {
                        FacesMessage message = new FacesMessage("Error: invalid username.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        context.addMessage(toValidate.getClientId(context), message);
                        ((UIInput) toValidate).setValid(false);
                    }
            } catch (Exception e) {
                FacesExceptionHandler.handleException(e, "Unknown error.", logger);
            }
    }
    
}

