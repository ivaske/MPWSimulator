package Controller;

import Model.IO;
import Model.Programm;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.nio.file.*;
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

    public final static String PFAD_DATEIEN = System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + "saves" + FileSystems.getDefault().getSeparator();
    private final static String DATEIENDUNG = ".java";


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
        Path path = Paths.get(PFAD_DATEIEN + Programm.DEFAULT_PANZER + DATEIENDUNG);
        File file = new File(path.toString());

        if (file.exists()) {
            openFile(file);
        } else {
            //Initialisiere DefaultHamster
            Programm defaultProgramm = new Programm();
            defaultProgramm.buildScene();

            _listProgramme.add(defaultProgramm);
        }
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

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

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
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Eine Datei öffnen");
        chooser.setInitialDirectory(new File(PFAD_DATEIEN));
        File file = chooser.showOpenDialog(callingStage);

        openFile(file);

    }

    public static void openFile(File file) {

        if (file == null || !file.exists()) {
            return;
        }

        String programmName = file.getName().substring(0, file.getName().length() - DATEIENDUNG.length());
        if (checkProgrammOpen(programmName)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Dieses Programm ist bereits geöffnet.");
            alert.showAndWait();
            return;
        }

        //try-closeable
        try (FileInputStream stream = new FileInputStream(file)) {

            byte[] buffer = new byte[(int) file.length()];
            stream.read(buffer);

            Programm programm = new Programm(programmName, new String(buffer));

            _listProgramme.add(programm);
            programm.buildScene();

        } catch (FileNotFoundException ex) {
            IO.println(ex.getMessage());
        } catch (IOException ex) {
            IO.println(ex.getMessage());
        }
    }

    private static boolean checkProgrammOpen(String programmName) {
        Programm programm = new Programm(programmName);
        return _listProgramme.contains(programm); //Funktioniert, da die equals-Methode entprechend überschrieben wurde.
    }

    /**
     * Schließt die Instanz eines Programmes.
     */

    public static void schliesseProgramm(Programm programm) {

        programm.get_stage().close();
        _listProgramme.remove(programm);

        if (_listProgramme.size() == 0) {
            Platform.exit();
        }
    }

    /**
     * Speichert das aktuelle Programm.
     */
    public static void speichereProgramm(Programm programm) {
        //try closeable
        try (FileOutputStream stream = new FileOutputStream(new File(PFAD_DATEIEN + programm.get_name() + DATEIENDUNG))) {
            String inhalt = programm.generiereZuSpeicherneDatei();

            stream.write(inhalt.getBytes());

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
