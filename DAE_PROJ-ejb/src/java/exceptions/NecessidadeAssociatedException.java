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
public class NecessidadeAssociatedException extends Exception {

    /**
     * Creates a new instance of <code>NecessidadeAssociatedException</code>
     * without detail message.
     */
    public NecessidadeAssociatedException() {
    }

    /**
     * Constructs an instance of <code>NecessidadeAssociatedException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NecessidadeAssociatedException(String msg) {
        super(msg);
    }
}
