/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AdministratorDTO;
import dtos.CuidadorDTO;
import dtos.MaterialDTO;
import dtos.ProfissionalSaudeDTO;
import dtos.UtenteDTO;
import ejbs.AdministratorBean;
import ejbs.CuidadorBean;
import ejbs.MaterialBean;
import ejbs.ProfissionalSaudeBean;
import ejbs.UtenteBean;
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
    
    @EJB
    private MaterialBean materialBean;
    
    @EJB
    private UtenteBean utenteBean;
   
    private AdministratorDTO newAdmin;
    private ProfissionalSaudeDTO newProfissionalSaude;
    private MaterialDTO newMaterial;
    private CuidadorDTO newCuidador;
    private UtenteDTO newUtente;
    
    private static final Logger LOGGER = Logger.getLogger("welcome");
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    private AdministratorDTO currentAdmin;
    private ProfissionalSaudeDTO currentProfissionalSaude;
    private MaterialDTO currentMaterial;
    private CuidadorDTO currentCuidador;
    private UtenteDTO currentUtente;
    
    private UIComponent component;
    
    public AdministratorManager() {

        this.newAdmin = new AdministratorDTO();
        this.newProfissionalSaude = new ProfissionalSaudeDTO();
        this.newCuidador = new CuidadorDTO();
        this.newMaterial = new MaterialDTO();
        this.newUtente = new UtenteDTO();
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
    
   ///////ADMIN///////
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
    
    ///PROFISSIONAL DE SAUDE///
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
            UIParameter param = (UIParameter) event.getComponent().findComponent("professionalUsername");
            String id = param.getValue().toString();
            profissionalSaudeBean.removeProfissionalSaude(id);
        }catch(Exception e){
            logger.warning("Problem removing student in method removeProfissionalSaude()");
        }
    }
    
    ///MATERIAL///
    public String createMaterial() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            materialBean.createMaterial(newMaterial.getCode(), newMaterial.getName(), newMaterial.getType(), newMaterial.getQuantity());
            clearNewMaterial();
            return "index?faces-redirect=true";
        }catch (EntityExistsException | EntityDoesNotExistException e){
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            
        }catch(Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public List<MaterialDTO> getAllMateriais(){
        try{
            return materialBean.getAllMaterials();
        }catch (Exception e){
             LOGGER.warning("Error: problem in method getAllProfissionaisSaude");
        }
        return null;
    }
    
    private void clearNewMaterial(){
        newMaterial = new MaterialDTO();  
    }
    
    public String updateMaterial(){
        try{
            materialBean.update(
                    currentMaterial.getCode(), 
                    currentMaterial.getName(),
                    currentMaterial.getType(), 
                    currentMaterial.getQuantity());
            return "index?faces-redirect=true";
        
        }catch(Exception e){
            logger.warning("Problem uptading material in method updateMaterial()");
        }
        return "admin_materials_update";
    }

    public void removeMaterial(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("materialCode");
            String id = param.getValue().toString();
            materialBean.removeMaterial(id);
        }catch(Exception e){
            logger.warning("Problem removing student in method removeProfissionalSaude()");
        }
    }
    
    ///UTENTE///
    public String createUtente() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            utenteBean.createUtente(newUtente.getCode(), newUtente.getName());
            clearNewUtente();
            return "index?faces-redirect=true";
        }catch (EntityExistsException | EntityDoesNotExistException e){
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            
        }catch(Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public List<UtenteDTO> getAllUtentes(){
        try{
            return utenteBean.getAllUtentes();
        }catch (Exception e){
             LOGGER.warning("Error: problem in method getAllUtentes");
        }
        return null;
    }
    
    private void clearNewUtente(){
        newUtente = new UtenteDTO();  
    }
    
    public String updateUtente(){
        try{
            utenteBean.update(
                    currentUtente.getCode(), 
                    currentUtente.getName()
                    );
            return "index?faces-redirect=true";
        
        }catch(Exception e){
            logger.warning("Problem uptading utente in method updateUtente()");
        }
        return "admin_utentes_update";
    }

    public void removeUtente(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("utenteCode");
            String id = param.getValue().toString();
            utenteBean.removeUtente(id);
        }catch(Exception e){
            logger.warning("Problem removing utente in method removeUtente()");
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

    public CuidadorBean getCuidadorBean() {
        return cuidadorBean;
    }

    public void setCuidadorBean(CuidadorBean cuidadorBean) {
        this.cuidadorBean = cuidadorBean;
    }

    public MaterialBean getMaterialBean() {
        return materialBean;
    }

    public void setMaterialBean(MaterialBean materialBean) {
        this.materialBean = materialBean;
    }

    public MaterialDTO getNewMaterial() {
        return newMaterial;
    }

    public void setNewMaterial(MaterialDTO newMaterial) {
        this.newMaterial = newMaterial;
    }

    public CuidadorDTO getNewCuidador() {
        return newCuidador;
    }

    public void setNewCuidador(CuidadorDTO newCuidador) {
        this.newCuidador = newCuidador;
    }

    public MaterialDTO getCurrentMaterial() {
        return currentMaterial;
    }

    public void setCurrentMaterial(MaterialDTO currentMaterial) {
        this.currentMaterial = currentMaterial;
    }

    public CuidadorDTO getCurrentCuidador() {
        return currentCuidador;
    }

    public void setCurrentCuidador(CuidadorDTO currentCuidador) {
        this.currentCuidador = currentCuidador;
    }

    public UtenteBean getUtenteBean() {
        return utenteBean;
    }

    public void setUtenteBean(UtenteBean utenteBean) {
        this.utenteBean = utenteBean;
    }

    public UtenteDTO getNewUtente() {
        return newUtente;
    }

    public void setNewUtente(UtenteDTO newUtente) {
        this.newUtente = newUtente;
    }

    public UtenteDTO getCurrentUtente() {
        return currentUtente;
    }

    public void setCurrentUtente(UtenteDTO currentUtente) {
        this.currentUtente = currentUtente;
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

