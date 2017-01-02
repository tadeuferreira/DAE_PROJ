/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author brunoalexandredesousahenriques
 */
public class UtenteAssociatedException extends Exception {

    /**
     * Creates a new instance of <code>UtenteAssociatedException</code> without
     * detail message.
     */
    public UtenteAssociatedException() {
    }

    /**
     * Constructs an instance of <code>UtenteAssociatedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UtenteAssociatedException(String msg) {
        super(msg);
    }
}
