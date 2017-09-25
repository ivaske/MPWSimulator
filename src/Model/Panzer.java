package Model;

import Controller.AktionenButtonController;

/**
 * Klasse des Panzers, der durch die Landschaft navigiert
 *
 * @author Yannick Vaske
 * @version 17.08.2017
 * @see Landschaft
 */
public class Panzer {

    private final Landschaft _landschaft;
    private AktionenButtonController _controller;

    /**
     * Konstruktor des Panzers. Dieser Erfordert das vorheriger Erstellen der Model.Landschaft.
     *
     * @param landschaft Die Model.Landschaft in dem sich der Model.Panzer bewegt.
     */
    public Panzer(Landschaft landschaft, AktionenButtonController controller) {
        _controller = controller;
        _landschaft = landschaft;
    }

    /**
     * Dise Methode wird überschrieben vom neu compilierten
     */
    @Invisible
    public void main() {
       
    }

    /**
     * Funktion um den Model.Panzer eine Position weiter in der aktuellen Ausrichung zu bewegen.
     */
    public void vor() {
        _controller.Aktion_Vor();
    }

    /**
     * Dreht die Model.Ausrichtung des Panzers gegen Uhrzeigersinn um 90 Grad.
     */
    public void linksUm() {
        _controller.Aktion_Links_Um();
    }

    /**
     * Nimmt, wenn die aktuelle Model.Kachel Munition enthält, eine Einheit Munition von der Model.Kachel in den Model.Panzer auf.
     */
    public void nimm() {
        _controller.Aktion_Nimm();
    }

    /**
     * Verwendet eine Munition aus dem Model.Panzer um eine Wand auf dem Feld einen voraus in der aktuellen Model.Ausrichtung zu zerstören.
     * Verwendet auch dann eine Einheit Munition, wenn keine Wand voraus ist.
     */
    public void schiessen() {
        _controller.Aktion_Schissen();
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
