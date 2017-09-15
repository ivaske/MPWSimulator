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

    public void Button_LinksUm_Click() {
        _landschaft.linksUm();
    }

    public void Button_Vor_Click() {
        try {
            _landschaft.vor();
        } catch (VorneNichtFreiException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void Button_Nimm_Click() {
        try {
            _landschaft.nimm();
        } catch (KeineMunitionAufKachelException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void Button_Schiessen_Click() {
        try {
            _landschaft.schiessen();
        } catch (KeineMunitionInPanzerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    public void Button_MunitionDa_Click() {
        boolean munitionDa = _landschaft.munitionDa();
        if (munitionDa) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Es ist Munition da.", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Es ist keine Munition da.", ButtonType.OK);
            alert.show();
        }
    }

    public void Button_Munitionleer_Click() {
        boolean munitionLeer = _landschaft.munitionLeer();
        if (munitionLeer) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Der Panzer hat keine Munition.", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Der Panzer ist geladen.", ButtonType.OK);
            alert.show();
        }
    }

    public void Button_VornFrei_Click() {
        boolean vornFrei = _landschaft.vornFrei();
        if (vornFrei) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vorne ist frei.", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vorne ist nicht frei.", ButtonType.OK);
            alert.show();
        }
    }

    public void Button_WandVoraus_Click() {
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
