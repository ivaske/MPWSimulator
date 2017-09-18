package Model;

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
    private static final String DEFAULT_PANZER = "defaultPanzer";
    private final String FILE_PREFIX;
    private final String FILE_POSTFIX;
    private SimpleStringProperty _textFieldContent;

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
        this(name, "void main() {\n" +
                "\n" +
                "    }");
    }

    public Programm(String name, String savedData) {
        _name = name;

        FILE_PREFIX = "package Model;\n" +
                "\n" +
                "public class " + _name + " extends Model.Panzer {\n" +
                "    public Test(Model.Landschaft landschaft) {\n" +
                "        super(landschaft);\n" +
                "    }\n" +
                "    public ";

        FILE_POSTFIX = "}";
        _textFieldContent = new SimpleStringProperty(savedData);

    }

    public void buildScene() {
        Stage programmStage = new Stage();

        Simulator simulator = new Simulator();
        simulator.start(programmStage, this);

    }

    public String get_name() {
        return _name;
    }

    public String get_textFieldContent() {
        return _textFieldContent.getValue();
    }

    public void set_textFieldContent(String _textFieldContent) {
        this._textFieldContent.set(_textFieldContent);
        IO.println(_textFieldContent);
    }
}
