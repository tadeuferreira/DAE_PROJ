/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author brunoalexandredesousahenriques
 */
public class Utils extends Exception {

    /**
     * Creates a new instance of <code>Utils</code> without detail message.
     */
    public Utils() {
    }

    /**
     * Constructs an instance of <code>Utils</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public Utils(String msg) {
        super(msg);
    }
    
    public static String getConstraintViolationMessages(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<?> cv : cvs) {
                errorMessages.append(cv.getMessage());
                errorMessages.append("; ");
            }
        return errorMessages.toString();
    }
}
