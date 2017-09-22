package Controller;

import Exceptions.KeineMunitionAufKachelException;
import Exceptions.KeineMunitionInPanzerException;
import Exceptions.VorneNichtFreiException;
import Model.Landschaft;
import View.Simulator;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Random;

/**
 * Die Controller-Klasse die die Aktionen auf der Landschaft behandeln
 *
 * @author Yannick Vaske
 * @version 17.08.2017
 */
public class AktionenButtonController {
    private Landschaft _landschaft;
    private Simulator _simulator;

    public AktionenButtonController(Landschaft landschaft, Simulator simulator) {
        this._landschaft = landschaft;
        this._simulator = simulator;

    }

    public void Aktion_Links_Um() {
        _landschaft.linksUm();
    }

    public void Aktion_Vor() {
        try {
            _landschaft.vor();
        } catch (VorneNichtFreiException ex) {
            SoundController.getInstance().ErrorSoundAbspielen();
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void Aktion_Nimm() {
        try {
            _landschaft.nimm();
        } catch (KeineMunitionAufKachelException ex) {
            SoundController.getInstance().ErrorSoundAbspielen();
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void Aktion_Schissen() {
        try {
            _landschaft.schiessen();
        } catch (KeineMunitionInPanzerException ex) {
            SoundController.getInstance().ErrorSoundAbspielen();
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void Abfrage_Muniton_Da() {
        boolean munitionDa = _landschaft.munitionDa();
        if (munitionDa) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Es ist Munition da.", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Es ist keine Munition da.", ButtonType.OK);
            alert.show();
        }
    }

    public void Abfrage_Munition_leer() {
        boolean munitionLeer = _landschaft.munitionLeer();
        if (munitionLeer) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Der Panzer hat keine Munition.", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Der Panzer ist geladen.", ButtonType.OK);
            alert.show();
        }
    }

    public void Abfrage_Vorn_Frei() {
        boolean vornFrei = _landschaft.vornFrei();
        if (vornFrei) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vorne ist frei.", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vorne ist nicht frei.", ButtonType.OK);
            alert.show();
        }
    }

    public void Abfrage_Wand_Voraus() {
        boolean wandvoraus = _landschaft.wandVoraus();
        if (wandvoraus) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vorne ist frei.", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vorne ist eine Wand.", ButtonType.OK);
            alert.show();
        }
    }

    public void Button_RandomMap_Click() {
        Random rn = new Random();
        _landschaft.set_seed(Math.abs(rn.nextInt()));
        _landschaft.aendereSpielfeldGroesse(_landschaft.get_spielfeldGroesseRows(), _landschaft.get_spielfeldGroesseCols());
    }
}
