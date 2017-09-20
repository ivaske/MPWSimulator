package Model;

import Exceptions.KeineMunitionAufKachelException;
import Exceptions.KeineMunitionInPanzerException;
import Exceptions.VorneNichtFreiException;
import Utils.Observable;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * Landschaft der MPW (Panzer-Spiel)
 *
 * @author Yannick Vaske
 * @version 17.08.2017
 * @see Panzer
 * @see Kachel
 */
public class Landschaft extends Observable {
    private static final int STANDARD_ROWS = 10;
    private static final int STANDARD_COLS = 10;
    private static final int STANDARD_SEED = 1540434588;

    private int _spielfeldGroesseRows;
    private int _spielfeldGroesseCols;

    private int _seed;

    private Kachel[][] _spielFeld;

    private Panzer _panzer;
    private Ausrichtung _ausrichtung;
    private Kachel _positionPanzer;
    private int _anzahlMunitionInPanzer;

    //---------------------------------------------------
    //-------------Initialisierung-----------------------

    /**
     * Der Konstruktor der Landschaft. Dieser Initialisiert das Spielfeld auf die Standardgröße
     * ,den Model.Panzer und gibt in einem neuen Spielfeld einige Gegenstände vor.
     */
    public Landschaft() {
        this(STANDARD_ROWS, STANDARD_COLS);
    }

    /**
     * Der Konstruktor der Landschaft. Dieser Initialisiert das Spielfeld auf die übergebene Größe
     * ,den Model.Panzer und gibt in einem neuen Spielfeld einige Gegenstände vor.
     *
     * @param spielfeldGroesseRows Anzahl der Reihen des Spielfelds.
     * @param spielfeldGroesseCols Anzahl der Spalten des Spielfelds.
     */
    public Landschaft(int spielfeldGroesseRows, int spielfeldGroesseCols) {
        this._spielfeldGroesseRows = spielfeldGroesseRows;
        this._spielfeldGroesseCols = spielfeldGroesseCols;
        _seed = STANDARD_SEED;

        initialisiereSpielfeld();
        setzteVorgegebenesSpielfeld();
        initialisierePanzer();
    }

    /**
     * Aendert die Groeße des Spielfelds auf die uebergebenen Parameter.
     *
     * @param spielfeldGroesseRows Anzahl der Reihen des Spielfelds.
     * @param spielfeldGroesseCols Anzahl der Spalten des Spielfelds.
     */
    public void aendereSpielfeldGroesse(int spielfeldGroesseRows, int spielfeldGroesseCols) {
        _spielfeldGroesseRows = spielfeldGroesseRows;
        _spielfeldGroesseCols = spielfeldGroesseCols;
        initialisiereSpielfeld();
        setzteVorgegebenesSpielfeld();
        initialisierePanzer();
        notifyObserver();
    }

    /**
     * Das Spielfeld Array wird in der Standard Spielfeldgröße initialisiert. Alle Kacheln werden dabei auf leer gesetzt.
     */
    private void initialisiereSpielfeld() {
        _spielFeld = new Kachel[_spielfeldGroesseRows][_spielfeldGroesseCols];

        for (int i = 0; i < _spielfeldGroesseRows; i++) {
            for (int j = 0; j < _spielfeldGroesseCols; j++) {
                Kachel kachel = new Kachel(KachelTyp.leer);
                setzteKachel(i, j, kachel);
            }
        }
    }

    /**
     * Es wird ein Model.Panzer initialisiert und eine Referenz auf die Model.Landschaft wird übergeben.
     * Der Model.Panzer wird dabei auf das Feld 5|5 gesetzt mit der Model.Ausrichtung nach Osten.
     */
    private void initialisierePanzer() {
        _panzer = new Panzer(this);
        _positionPanzer = _spielFeld[0][0];
        aktualisiereKachelTyp(0, 0, KachelTyp.leer);

        aktualisiereKachelTyp(_spielfeldGroesseRows - 2, _spielfeldGroesseCols - 2, KachelTyp.Hamster);
        _ausrichtung = Ausrichtung.Ost;
    }

