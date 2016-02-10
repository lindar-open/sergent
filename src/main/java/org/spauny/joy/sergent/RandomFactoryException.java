package org.spauny.joy.sergent;

/**
 *
 * @author iulian.dafinoiu
 */
public class RandomFactoryException extends RuntimeException {
    private static final long serialVersionUID = 1845120741085209647L;
    public RandomFactoryException() {
        super();
    }

    public RandomFactoryException(String message) {
        super(message);
    }

    public RandomFactoryException(Throwable cause) {
        super(cause);
    }

    public RandomFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
