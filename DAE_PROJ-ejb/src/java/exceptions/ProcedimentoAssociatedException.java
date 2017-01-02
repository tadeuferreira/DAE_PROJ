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
public class ProcedimentoAssociatedException extends Exception {

    /**
     * Creates a new instance of <code>ProcedimentoAssociatedException</code>
     * without detail message.
     */
    public ProcedimentoAssociatedException() {
    }

    /**
     * Constructs an instance of <code>ProcedimentoAssociatedException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ProcedimentoAssociatedException(String msg) {
        super(msg);
    }
}
