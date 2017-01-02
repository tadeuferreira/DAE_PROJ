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
public class UtenteNotEnrolledException extends Exception {

    /**
     * Creates a new instance of <code>UtenteNotEnrolledException</code> without
     * detail message.
     */
    public UtenteNotEnrolledException() {
    }

    /**
     * Constructs an instance of <code>UtenteNotEnrolledException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UtenteNotEnrolledException(String msg) {
        super(msg);
    }
}