    /**
     * Methode um einen Panzer zu setzen. Wird benötigt, um einen neu kompilierten Panzer der landschaft zuzufügen.
     *
     * @param panzer Neuer Panzer der gesetzt werden soll.
     */
    public void setzePanzer(Panzer panzer) {
        _panzer = panzer;

        for (Method method : panzer.getClass().getDeclaredMethods()) {
            IO.println("Method: " + method.getName());
        }

    }

    /**
     * Setzt auf das Spielfeld einige Gegenstände.
     */
    private void setzteVorgegebenesSpielfeld() {

        int minimum = 0;
        int maximum = 6;

        for (int row = 0; row < _spielfeldGroesseRows; row++) {
            Random random = new Random(_seed + row);

            for (int col = 0; col < _spielfeldGroesseCols; col++) {

                int n = maximum - minimum + 1;
                int temp = random.nextInt() % n;
                int value = minimum + temp;

                switch (value) {
                    case 1:
                    case 3:
                        aktualisiereKachelTyp(row, col, KachelTyp.Wand);
                        break;
                    case 5:
                        Kachel kachel1 = aktualisiereKachelTyp(row, col, KachelTyp.Munition);
                        n = maximum - minimum + 1;
                        temp = random.nextInt() % n;
                        int anzahl = minimum + temp;
                        anzahl = Math.abs(anzahl);
                        anzahl = (int) (anzahl / 2);
                        if (anzahl <= 0) anzahl++;

                        kachel1.set_anzahlMunition(anzahl);
                        break;
                    case 2:
                    case 0:
                    case 4:
                        aktualisiereKachelTyp(row, col, KachelTyp.leer);
                        break;
                }

            }
        }
    }

    //--------------------------------------------------

    //--------------------------------------------------
    //-----------------Methoden des Panzers-------------

    /**
     * Funktion um den Model.Panzer eine Position weiter in der aktuellen Ausrichung zu bewegen.
     *
     * @throws VorneNichtFreiException Wenn eine Wand oder der Spielfeldrand vorraus ist.
     */
    public void vor() throws VorneNichtFreiException {

        if (vornFrei()) {
            int[] naechste = gebeNaechsteKoordinate();
            _positionPanzer = _spielFeld[naechste[0]][naechste[1]];
        } else {
            throw new VorneNichtFreiException("Der Weg ist in der aktuellen Model.Ausrichtung nicht frei.");
        }
        notifyObserver();
        printToConsole();
    }

    /**
     * Dreht die Model.Ausrichtung des Panzers gegen Uhrzeigersinn um 90 Grad.
     */
    public void linksUm() {
        switch (_ausrichtung) {
            case Nord:
                _ausrichtung = Ausrichtung.West;
                break;
            case Ost:
                _ausrichtung = Ausrichtung.Nord;
                break;
            case Sued:
                _ausrichtung = Ausrichtung.Ost;
                break;
            case West:
                _ausrichtung = Ausrichtung.Sued;
                break;
        }
        notifyObserver();
        printToConsole();
    }

    /**
     * Nimmt, wenn die aktuelle Model.Kachel Munition enthält, eine Einheit Munition von der Model.Kachel in den Model.Panzer auf.
     *
     * @throws KeineMunitionAufKachelException Wird geworfen, wenn keine Munition auf der Model.Kachel liegt.
     */
    public void nimm() throws KeineMunitionAufKachelException {
        if (!munitionDa()) {
            throw new KeineMunitionAufKachelException("Es liegt keine Munition auf der Model.Kachel.");
        }

        _anzahlMunitionInPanzer++;
        _positionPanzer.set_anzahlMunition(_positionPanzer.get_anzahlMunition() - 1);
        if (_positionPanzer.get_anzahlMunition() == 0) {
            _positionPanzer.set_Kacheltyp(KachelTyp.leer);
        }
        notifyObserver();
    }

