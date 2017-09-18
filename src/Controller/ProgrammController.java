package Controller;

import Model.IO;
import Model.Programm;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Yannick Vaske
 * @version 18.09.2017
 * <p>
 * Diese Klasse sorgt für dafür, dass mehrer Instanzen des Programms gleichzeitig ausgeführt werden können.
 */
public class ProgrammController extends Application {

    private final static String PFAD_DATEIEN = System.getProperty("user.dir") + "\\saves\\";

    private static ArrayList<Programm> _listProgramme;

    /**
     * Haupteinstiegspunkt des Programms. Startet eine Instanz des Programms; den defaultHamster
     */
    public static void main() {
        //Starte Application
        launch();

    }

    public ProgrammController() throws IOException {
        //Initialisiere Programmliste
        _listProgramme = new ArrayList<>();

        System.out.println(PFAD_DATEIEN);
        Path path = Paths.get(PFAD_DATEIEN);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

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

        pane.add(label, 0, 0);
        pane.add(textField, 0, 1);

        ButtonType buttonTypeOK = ButtonType.OK;
        ButtonType buttonTypeAbbort = ButtonType.CANCEL;

        dialog.getDialogPane().getButtonTypes().add(buttonTypeOK);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeAbbort);

        dialog.getDialogPane().setContent(pane);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.setTitle("Neu");
        stage.getIcons().add(new Image("/resources/Panzer24.png"));

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Programm programm = new Programm(textField.getText());
            programm.buildScene();
        }

    }

    /**
     * Öffnet einen Filebrowser um ein vorhandenes Programm zu laden.
     */
    public static void oeffneProgramm(Stage callingStage) {
        try {


            FileChooser chooser = new FileChooser();
            chooser.setTitle("Eine Datei öffnen");
            chooser.setInitialDirectory(new File(PFAD_DATEIEN));
            File file = chooser.showOpenDialog(callingStage);

            if (file == null || !file.exists()) {
                return;
            }

            FileInputStream stream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];

            stream.read(buffer);

            String name = file.getName().substring(0,file.getName().lastIndexOf("."));

            Programm programm = new Programm(name, new String(buffer));
            _listProgramme.add(programm);
            programm.buildScene();

        } catch (FileNotFoundException ex) {
            IO.println(ex.getMessage());

        } catch (IOException ex) {
            IO.println(ex.getMessage());

        }
    }

    /**
     * Schließt die Instanz eines Programmes.
     */
    public void schliesseProgramm() {

    }

    /**
     * Speichert das aktuelle Programm.
     */
    public static void speichereProgramm(Programm programm) {

    }
}
