package Exceptions;

/**
 * Exception die geworfen wird, wenn der Panzer versucht sich nach vorne zu bewegen, aber die naechste Kachel nicht frei für den Panzer ist.
 * @author Yannick Vaske
 * @version 17.08.2017
 */
public class VorneNichtFreiException extends Exception {
    public VorneNichtFreiException(String message) {
        super(message);
    }
}
