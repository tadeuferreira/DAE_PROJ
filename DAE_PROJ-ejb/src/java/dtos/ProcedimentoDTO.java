/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Cuidador;
import entities.Utente;
import java.io.Serializable;

/**
 *
 * @author brunoalexandredesousahenriques
 */
public class ProcedimentoDTO implements Serializable{
    
    protected int procCode;
    protected String nameProc;
    protected String descriptionProc;
    protected String necessidade;
    protected String utente;
    
    public ProcedimentoDTO(){
        
    }
    
    public ProcedimentoDTO(int procCode, String nameProc, String descriptionProc, String utente, String necessidade ){
        this.procCode = procCode;
        this.nameProc = nameProc;
        this.descriptionProc = descriptionProc;
       
        this.utente = utente;
        this.necessidade = necessidade;
    }

    public int getProcCode() {
        return procCode;
    }

    public void setProcCode(int procCode) {
        this.procCode = procCode;
    }

    public String getNameProc() {
        return nameProc;
    }

    public void setNameProc(String nameProc) {
        this.nameProc = nameProc;
    }

    public String getDescriptionProc() {
        return descriptionProc;
    }

    public void setDescriptionProc(String descriptionProc) {
        this.descriptionProc = descriptionProc;
    }

    public String getNecessidade() {
        return necessidade;
    }

    public void setNecessidade(String necessidade) {
        this.necessidade = necessidade;
    }

   

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }
    
    
}
