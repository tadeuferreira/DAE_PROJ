/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AdministratorDTO;
import exceptions.CuidadorAssociatedException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MaterialAssociatedException;
import exceptions.MyConstraintViolationException;
import exceptions.NecessidadeAssociatedException;
import exceptions.UtenteAssociatedException;
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

    @EJB
    CuidadorBean cuidadorBean;
    
    @EJB
    NecessidadeBean necessidadeBean;

    
    @PostConstruct
    public void populateDB() {
    
        try{
           administratorBean.createAdministrator("a1", "a1", "a1", "a1@a1.com");
           administratorBean.createAdministrator("1212121", "Diogo", "Diogo", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("2323232", "Rodrigo", "Rodrigo", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("3434343", "Fernando", "Fernando", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("4545454", "Tiago", "Tiago", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("6767675", "Carlos", "Carlos", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("8989896", "Henrique", "Henrique", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("5675677", "Joao", "Joao", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("1231238", "Miguel", "Miguel", "dae.ei.ipleiria@gmail.com");

           professionalBean.createProfissionalSaude("p1", "p1", "p1", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("567456", "Ricardo", "Ricardo", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("456456", "Diogo", "Diogo", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("786788", "Tiago", "Tiago", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("243554", "Carlos", "Carlos", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("456466", "Miguel", "Miguel", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("454324", "Joao", "Joao", "dae.ei.ipleiria@gmail.com");
           professionalBean.createProfissionalSaude("978907", "Marco", "Marco", "dae.ei.ipleiria@gmail.com");
          
           materialBean.createMaterial(123456, "Pomada", "tipo 1", "20");
           materialBean.createMaterial(456689, "Penso", "tipo 2", "45");
           materialBean.createMaterial(234789, "Gotas", "tipo 3", "67");
           materialBean.createMaterial(123890, "Desinfectante", "tipo 3", "34");
           materialBean.createMaterial(765345, "Seringa", "tipo 5", "20");
           materialBean.createMaterial(123432, "Muletas", "tipo 8", "45");
           materialBean.createMaterial(123549, "Cadeira de rodas", "tipo 8", "67");
           
           utenteBean.createUtente("678867", "Rogerio");
           utenteBean.createUtente("978695", "Miguel");
           utenteBean.createUtente("456372", "Joao");
           utenteBean.createUtente("342534", "Maria");
           utenteBean.createUtente("453983", "Isabel");
           
           cuidadorBean.create("c1", "c1", "c1", "c1@c1.com");
           cuidadorBean.create("56745612", "Ricardo", "Ricardo", "dae.ei.ipleiria@gmail.com");
           cuidadorBean.create("45645634", "Diogo", "Diogo", "dae.ei.ipleiria@gmail.com");
           cuidadorBean.create("78678856", "Tiago", "Tiago", "dae.ei.ipleiria@gmail.com");
           cuidadorBean.create("24355478", "Carlos", "Carlos", "dae.ei.ipleiria@gmail.com");
           cuidadorBean.create("45646690", "Miguel", "Miguel", "dae.ei.ipleiria@gmail.com");
           cuidadorBean.create("45432413", "Joao", "Joao", "dae.ei.ipleiria@gmail.com");
           cuidadorBean.create("97890746", "Marco", "Marco", "dae.ei.ipleiria@gmail.com");
           
           utenteBean.enrollUtenteToCuidador("c1", "678867");
           utenteBean.enrollUtenteToCuidador("c1", "978695");
           utenteBean.enrollUtenteToCuidador("c1", "456372");
           
           materialBean.associateMaterialtoCuidador("c1", 123456);
           materialBean.associateMaterialtoCuidador("c1", 456689);
           materialBean.associateMaterialtoCuidador("c1", 456689);
           materialBean.associateMaterialtoCuidador("24355478", 123456);
           materialBean.associateMaterialtoCuidador("56745612", 123890);
           materialBean.associateMaterialtoCuidador("24355478", 234789);
           
           
           necessidadeBean.createNecessidade(1, "massagem", "nos pes", "453983");
           necessidadeBean.createNecessidade(2, "massagem", "nos ombros", "453983");
           necessidadeBean.createNecessidade(3, "massagem", "nos costas", "453983");
           necessidadeBean.createNecessidade(4, "massagem", "nos pernas", "453983");
           
           materialBean.associateMaterialToNecessidade(1, 123456);
           materialBean.associateMaterialToNecessidade(2, 234789);
           materialBean.associateMaterialToNecessidade(3, 123432);
           materialBean.associateMaterialToNecessidade(4, 123456);
           
           utenteBean.enrollUtenteToCuidador("45645634", "453983");
           utenteBean.enrollUtenteToCuidador("45645634", "978695");
           utenteBean.enrollUtenteToCuidador("45645634", "342534");
           
       }catch (EntityAlreadyExistsException | 
               EntityDoesNotExistException | 
               MyConstraintViolationException | 
               CuidadorAssociatedException | 
               MaterialAssociatedException |
               NecessidadeAssociatedException |
               UtenteAssociatedException e){
           System.err.println("Error:" + e.getMessage());
       }

           List<AdministratorDTO> administrators = administratorBean.getAllAdministrators();
           
           administrators.forEach((a) -> {
               System.out.println(a);
        });  
           
    }    
}
