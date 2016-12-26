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
    
    @PostConstruct
    public void populateDB() {
    
        try{
          
           administratorBean.createAdministrator("121212", "Diogo", "Diogo", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("232323", "Rodrigo", "Rodrigo", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("343434", "Fernando", "Fernando", "dae.ei.ipleiria@gmail.com");
           administratorBean.createAdministrator("454545", "Tiago", "Tiago", "dae.ei.ipleiria@gmail.com");
          
      
       }catch (EntityAlreadyExistsException | EntityDoesNotExistException | MyConstraintViolationException e){
          System.err.println("Error:" + e.getMessage());
       }

       
           List<AdministratorDTO> administrators = administratorBean.getAllAdministrators();
           
           administrators.forEach((a) -> {
               System.out.println(a);
        });  
    }    
}
