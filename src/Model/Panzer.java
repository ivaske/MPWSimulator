package Model;

import Exceptions.KeineMunitionAufKachelException;
import Exceptions.KeineMunitionInPanzerException;
import Exceptions.VorneNichtFreiException;

/**
 * Klasse des Panzers, der durch die Landschaft navigiert
 *
 * @author Yannick Vaske
 * @version 17.08.2017
 * @see Landschaft
 */
public class Panzer {

    private final Landschaft _landschaft;

    /**
     * Konstruktor des Panzers. Dieser Erfordert das vorheriger Erstellen der Model.Landschaft.
     * @param landschaft Die Model.Landschaft in dem sich der Model.Panzer bewegt.
     */
    public Panzer(Landschaft landschaft) {
        _landschaft = landschaft;
    }

    /**
     * Funktion um den Model.Panzer eine Position weiter in der aktuellen Ausrichung zu bewegen.
     *
     * @throws VorneNichtFreiException Wenn eine Wand oder der Spielfeldrand vorraus ist.
     */
    public void vor() throws VorneNichtFreiException {
        _landschaft.vor();
    }

    /**
     * Dreht die Model.Ausrichtung des Panzers gegen Uhrzeigersinn um 90 Grad.
     */
    public void linksUm() {
        _landschaft.linksUm();
    }

    /**
     * Nimmt, wenn die aktuelle Model.Kachel Munition enthält, eine Einheit Munition von der Model.Kachel in den Model.Panzer auf.
     *
     * @throws KeineMunitionAufKachelException Wird geworfen, wenn keine Munition auf der Model.Kachel liegt.
     */
    public void nimm() throws KeineMunitionAufKachelException {
        _landschaft.nimm();
    }

    /**
     * Verwendet eine Munition aus dem Model.Panzer um eine Wand auf dem Feld einen voraus in der aktuellen Model.Ausrichtung zu zerstören.
     * Verwendet auch dann eine Einheit Munition, wenn keine Wand voraus ist.
     *
     * @throws KeineMunitionInPanzerException Wird geworfen, wenn keine Munition im Model.Panzer vorhanden ist.
     */
    public void schiessen() throws KeineMunitionInPanzerException {
        _landschaft.schiessen();
    }

    /**
     * Fragt ab, ob das Feld einen voraus in der aktuellen Model.Ausrichtung für den Model.Panzer frei ist.
     *
     * @return Boolean, ob das Feld frei ist.
     */
    public boolean vornFrei() {
        return _landschaft.vornFrei();
    }

    /**
     * Fragt ab, ob die Munition im Model.Panzer leer ist.
     *
     * @return Boolean, ob die Munition im Model.Panzer leer ist.
     */
    public boolean munitionLeer() {
        return _landschaft.munitionLeer();
    }

    /**
     * Fragt ab, ob auf der aktuellen Model.Kachel Munition vorhanden ist.
     *
     * @return Boolean, ob das Feld Munition beinhaltet
     */
    public boolean munitionDa() {
        return _landschaft.munitionDa();
    }

    /**
     * Fragt ab, ob eine Wand auf dem nächsten Feld ist.
     *
     * @return Gibt true zurück, wenn eine Wand vorraus ist. Gibt jedoch false zurück, wenn der Spielfeldrand vorraus ist.
     */
    public boolean wandVoraus() {
        return _landschaft.wandVoraus();
    }
}
