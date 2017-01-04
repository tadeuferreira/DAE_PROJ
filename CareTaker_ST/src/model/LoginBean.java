/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dtos.CuidadorDTO;
import java.security.MessageDigest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author lztd1
 */
public enum LoginBean {
    
    INSTANCE;

    String baseUri;
    Client client;
    String username;
    CuidadorDTO cuidador;
    
    private LoginBean() {
        client = ClientBuilder.newClient();
        baseUri = "http://localhost:8080/DAE_PROJ-war/webapi";
    }
    
    public boolean login(String username, String password){
        client = ClientBuilder.newClient();
        this.username = username;
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
        client.register(feature);
        try{
            cuidador = client.target(baseUri)
            .path("/caretakers/caretaker/"+username)
            .request(MediaType.APPLICATION_XML)
            .get(new GenericType<CuidadorDTO>(){});
        
        if(cuidador == null)
            return false;

        return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
      return false;
    }
     
    public String getBaseUri() {
        return baseUri;
    }

    public Client getClient() {
        return client;
    }
    
    public String getUsername(){
        return username;
    }

    public CuidadorDTO getCuidador() {
        return cuidador;
    }
    
    
    public static String sha256(String base) {
    try{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    } catch(Exception ex){
       throw new RuntimeException(ex);
    }
}

    public void reset() {
        client = ClientBuilder.newClient();
        username = "";
        cuidador = null;
    }
}
