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
public class MaterialAssociatedException extends Exception {

    /**
     * Creates a new instance of <code>MaterialAssociatedException</code>
     * without detail message.
     */
    public MaterialAssociatedException() {
    }

    /**
     * Constructs an instance of <code>MaterialAssociatedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public MaterialAssociatedException(String msg) {
        super(msg);
    }
}
