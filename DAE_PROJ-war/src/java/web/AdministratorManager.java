/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web;

import dtos.AdministratorDTO;
import dtos.CuidadorDTO;
import dtos.MaterialDTO;
import dtos.NecessidadeDTO;
import dtos.ProcedimentoDTO;
import dtos.ProfissionalSaudeDTO;
import dtos.UtenteDTO;
import ejbs.AdministratorBean;
import ejbs.CuidadorBean;
import ejbs.MaterialBean;
import ejbs.NecessidadeBean;
import ejbs.ProcedimentoBean;
import ejbs.ProfissionalSaudeBean;
import ejbs.UtenteBean;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import java.io.Serializable;
import java.util.ArrayList;
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
    
    @EJB
    private NecessidadeBean necessidadeBean;
    
    @EJB
    private ProcedimentoBean procedimentoBean;
    
    private AdministratorDTO newAdmin;
    private ProfissionalSaudeDTO newProfissionalSaude;
    private MaterialDTO newMaterial;
    private CuidadorDTO newCuidador;
    private UtenteDTO newUtente;
    private NecessidadeDTO newNecessidade;
    private ProcedimentoDTO newProcedimento;
    
    
    private static final Logger LOGGER = Logger.getLogger("welcome");
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    private AdministratorDTO currentAdmin;
    private ProfissionalSaudeDTO currentProfissionalSaude;
    private MaterialDTO currentMaterial;
    private CuidadorDTO currentCuidador;
    private UtenteDTO currentUtente;
    private NecessidadeDTO currentNecessidade;
    
    private String searchAdminUsername;
    private String searchProfUsername;
    private String searchCuidadorUsername;
    private String searchUtenteCode;
    private String searchMaterialCode;
    
    private UIComponent component;
    
    public AdministratorManager() {
        
        this.newAdmin = new AdministratorDTO();
        this.newProfissionalSaude = new ProfissionalSaudeDTO();
        this.newCuidador = new CuidadorDTO();
        this.newMaterial = new MaterialDTO();
        this.newUtente = new UtenteDTO();
        this.newProcedimento = new ProcedimentoDTO();
        this.newNecessidade = new NecessidadeDTO();
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
          return goToIndex();
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
           return goToIndex();
        } catch (EntityDoesNotExistException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        if(isUserInRole("Administrator")){
            return "/faces/professional_admin/professional_admin_cuidadores_update.xhtml?faces-redirect=true";
        }
        if(isUserInRole("Professional")){
            return "/faces/professional/professional_cuidadores_update.xhtml?faces-redirect=true";
        }
        return "/error?faces-redirect=true";
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
    
    public String searchCuidador(){
        try{
            currentCuidador = cuidadorBean.getCuidador(searchCuidadorUsername);
            return "/faces/professional_admin/professional_admin_cuidadores_details";
        
        }catch (Exception e){
            LOGGER.warning("Error: problem in method getCuidador");
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        
        }
        return null;
    }
    
    ///////ADMIN///////
    public String createAdministrator() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            administratorBean.createAdministrator(newAdmin.getUsername(), newAdmin.getPassword(), newAdmin.getName(), newAdmin.getEmail());
            clearNewAdministrator();
            return goToIndex();
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
            return goToIndex();
            
        }catch(Exception e){
            logger.warning("Problem uptading administrator in method updateAdministrator()");
        }
        return "/faces/admin/admin_admins_update";
    }
    
    public void removeAdministrator(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("adminUsername");
            String username = param.getValue().toString();
            administratorBean.removeAdministrator(username);
        }catch(Exception e){
            logger.warning("Problem removing administrator in method removeAdministrator()");
        }
    }
    
    public String searchAdministrator(){
        try{
            currentAdmin = administratorBean.getAdministrator(searchAdminUsername);
            return "admin_admins_details?faces-redirect=true";
        
        }catch (Exception e){
            LOGGER.warning("Error: problem in method getAdministrator");
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        
        }
        return null;
    }
    
    ///PROFISSIONAL DE SAUDE///
    public String createProfissionalSaude() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            profissionalSaudeBean.createProfissionalSaude(newProfissionalSaude.getUsername(), newProfissionalSaude.getPassword(), newProfissionalSaude.getName(), newProfissionalSaude.getEmail());
            clearNewProfissionalSaude();
           return goToIndex();
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
           return goToIndex();
            
        }catch(Exception e){
            logger.warning("Problem uptading Profissional in method updateProsissionalSaude()");
        }
        return "/faces/admin/admin_professionals_update";
    }
    
    public void removeProfissionalSaude(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("professionalUsername");
            String id = param.getValue().toString();
            profissionalSaudeBean.removeProfissionalSaude(id);
        }catch(Exception e){
            logger.warning("Problem removing Profissional in method removeProfissionalSaude()");
        }
    }
    
    public String searchProfessional(){
        try{
            currentProfissionalSaude = profissionalSaudeBean.getProfissional(searchProfUsername);
            return "admin_professionals_details?faces-redirect=true";
        
        }catch (Exception e){
            LOGGER.warning("Error: problem in method getProfessional");
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        
        }
        return null;
    }
    
    ///MATERIAL///
    public String createMaterial() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            materialBean.createMaterial(newMaterial.getCode(), newMaterial.getName(), newMaterial.getType(), newMaterial.getDescription());
            clearNewMaterial();
            return goToIndex();
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
                    currentMaterial.getDescription());
           return goToIndex();
            
        }catch(Exception e){
            logger.warning("Problem uptading material in method updateMaterial()");
        }
        return "/faces/professional_admin/professional_admin_materials_update";
    }
    
    public void removeMaterial(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("materialCode");
            String code = param.getValue().toString();
            materialBean.removeMaterial(code);
        }catch(Exception e){
            logger.warning("Problem removing material in method removeMaterial()");
        }
    }
    
    public List<MaterialDTO> getCurrentCuidadorMateriais() {
        try {
            return materialBean.getAssociatedMateriais(currentCuidador.getUsername());
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
    }
    
    public List<MaterialDTO> getUnrolledMaterials() {
        try {
            return materialBean.getUnrolledMaterials(currentCuidador.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public void enrollMaterial(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("materialCode");
            String code = param.getValue().toString();
            materialBean.associateMaterialtoCuidador(currentCuidador.getUsername(), code);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public void unrollMaterial(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("materialCode");
            String code = param.getValue().toString();
            materialBean.unrollMaterial(currentCuidador.getUsername(), code);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String searchMaterial(){
        try{
            currentMaterial = materialBean.getMaterial(searchMaterialCode);
            return "/faces/professional_admin/professional_admin_materials_details";
        
        }catch (Exception e){
            LOGGER.warning("Error: problem in method getMaterial");
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        
        }
        return null;
    }
    
    ///UTENTE///
    public String createUtente() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            utenteBean.createUtente(newUtente.getCode(), newUtente.getName());
            clearNewUtente();
            return goToIndex();
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
           return goToIndex();
            
        }catch(Exception e){
            logger.warning("Problem uptading utente in method updateUtente()");
        }
        if(isUserInRole("Administrator")){
            return "/faces/professional_admin/professional_admin_utentes_update.xhtml?faces-redirect=true";
        }
        if(isUserInRole("Professional")){
            return "/faces/professional/professional_utentes_update.xhtml?faces-redirect=true";
        }
        return "/error?faces-redirect=true";
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
    
    public List<UtenteDTO> getCurrentCuidadorUtentes() {
        try {
            return utenteBean.getEnrolledUtentes(currentCuidador.getUsername());
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
    }
    
    public List<UtenteDTO> getUnrolledUtentes() {
        try {
            return utenteBean.getUnrolledUtentes(currentCuidador.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public List<UtenteDTO> getEnrolledUtentes() {
        try {
            return utenteBean.getEnrolledUtentes(currentCuidador.getUsername());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public void enrollUtente(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("code");
            String code = param.getValue().toString();
            utenteBean.enrollUtenteToCuidador(currentCuidador.getUsername(), code);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public void unrollUtente(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("code");
            String code = param.getValue().toString();
            utenteBean.unrollUtente(currentCuidador.getUsername(), code);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String searchUtente(){
        try{
            currentUtente = utenteBean.getUtente(searchUtenteCode);
            return "/faces/professional_admin/professional_admin_utentes_details";
        
        }catch (Exception e){
            LOGGER.warning("Error: problem in method getUtente");
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        
        }
        return null;
    }
    
    ///NECESSIDADES///
    
    public String createNecessidade() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            necessidadeBean.createNecessidade(newNecessidade.getCode(), newNecessidade.getName(), newNecessidade.getDescription(), currentUtente.getCode());
            clearNewNecessidade();
            return "/faces/professional/professional_utentes_update";
        }catch (EntityExistsException | EntityDoesNotExistException e){
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            
        }catch(Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public List<MaterialDTO> getCurrentNecessidadeMateriais() {
        try {
            return materialBean.getAssociatedMateriaisOfNecessidade(currentNecessidade.getCode());
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
    }
    
    public List<NecessidadeDTO> getAllNecessidades(){
        try{
            return necessidadeBean.getAllNecessidades();
        }catch (Exception e){
            LOGGER.warning("Error: problem in method getAllNecessidades");
        }
        return null;
    }
    
    private void clearNewNecessidade(){
        newNecessidade = new NecessidadeDTO();
    }
    
    public String updateNecessidade(){
        try{
            necessidadeBean.update(
                    currentNecessidade.getCode(),
                    currentNecessidade.getName(),
                    currentNecessidade.getDescription()
            );
            return "/faces/professional/professional_utentes_update";
            
        }catch(Exception e){
            logger.warning("Problem uptading necessidade in method updateNecessidade()");
        }
        return "/faces/professional/professional_necessidade_update";
    }
    
    public void removeNecessidade(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("necessidadeCode");
            String code = param.getValue().toString();
            necessidadeBean.removeNecessidade(code);
        }catch(Exception e){
            logger.warning("Problem removing necessidade in method removeNecessidade()");
        }
    }
    
    public List<NecessidadeDTO> getCurrentUtenteNecessidades() {
        try {
            List<NecessidadeDTO> nec = necessidadeBean.getAssociatedNecessidades(currentUtente.getCode());
            return nec;
        } catch (Exception e) {
            System.out.println("Execption Necessidades");
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
    }
    /*
    public List<NecessidadeDTO> getUnrolledNecessidades() {
        try {
            return necessidadeBean.getUnrolledNecessidades(currentUtente.getCode());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    */
    public List<NecessidadeDTO> getEnrolledNecessidades() {
        try {
            return necessidadeBean.getAssociatedNecessidades(currentUtente.getCode());
        } catch (EntityDoesNotExistException e) {
             
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    /*
    public void enrollNecessidade(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("number");
            int number = Integer.parseInt(param.getValue().toString());
            necessidadeBean.associateNecessidadeToUtente(currentUtente.getCode(), number);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public void unrollNecessidade(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("number");
            int number = Integer.parseInt(param.getValue().toString());
            necessidadeBean.unrollNecessidade(currentUtente.getCode(), number);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    */
    public List<MaterialDTO> getUnrolledMateriaisOfNecessidade() {
        try {
            return materialBean.getUnrolledMaterialsOfNecessidade(currentNecessidade.getCode());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public List<MaterialDTO> getEnrolledMateriaisOfNecessidade() {
        try {
            return materialBean.getAssociatedMateriaisOfNecessidade(currentNecessidade.getCode());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
             System.out.println(e.toString());
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    public List<MaterialDTO> getEnrolledMateriaisOfAllNecessidade() {
        try {
            List<NecessidadeDTO> necessidades = getCurrentUtenteNecessidades();
            
            List<MaterialDTO> materials = new ArrayList<>();
            
            for(NecessidadeDTO n : necessidades){
                List<MaterialDTO> aux = materialBean.getAssociatedMateriaisOfNecessidade(n.getCode());
                for(MaterialDTO m : aux)
                    materials.add(m);
            }
            
            return materials;
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
             System.out.println(e.toString());
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public void enrollMaterialOfNecessidade(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("materialCode");
            String code = param.getValue().toString();
            System.out.println("web.AdministratorManager.enrollMaterialOfNecessidade() ->  " + code);
            materialBean.associateMaterialToNecessidade(currentNecessidade.getCode(), code);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public void unrollMaterialOfNecessidade(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("materialCode");
            String code = param.getValue().toString();
            materialBean.unrollMaterialOfNecessidade(currentNecessidade.getCode(), code);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    ///PROCEDIMENTO///
    /*
    public String createProcedimento() throws EntityAlreadyExistsException, EntityDoesNotExistException{
        try{
            procedimentoBean.createProcedimento(newProcedimento.getProcCode(), newProcedimento.getNameProc(), newProcedimento.getDescriptionProc(),
                    newProcedimento.getUtente(), newProcedimento.getNecessidade());
            clearNewProcedimento();
            return "index?faces-redirect=true";
        }catch (EntityExistsException | EntityDoesNotExistException e){
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            
        }catch(Exception e){
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    private void clearNewProcedimento(){
        newProcedimento = new ProcedimentoDTO();
    }
    
    public List<ProcedimentoDTO> getAllProcedimentos(){
        try{
            return procedimentoBean.getAllProcedimentos();
        }catch (Exception e){
            LOGGER.warning("Error: problem in method getAlProcedimentos");
        }
        return null;
    }
    
    public List<ProcedimentoDTO> getEnrolledProcedimentosOfUtente() {
        try {
            return procedimentoBean.getEnrolledProcedimentosOfUtente(currentUtente.getCode());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public List<ProcedimentoDTO> getUnrolledProcedimentos() {
        try {
            return procedimentoBean.getUnrolledProcedimentos(currentUtente.getCode());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    
    
    public List<ProcedimentoDTO> getCurrentProcedimentosUtente() {
        try {
            return procedimentoBean.getEnrolledProcedimentosOfUtente(currentUtente.getCode());
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
    }
    */
    public String goToIndex(){
         if(isUserInRole("Administrator")){
                return "/faces/admin/admin_index.xhtml?faces-redirect=true";
            }
            if(isUserInRole("Professional")){
                return "/faces/professional/professional_index.xhtml?faces-redirect=true";
            }
            return "/error?faces-redirect=true";
    }

    public String getSearchCuidadorUsername() {
        return searchCuidadorUsername;
    }

    public void setSearchCuidadorUsername(String searchCuidadorUsername) {
        this.searchCuidadorUsername = searchCuidadorUsername;
    }

    public String getSearchUtenteCode() {
        return searchUtenteCode;
    }

    public void setSearchUtenteCode(String searchUtenteCode) {
        this.searchUtenteCode = searchUtenteCode;
    }

    public String getSearchMaterialCode() {
        return searchMaterialCode;
    }

    public void setSearchMaterialCode(String searchMaterialCode) {
        this.searchMaterialCode = searchMaterialCode;
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

    public String getSearchAdminUsername() {
        return searchAdminUsername;
    }

    public void setSearchAdminUsername(String searchAdminUsername) {
        this.searchAdminUsername = searchAdminUsername;
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

    public String getSearchProfUsername() {
        return searchProfUsername;
    }

    public void setSearchProfUsername(String searchProfUsername) {
        this.searchProfUsername = searchProfUsername;
    }
    
    public NecessidadeBean getNecessidadeBean() {
        return necessidadeBean;
    }
    
    public void setNecessidadeBean(NecessidadeBean necessidadeBean) {
        this.necessidadeBean = necessidadeBean;
    }
    
    public NecessidadeDTO getNewNecessidade() {
        return newNecessidade;
    }
    
    public void setNewNecessidade(NecessidadeDTO newNecessidade) {
        this.newNecessidade = newNecessidade;
    }
    
    public NecessidadeDTO getCurrentNecessidade() {
        return currentNecessidade;
    }
    
    public void setCurrentNecessidade(NecessidadeDTO currentNecessidade) {
        this.currentNecessidade = currentNecessidade;
    }
    
    public ProcedimentoBean getProcedimentoBean() {
        return procedimentoBean;
    }
    
    public void setProcedimentoBean(ProcedimentoBean procedimentoBean) {
        this.procedimentoBean = procedimentoBean;
    }
    
    public ProcedimentoDTO getNewProcedimento() {
        return newProcedimento;
    }
    
    public void setNewProcedimento(ProcedimentoDTO newProcedimento) {
        this.newProcedimento = newProcedimento;
    }
    
    public boolean isUserInRole(String role) {
        return (isSomeUserAuthenticated() &&
                FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role));
    }
    public boolean isSomeUserAuthenticated() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal()!=null;
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

