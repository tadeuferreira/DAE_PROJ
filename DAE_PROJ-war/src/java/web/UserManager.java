/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author lztd1
 */
@ManagedBean
@SessionScoped
public class UserManager implements Serializable {
    
    /**
     * Creates a new instance of UserManagee
     */
    
    private String username;
    private String password;
    private boolean loginFlag = true;
    private static final Logger logger = Logger.getLogger("web.UserManager");
    
    public UserManager() {
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        System.out.println(username + " " + password);
        try {
            request.login(this.username, this.password);
        } catch (ServletException e) {
            System.out.println("exception");
            logger.log(Level.WARNING, e.getMessage());
            return "/error?faces-redirect=true";
        }
        System.out.println("LOGIN!");
        if(isUserInRole("Administrator")){
            System.out.println("ADMIN!");
            return "/faces/admin/admin_index?faces-redirect=true";
        }
        if(isUserInRole("Professional")){
            System.out.println("Pro!");
            return "/faces/professional/professional_index?faces-redirect=true";
        }
        return "/error?faces-redirect=true";
    }
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        // remove data from beans:
        for (String bean : context.getExternalContext().getSessionMap().keySet()) {
            context.getExternalContext().getSessionMap().remove(bean);
        }
        // destroy session:
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        // using faces-redirect to initiate a new request:
        return "/index_login.xhtml?faces-redirect=true";
    }
    
    public String clearLogin(){
        if(isSomeUserAuthenticated()){
            logout();
        }
        return "/index_login.xhtml?faces-redirect=true";
    }
    
    
    
    public boolean isUserInRole(String role) {
        return (isSomeUserAuthenticated() &&
                FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role));
    }
    public boolean isSomeUserAuthenticated() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal()!=null;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isLoginFlag() {
        return loginFlag;
    }
    
    public void setLoginFlag(boolean loginFlag) {
        this.loginFlag = loginFlag;
    }
    
    
}
