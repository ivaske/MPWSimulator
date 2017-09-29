package Model;

import Controller.AktionenButtonController;
import Simulation.SimulationManager;
import View.LandschaftPanel;
import View.Simulator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;


/**
 * @author Yannick Vaske
 * @version 18.09.2017
 * <p>
 * Diese Klasse stellt eine Instanz des Programms dar.
 */
public class Programm {

    private String _name;
    public static final String DEFAULT_PANZER = "defaultPanzer";
    private final String FILE_PREFIX;
    private final String FILE_POSTFIX;
    private final String PRE_DATA = "void main() {\n" + "    }";
    private SimpleStringProperty _textFieldContent;
    private Stage _stage;
    private AktionenButtonController _controller;

    // Anderes
    private Landschaft _landschaft;
    private LandschaftPanel _landschaftPanel;
    private Simulator _simulator;
    private SimulationManager _manager;


    /**
     * Der default Konstruktor startet den defaultPanzer
     */
    public Programm() {
        this(DEFAULT_PANZER);
    }

    /**
     * Dieser Konstruktor erstellt ein Programm mit dem übergebenen Namen.
     *
     * @param name Der Name des neu erstellten Programms.
     */
    public Programm(String name) {
        this(name, "");
    }

    public Programm(String name, String savedData) {
        _name = name;
        _landschaft = new Landschaft();
        _manager = new SimulationManager(_landschaft);

        //Initialisieren von PRE und POST Fix Werten
        FILE_PREFIX = "public class " + _name + " extends Model.Panzer {\n" +
                "    public " + _name + "(Model.Landschaft landschaft, Controller.AktionenButtonController controller) {\n" +
                "        super(landschaft,controller);\n" +
                "    }\n" +
                "    public ";

        FILE_POSTFIX = "}";

        //parse savedData: Wenn keine savedData vorhanden, dann wird ein Standardwert geschrieben.
        if (savedData != "") {
            savedData = savedData.substring(FILE_PREFIX.length(), savedData.length() - FILE_POSTFIX.length());
        } else {
            savedData = PRE_DATA;
        }

        _textFieldContent = new SimpleStringProperty(savedData);
    }

    public void set_controller(AktionenButtonController controller) {
        this._controller = controller;
    }

    public String generiereZuSpeicherneDatei() {
        return FILE_PREFIX + _textFieldContent.getValue() + FILE_POSTFIX;
    }

    public Simulator buildScene() {
        _stage = new Stage();

        _simulator = new Simulator();
        _simulator.start(_stage, this);
        return _simulator;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Programm that = (Programm) obj;

        if (this.get_name().equals(that.get_name())) {
            return true;
        } else {
            return false;
        }
    }

    public Landschaft get_landschaft() {
        return _landschaft;
    }

    public String get_name() {
        return _name;
    }

    public String get_nameForClassloader() {
        return "Model." + _name;
    }

    public String get_textFieldContent() {
        return _textFieldContent.getValue();
    }

    public void set_textFieldContent(String _textFieldContent) {
        this._textFieldContent.set(_textFieldContent);
    }

    public Stage get_stage() {
        return _stage;
    }

    public LandschaftPanel get_landschaftPanel() {
        return _landschaftPanel;
    }

    public void set_landschaftPanel(LandschaftPanel _landschaftPanel) {
        this._landschaftPanel = _landschaftPanel;
    }

    public Simulator get_simulator() {
        return _simulator;
    }

    public SimulationManager get_manager() {
        return _manager;
    }
}