    /**
     * Verwendet eine Munition aus dem Model.Panzer um eine Wand auf dem Feld einen voraus in der aktuellen Model.Ausrichtung zu zerstören.
     * Verwendet auch dann eine Einheit Munition, wenn keine Wand voraus ist.
     *
     * @throws KeineMunitionInPanzerException Wird geworfen, wenn keine Munition im Model.Panzer vorhanden ist.
     */
    public void schiessen() throws KeineMunitionInPanzerException {

        if (munitionLeer()) {
            throw new KeineMunitionInPanzerException("Der Panzer hat keine Munition zum schiessen.");
        }

        _anzahlMunitionInPanzer--; //Es wird geschossen, unabhängig davon, ob etwas voraus ist.

        int[] naechstePosition = gebeNaechsteKoordinate();

        Kachel kachel = _spielFeld[naechstePosition[0]][naechstePosition[1]];

        if (kachel.get_Kacheltyp() == KachelTyp.Wand) {
            aktualisiereKachelTyp(naechstePosition[0], naechstePosition[1], KachelTyp.leer);
        } else if (kachel.get_Kacheltyp() == KachelTyp.Hamster) {
            erschiesseHamster(naechstePosition[0], naechstePosition[1]);
        }
        notifyObserver();

    }

    /**
     * Fragt ab, ob das Feld einen voraus in der aktuellen Model.Ausrichtung für den Model.Panzer frei ist.
     *
     * @return Boolean, ob das Feld frei ist.
     */
    public boolean vornFrei() {
        boolean frei = false;
        int[] position = gebePostitionPanzer();
        int x = position[0];
        int y = position[1];

        int[] naechstePosition = gebeNaechsteKoordinate();
        frei = feldFuerPanzerFrei(naechstePosition[0], naechstePosition[1]);

        return frei;
    }

    /**
     * Fragt ab, ob die Munition im Model.Panzer leer ist.
     *
     * @return Boolean, ob die Munition im Model.Panzer leer ist.
     */
    public boolean munitionLeer() {
        return _anzahlMunitionInPanzer == 0;
    }

    /**
     * Fragt ab, ob auf der aktuellen Model.Kachel Munition vorhanden ist.
     *
     * @return Boolean, ob das Feld Munition beinhaltet
     */
    public boolean munitionDa() {
        int[] position = gebePostitionPanzer();
        int x = position[0];
        int y = position[1];

        return _spielFeld[x][y].get_Kacheltyp() == KachelTyp.Munition;
    }

    /**
     * Fragt ab, ob eine Wand auf dem nächsten Feld ist.
     *
     * @return Gibt true zurück, wenn eine Wand vorraus ist. Gibt jedoch false zurück, wenn der Spielfeldrand vorraus ist.
     */
    public boolean wandVoraus() {
        int[] naechstePosition = gebeNaechsteKoordinate();

        if (_spielFeld[naechstePosition[0]][naechstePosition[1]].get_Kacheltyp() == KachelTyp.Wand) {
            return true;
        } else {
            return false;
        }
    }

    //-------------------------------------------------

    //-------------------------------------------------
    //----------Hilfsmethoden--------------------------

    private void erschiesseHamster(int row, int col) {

        aktualisiereKachelTyp(row, col, KachelTyp.leerBlood);
        int nrow;
        int ncol;
        for (Ausrichtung ausrichtung : Ausrichtung.values()) {
            int[] naechste = gebeNaechsteKoordinate(ausrichtung, new int[]{row, col});
            nrow = naechste[0];
            ncol = naechste[1];

            if (nrow < _spielfeldGroesseRows && nrow >= 0 && ncol < _spielfeldGroesseCols && ncol >= 0) {
                if (gebeKachelTyp(nrow, ncol) == KachelTyp.leer || gebeKachelTyp(nrow, ncol) == KachelTyp.leerBlood || gebeKachelTyp(nrow, ncol) == KachelTyp.Munition) {
                    aktualisiereKachelTyp(nrow, ncol, KachelTyp.leerBlood);
                }
            }
        }


    }

