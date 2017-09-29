package Controller;

import Model.Landschaft;
import Model.Programm;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.*;

public class SerializeController {

    public static synchronized void serialize(Landschaft landschaft) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(ProgrammController.PFAD_DATEIEN));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Landschafts-Dateien","*.ter"));



        File file = chooser.showSaveDialog(null);

        if (file != null) {

            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

                objectOutputStream.writeObject(landschaft);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public static synchronized void deserialize(Landschaft landschaft) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(ProgrammController.PFAD_DATEIEN));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Landschafts-Dateien","*.ter"));

        File file = chooser.showOpenDialog(null);

        if (file != null) {
            Landschaft neueLandschaft = null;

            try (FileInputStream fileInputStream = new FileInputStream(file);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                neueLandschaft   = (Landschaft) objectInputStream.readObject();

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Die angegebene Datei konnte nicht geladen werden.");
                alert.showAndWait();
            }

            landschaft.aktualisiereLandschaftAusDeserialisierung(neueLandschaft);
        }
    }
}
