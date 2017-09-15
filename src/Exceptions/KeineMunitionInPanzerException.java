package Exceptions;

/**
 * Exception die geworfen wird, wenn versucht wird Munition zu verwenden, aber keine vorhanden ist.
 * @author Yannick Vaske
 * @version 17.08.2017
 */
public class KeineMunitionInPanzerException extends Exception {
    public KeineMunitionInPanzerException(String message) {
        super(message);
    }
}
