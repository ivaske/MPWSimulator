package Controller;

import Model.IO;
import Model.Panzer;
import Model.Programm;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.tools.DiagnosticCollector;
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
            Programm programm = openFile(file);
            if (programm != null) {
                boolean erfolg = CompileController.trycompileSilent(programm);
                if (!erfolg) {
                    programm.get_landschaft().setzePanzer(new Panzer(programm.get_landschaft(),
                            new AktionenButtonController(programm.get_landschaft(), programm.get_simulator())));
                }

            }
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

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        Label label = new Label("Bitte geben Sie einen Namen für das Programm ein.");
        TextField textField = new TextField();
        textField.addEventHandler(KeyEvent.KEY_RELEASED, event -> checkSyntax(event, okButton));

        pane.add(label, 0, 0);
        pane.add(textField, 0, 1);

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

    private static void checkSyntax(KeyEvent event, Button buttonToDisable) {

        TextField textField = (TextField) event.getSource();
        String input = textField.getText();

        boolean disable = false;
        if (input.length() > 0) {
            for (int i = 0; i < input.toCharArray().length; i++) {
                if (i == 0) {
                    if (!Character.isJavaIdentifierStart(input.toCharArray()[i])) {
                        disable = true;
                        break;
                    }
                } else {
                    if (!Character.isJavaIdentifierPart(input.toCharArray()[i])) {
                        disable = true;
                        break;
                    }
                }
            }
        } else {
            disable = true;
        }

        //Überprüfen, ob ein Programm mit dem namen bereits geöffnet ist
        if (!disable) {
            disable = checkProgrammOpen(input);
        }

        buttonToDisable.setDisable(disable);
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

    public static Programm openFile(File file) {

        if (file == null || !file.exists()) {
            return null;
        }

        String programmName = file.getName().substring(0, file.getName().length() - DATEIENDUNG.length());
        if (checkProgrammOpen(programmName)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Dieses Programm ist bereits geöffnet.");
            alert.showAndWait();
            return null;
        }

        //try-closeable
        try (FileInputStream stream = new FileInputStream(file)) {

            byte[] buffer = new byte[(int) file.length()];
            stream.read(buffer);

            Programm programm = new Programm(programmName, new String(buffer));

            _listProgramme.add(programm);
            programm.buildScene();

            return programm;
        } catch (FileNotFoundException ex) {
            IO.println(ex.getMessage());
        } catch (IOException ex) {
            IO.println(ex.getMessage());
        }
        return null;
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
    public static File speichereProgramm(Programm programm) {
        File programmFile = new File(PFAD_DATEIEN + programm.get_name() + DATEIENDUNG);
        //try closeable
        try (FileOutputStream stream = new FileOutputStream(programmFile)) {
            String inhalt = programm.generiereZuSpeicherneDatei();

            stream.write(inhalt.getBytes());

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return programmFile;
    }
}
