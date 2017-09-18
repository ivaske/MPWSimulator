package Controller;

import Model.Programm;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Yannick Vaske
 * @version 18.09.2017
 * <p>
 * Diese Klasse sorgt für dafür, dass mehrer Instanzen des Programms gleichzeitig ausgeführt werden können.
 */
public class ProgrammController extends Application {

    private static ArrayList<Programm> _listProgramme;

    /**
     * Haupteinstiegspunkt des Programms. Startet eine Instanz des Programms; den defaultHamster
     *
     * @param args werden nicht behandelt.
     */
    public static void main(String[] args) {

        //Initialisiere Programmliste
        _listProgramme = new ArrayList<>();

        //Starte Application
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Initialisiere DefaultHamster
        Programm defaultProgramm = new Programm();
        defaultProgramm.buildScene();

        _listProgramme.add(defaultProgramm);

    }

    /**
     * Öffnet einen Dialog um einen neuen Namen abzufragen und diesen dann für ein neues Programm zu verwenden.
     */
    public static void neuesProgramm() {
        Dialog dialog = new Dialog();
        GridPane pane = new GridPane();

        Label label = new Label("Bitte geben Sie einen Namen für das Programm ein.");
        TextField textField = new TextField();

        pane.add(label,0,0);
        pane.add(textField,0,1);

        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
        ButtonType buttonTypeAbbort = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().add(buttonTypeOK);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeAbbort);

        dialog.getDialogPane().setContent(pane);

        dialog.showAndWait();
    }

    /**
     * Öffnet einen Filebrowser um ein vorhandenes Programm zu laden.
     */
    public void oeffneProgramm() {

    }

    /**
     * Schließt die Instanz eines Programmes.
     */
    public void schliesseProgramm() {

    }

    /**
     * Speichert das aktuelle Programm.
     */
    public void speichereProgramm() {

    }
}
