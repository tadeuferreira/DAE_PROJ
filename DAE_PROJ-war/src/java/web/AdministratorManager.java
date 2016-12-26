/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AdministratorDTO;
import dtos.ProfissionalSaudeDTO;
import ejbs.AdministratorBean;
import ejbs.ProfissionalSaudeBean;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
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
   
    private AdministratorDTO newAdmin;
    private ProfissionalSaudeDTO newProfissionalSaude;
    
    private static final Logger LOGGER = Logger.getLogger("welcome");
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    private AdministratorDTO currentAdmin;
    private ProfissionalSaudeDTO currentProfissionalSaude;
    
    private UIComponent component;
    
    public AdministratorManager() {
        this.newAdmin = new AdministratorDTO();
        this.newProfissionalSaude = new ProfissionalSaudeDTO();
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
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteAdministratorId");
            String id = param.getValue().toString();
            administratorBean.removeAdministrator(id);
        }catch(Exception e){
            logger.warning("Problem removing student in method removeStudent()");
        }
    }
    
    public String createProfissionalSaude() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            profissionalSaudeBean.createProfissionalSaude(newProfissionalSaude.getUsername(), newProfissionalSaude.getPassword(), newProfissionalSaude.getName(), newProfissionalSaude.getEmail());
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
        newProfissionalSaude = new ProfissionalSaudeDTO();  
    }
    
     public String updateProfissionalSaude(){
        try{
            profissionalSaudeBean.update(
                    currentProfissionalSaude.getUsername(), 
                    currentProfissionalSaude.getPassword(),
                    currentProfissionalSaude.getName(), 
                    currentProfissionalSaude.getEmail());
            return "index?faces-redirect=true";
        
        }catch(Exception e){
            logger.warning("Problem uptading student in method updateProsissionalSaude()");
        }
        return "admin_professionals_update";
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

    public AdministratorBean getAdministratorBean() {
        return administratorBean;
    }

    public void setAdministratorBean(AdministratorBean administratorBean) {
        this.administratorBean = administratorBean;
    }

    public ProfissionalSaudeBean getProfissionalSaudeBean() {
        return profissionalSaudeBean;
    }

    public void setProfissionalSaudeBean(ProfissionalSaudeBean profissionalSaudeBean) {
        this.profissionalSaudeBean = profissionalSaudeBean;
    }

    public AdministratorDTO getNewAdmin() {
        return newAdmin;
    }

    public void setNewAdmin(AdministratorDTO newAdmin) {
        this.newAdmin = newAdmin;
    }

    public ProfissionalSaudeDTO getNewProfissionalSaude() {
        return newProfissionalSaude;
    }

    public void setNewProfissionalSaude(ProfissionalSaudeDTO newProfissionalSaude) {
        this.newProfissionalSaude = newProfissionalSaude;
    }

    public AdministratorDTO getCurrentAdmin() {
        return currentAdmin;
    }

    public void setCurrentAdmin(AdministratorDTO currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    public ProfissionalSaudeDTO getCurrentProfissionalSaude() {
        return currentProfissionalSaude;
    }

    public void setCurrentProfissionalSaude(ProfissionalSaudeDTO currentProfissionalSaude) {
        this.currentProfissionalSaude = currentProfissionalSaude;
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

