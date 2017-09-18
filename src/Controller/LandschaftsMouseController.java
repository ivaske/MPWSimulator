package Controller;

import Model.KachelTyp;
import Model.Landschaft;
import View.LandschaftPanel;
import Model.PlacingItems;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.*;

/**
 * @author Yannick Vaske
 * @version 15.09.2017
 * <p>
 * Diese Klasse dient als Controller für die Aktionen, die auf dem Landschaftspanel, bzw. dem Canvas ausgeführt werden.
 * @see LandschaftPanel
 */
public class LandschaftsMouseController {

    private Landschaft _landschaft;
    private PlacingItems _placingItems;
    private boolean _panzerDragging;

    /**
     * Der Konstruktor dieser Kontrollerklasse.
     *
     * @param landschaft   Es wird das Model benötigt um den aktuellen Status des Models zu erfragen und Änderungen im Model anzustoßen.
     * @param placingItems Für die Klickevents werden die PlacingItems benötigt, damit dieser Controller weiß, welcher Gegenstand im Model plaziert werden soll.
     */
    public LandschaftsMouseController(Landschaft landschaft, PlacingItems placingItems) {
        this._landschaft = landschaft;
        this._placingItems = placingItems;

        _panzerDragging = false;
    }

    /**
     * Diese Methode behandel Click-Events auf das Canvas.
     *
     * @param event Event das durch den MouseClick ausgelöst wird.
     */
    public void ClickedOnLandschaft(MouseEvent event) {
        System.out.println("Clicked On Landschaftspanel x:" + event.getX() + " y:" + event.getY());

        int col = (int) event.getY() / LandschaftPanel.BILDGROESSE_PIXEL;
        int row = (int) event.getX() / LandschaftPanel.BILDGROESSE_PIXEL;
        System.out.println("Clicked On Landschaftspanel row:" + row + " col:" + col);

        if (_landschaft.get_spielFeld()[row][col] == _landschaft.get_positionPanzer()) {
            _panzerDragging = true;
        }

        switch (_placingItems.get_isSelected()) {
            case Panzer:
                _landschaft.setztePanzerOnTile(row, col);
                break;
            case Munition:
                _landschaft.setzeMunitionOnTile(row, col);
                break;
            case Wall:
                _landschaft.setzeWallOnTile(row, col);
                break;
            case Hamster:
                _landschaft.setzeHamsterOnTile(row, col);
                break;
            case Delete:
                _landschaft.deleteTile(row, col);
                break;
        }
        event.consume();
    }

    /**
     * Diese Methode wird aufgerufen, sobald das Canvas einen Drag erkennt. Es wird geprüft, ob auf der aktuellen Kachel der Panzer steht. Falls ja wird der Drag erlaubt.
     *
     * @param event  Das Dragevent, welches diesen Aufruf auslöst.
     * @param canvas Das Canvas welches gedragged wird.
     */
    public void DragDetectedLandschaft(MouseEvent event, Canvas canvas) {

        int col = (int) event.getY() / LandschaftPanel.BILDGROESSE_PIXEL;
        int row = (int) event.getX() / LandschaftPanel.BILDGROESSE_PIXEL;

        System.out.println("Clicked On Landschaftspanel row:" + row + " col:" + col);

        if (_landschaft.get_spielFeld()[row][col] == _landschaft.get_positionPanzer()) {
            //Wenn beim Dragstart der Panzer ist, darf gedraged werden:
            System.out.println("Drag Detected");
            Dragboard db = canvas.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString("Drag");
            db.setContent(content);
            //Panzer wird je nach Ausrichtung im Dragview angezeigt.
            db.setDragView(new Image(getClass().getResource("/resources/Panzer32" + _landschaft.get_ausrichtung().toString() + ".png").toString()), 10, 10);
        }
        event.consume();
    }

    /**
     * Diese Methode wird beim draggen über das Canvas aufgerufen, um zum prüfen, ob der Dragvorgang an der aktuellen Position erfolgreich beendet werden kann.
     *
     * @param event Das Drag-Event, welches diesen Aufruf auslöst.
     */
    public void OnDragOver(DragEvent event) {

        int col = (int) event.getY() / LandschaftPanel.BILDGROESSE_PIXEL;
        int row = (int) event.getX() / LandschaftPanel.BILDGROESSE_PIXEL;

        if (_landschaft.gebeKachelTyp(row, col) == KachelTyp.Wand || _landschaft.gebeKachelTyp(row, col) == KachelTyp.Hamster) {
            return;
        } else {

            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }


    /**
     * Wenn der Drag beendet wird, wird diese Funktion aufgerufen, um zu überprüfen, ob der Panzer an dieser Stelle abgesetzt werden darf.
     *
     * @param event Das Drag-Event, welches diesen Aufruf auslöst.
     */
    public void OnDragDropped(DragEvent event) {
        System.out.println("DragDropped");

        int col = (int) event.getY() / LandschaftPanel.BILDGROESSE_PIXEL;
        int row = (int) event.getX() / LandschaftPanel.BILDGROESSE_PIXEL;

        if (_landschaft.gebeKachelTyp(row, col) != KachelTyp.Wand && _landschaft.gebeKachelTyp(row, col) != KachelTyp.Hamster) {
            _landschaft.setztePanzerOnTile(row, col);
        }

        event.consume();
    }
}
