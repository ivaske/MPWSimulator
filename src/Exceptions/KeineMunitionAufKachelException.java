package Exceptions;

/**
 * Exception die geworfen wird, wenn keine Muniton auf einer Model.Kachel liegt, aber es versucht wird, Munition aufzuheben.
 * @author Yannick Vaske
 * @version 17.08.2017
 */
public class KeineMunitionAufKachelException extends Exception {
    public KeineMunitionAufKachelException(String message) {
        super(message);
    }
}