    /**
     * Testfunktion zum Ausgeben des aktuellen Spielfelds in der Konsole.
     */
    public void printToConsole() {
        System.out.println();
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < _spielFeld.length; i++) {
            System.out.print(i + " ");
        }
        for (int col = 0; col < _spielFeld.length; col++) {
            System.out.println();
            System.out.print(col + " ");
            for (int row = 0; row < _spielFeld[col].length; row++) {
                String symbol = "";
                if (_spielFeld[row][col] == _positionPanzer) {
                    //symbol = "P ";
                    switch (_ausrichtung) {
                        case Ost:
                            symbol = "> ";
                            break;
                        case Sued:
                            symbol = "v ";
                            break;
                        case West:
                            symbol = "< ";
                            break;
                        case Nord:
                            symbol = "^ ";
                            break;

                    }
                } else {

                    switch (_spielFeld[row][col].get_Kacheltyp()) {
                        case Wand:
                            symbol = "W ";
                            break;
                        case leer:
                            symbol = "0 ";
                            break;
                        case Munition:
                            symbol = _spielFeld[row][col].get_anzahlMunition() + " ";
                            break;
                        case Hamster:
                            symbol = "H ";
                            break;
                        case leerBlood:
                            symbol = "B ";
                            break;
                    }
                }
                System.out.print(symbol);
            }
        }
    }

    /**
     * Durch diese Prozedur kann eine spezifische Model.Kachel gesetzt werden an einer bestimmten Position.
     *
     * @param row    Die Zeile in der die Model.Kachel gesetzt wird.
     * @param col    Die Spalte in der die Model.Kachel gesetzt wird.
     * @param kachel Die Model.Kachel, die gesetzt wird.
     */
    private void setzteKachel(int row, int col, Kachel kachel) {
        _spielFeld[row][col] = kachel;
    }

    /**
     * Aktualisiert eine vorhandene Model.Kachel zu einem übergebenen Typ und gibt die aktualisierte Model.Kachel zurück.
     *
     * @param row       Die Zeile in der die Model.Kachel gesetzt wird.
     * @param col       Die Spalte in der die Model.Kachel gesetzt wird.
     * @param kachelTyp Der neue Kacheltyp, der der Model.Kachel zugewiesen werden soll.
     * @return Gibt die aktualisierte Model.Kachel zurück.
     */
    private Kachel aktualisiereKachelTyp(int row, int col, KachelTyp kachelTyp) {
        _spielFeld[row][col].set_Kacheltyp(kachelTyp);
        return _spielFeld[row][col];
    }

    public KachelTyp gebeKachelTyp(int row, int col) {
        return _spielFeld[row][col].get_Kacheltyp();
    }

    /**
     * Gibt die Aktuelle Position des Panzers zurück.
     *
     * @return IntArray, wo Index: 0 = row; Index: 1 = col
     */
    private int[] gebePostitionPanzer() {
        for (int col = 0; col < _spielfeldGroesseCols; col++) {
            for (int row = 0; row < _spielfeldGroesseRows; row++) {
                if (_spielFeld[row][col] == _positionPanzer) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    /**
     * Gibt ein IntArray zurück mit der nächsten Koordinate in der aktuellen Ausrichtung zurück.
     *
     * @return IntArray: Index: 0 = row; Index: 1 = col
     */
    private int[] gebeNaechsteKoordinate() {

        return gebeNaechsteKoordinate(_ausrichtung, gebePostitionPanzer());
    }

    /**
     * Gibt ein IntArray zurück mit der nächsten Koordinate in der übergebenen Ausrichtung zurück.
     *
     * @return IntArray: Index: 0 = row; Index: 1 = col
     */
    private int[] gebeNaechsteKoordinate(Ausrichtung ausrichtung, int[] position) {
        int row = position[0];
        int col = position[1];

        switch (ausrichtung) {
            case Nord:
                return new int[]{row, col - 1};

            case Ost:
                return new int[]{row + 1, col};

            case Sued:
                return new int[]{row, col + 1};

            case West:
                return new int[]{row - 1, col};

        }
        return position;
    }

    /**
     * Interne Methode zum überprüfen, ob ein spezifisches Feld für den Panzer frei.
     *
     * @param row row der Kachel.
     * @param col col der Kachel.
     * @return Boolean, ob die Kachel für den Panzer frei ist.
     */
    private boolean feldFuerPanzerFrei(int row, int col) {
        if (row >= 0 && row < _spielfeldGroesseRows && col >= 0 && col < _spielfeldGroesseCols) {
            KachelTyp kachelTyp = gebeKachelTyp(row, col);
            if (kachelTyp == KachelTyp.leer || kachelTyp == KachelTyp.Munition || kachelTyp == KachelTyp.leerBlood) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt die Anazhl der Spalten zurück.
     *
     * @return Anzahl der Spalten.
     */
    public int get_spielfeldGroesseCols() {
        return _spielfeldGroesseCols;
    }

    /**
     * Gibt die Anzahl der Reihen zurück.
     *
     * @return Anzahl der Reihen.
     */
    public int get_spielfeldGroesseRows() {
        return _spielfeldGroesseRows;
    }

    /**
     * Gibt das Spielfeld zurück.
     *
     * @return zweidimensionales Kachelarray.
     */
    public Kachel[][] get_spielFeld() {
        return _spielFeld;
    }

    /**
     * Gibt die Position des Panzers als Kachel zurück.
     *
     * @return Kachel des Panzers.
     */
    public Kachel get_positionPanzer() {
        return _positionPanzer;
    }

    /**
     * Gibt die aktuelle Ausrichtung des Panzers zurück
     *
     * @return Ausrichtung des Panzers
     */
    public Ausrichtung get_ausrichtung() {
        return _ausrichtung;
    }

    public int get_seed() {
        return _seed;
    }

    public void set_seed(int _seed) {
        this._seed = _seed;
        System.out.println(_seed);
    }

    //-------------------------------------------------

    public void setztePanzerOnTile(int row, int col) {
        if (gebeKachelTyp(row, col) == KachelTyp.Wand) {
            _spielFeld[row][col].set_Kacheltyp(KachelTyp.leer);
        }
        _positionPanzer = _spielFeld[row][col];
        notifyObserver();
    }

    public void setzeMunitionOnTile(int row, int col) {
        if (gebeKachelTyp(row, col) != KachelTyp.Wand && _positionPanzer != _spielFeld[row][col]) {
            Kachel kachel = _spielFeld[row][col];
            kachel.set_Kacheltyp(KachelTyp.Munition);
            kachel.set_anzahlMunition(kachel.get_anzahlMunition() + 1);
            _spielFeld[row][col] = kachel;
            notifyObserver();
        }
    }

    public void setzeWallOnTile(int row, int col) {
        if (_positionPanzer != _spielFeld[row][col]) {
            _spielFeld[row][col].set_Kacheltyp(KachelTyp.Wand);
            notifyObserver();
        }

    }

    public void setzeHamsterOnTile(int row, int col) {
        if (_positionPanzer != _spielFeld[row][col]) {

            for (int nrow = 0; nrow < _spielfeldGroesseRows; nrow++) {
                for (int ncol = 0; ncol < _spielfeldGroesseCols; ncol++) {
                    if (gebeKachelTyp(nrow, ncol) == KachelTyp.Hamster) {
                        _spielFeld[nrow][ncol].set_Kacheltyp(KachelTyp.leer);
                    }
                }
            }
            _spielFeld[row][col].set_Kacheltyp(KachelTyp.Hamster);
            notifyObserver();
        }
    }

    public void deleteTile(int row, int col) {
        if (_positionPanzer != _spielFeld[row][col]) {
            _spielFeld[row][col].set_Kacheltyp(KachelTyp.leer);
            notifyObserver();
        }
    }

}
