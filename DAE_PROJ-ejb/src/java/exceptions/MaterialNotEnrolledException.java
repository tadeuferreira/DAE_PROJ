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
public class MaterialNotEnrolledException extends Exception {

    /**
     * Creates a new instance of <code>MaterialNotEnrolledException</code>
     * without detail message.
     */
    public MaterialNotEnrolledException() {
    }

    /**
     * Constructs an instance of <code>MaterialNotEnrolledException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public MaterialNotEnrolledException(String msg) {
        super(msg);
    }
}
