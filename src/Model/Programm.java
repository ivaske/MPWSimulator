package Model;

import View.Simulator;
import javafx.stage.Stage;

/**
 * @author Yannick Vaske
 * @version 18.09.2017
 * <p>
 * Diese Klasse stellt eine Instanz des Programms dar.
 */
public class Programm {

    private String _name;
    private static final String DEFAULT_PANZER = "defaultPanzer";

    /**
     * Der default Konstruktor startet den defaultPanzer
     */
    public Programm() {
        _name = DEFAULT_PANZER;

    }

    /**
     * Dieser Konstruktor erstellt ein Programm mit dem übergebenen Namen.
     *
     * @param name Der Name des neu erstellten Programms.
     */
    public Programm(String name) {
        _name = name;

    }

    public void buildScene() {
        Stage programmStage = new Stage();

        Simulator simulator = new Simulator();
        simulator.start(programmStage);

    }
}
