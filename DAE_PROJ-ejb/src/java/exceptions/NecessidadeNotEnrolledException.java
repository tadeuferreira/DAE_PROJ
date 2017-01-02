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
public class NecessidadeNotEnrolledException extends Exception {

    /**
     * Creates a new instance of <code>NecessidadeNotEnrolledException</code>
     * without detail message.
     */
    public NecessidadeNotEnrolledException() {
    }

    /**
     * Constructs an instance of <code>NecessidadeNotEnrolledException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NecessidadeNotEnrolledException(String msg) {
        super(msg);
    }
}
