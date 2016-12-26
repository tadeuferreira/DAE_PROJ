/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AdministratorDTO;
import dtos.ProfissionalSaudeDTO;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author lztd1
 */
@Singleton
@Startup
public class ConfigBean {

    @EJB
    AdministratorBean administratorBean;
    
    @EJB
    ProfissionalSaudeBean professionalBean;
    
    @EJB
    MaterialBean materialBean;
    
    @EJB
    UtenteBean utenteBean;
    
    @PostConstruct
    public void populateDB() {
    
        try{
          
           administratorBean.createAdministrator("121212", "Diogo", "Diogo", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("232323", "Rodrigo", "Rodrigo", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("343434", "Fernando", "Fernando", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("454545", "Tiago", "Tiago", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("676767", "Carlos", "Carlos", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("898989", "Henrique", "Henrique", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("567567", "Joao", "Joao", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("123123", "Miguel", "Miguel", "dae.ei.ipleiria@gmail.com");
           
           
           professionalBean.createProfissionalSaude("567456", "Ricardo", "Ricardo", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("456456", "Diogo", "Diogo", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("786788", "Tiago", "Tiago", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("243554", "Carlos", "Carlos", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("456466", "Miguel", "Miguel", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("454324", "Joao", "Joao", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("978907", "Marco", "Marco", "dae.ei.ipleiria@gmail.com");
           
           
           materialBean.createMaterial("123456", "Pomada", "tipo 1", "20");
           materialBean.createMaterial("456689", "Penso", "tipo 2", "45");
           materialBean.createMaterial("234789", "Gotas", "tipo 3", "67");
           materialBean.createMaterial("123890", "Desinfectante", "tipo 3", "34");
           
           
           utenteBean.createUtente("678867", "Rogerio");
           utenteBean.createUtente("978695", "Miguel");
           utenteBean.createUtente("456372", "Joao");
           utenteBean.createUtente("342534", "Maria");
           utenteBean.createUtente("453983", "Isabel");
                    
       }catch (EntityAlreadyExistsException | EntityDoesNotExistException | MyConstraintViolationException e){
          System.err.println("Error:" + e.getMessage());
       }

       
           List<AdministratorDTO> administrators = administratorBean.getAllAdministrators();
           
           administrators.forEach((a) -> {
               System.out.println(a);
        });  
           
    }    
}
