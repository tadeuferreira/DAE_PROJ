/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Daniel
 */
public class CuidadorAssociatedException extends Exception {

    /**
     * Creates a new instance of <code>CuidadorAssociatedException</code>
     * without detail message.
     */
    public CuidadorAssociatedException() {
    }

    /**
     * Constructs an instance of <code>CuidadorAssociatedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CuidadorAssociatedException(String msg) {
        super(msg);
    }
}
