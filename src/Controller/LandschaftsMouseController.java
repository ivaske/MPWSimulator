package Controller;

import Model.Landschaft;
import Model.LandschaftPanel;
import Model.PlacingItems;

public class LandschaftsMouseController {

    private Landschaft _landschaft;
    private PlacingItems _placingItems;
    private boolean _panzerDragging;

    public LandschaftsMouseController(Landschaft landschaft, PlacingItems placingItems) {
        this._landschaft = landschaft;
        this._placingItems = placingItems;

        _panzerDragging = false;
    }

    public void ClickedOnLandschaft(double x, double y) {
        System.out.println("Clicked On Landschaftspanel x:" + x + " y:" + y);

        int col = (int) y / LandschaftPanel.BILDGROESSE_PIXEL;
        int row = (int) x / LandschaftPanel.BILDGROESSE_PIXEL;
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

    }

    public void MouseMoveLandschaft(double x, double y) {
        System.out.println("Clicked On Landschaftspanel x:" + x + " y:" + y);

        if (_panzerDragging) {
            int col = (int) y / LandschaftPanel.BILDGROESSE_PIXEL;
            int row = (int) x / LandschaftPanel.BILDGROESSE_PIXEL;

            _landschaft.setztePanzerOnTile(row, col);
        }

    }

    public void ClickReleasedLandschaft(double x, double y) {
        System.out.println("Clicked On Landschaftspanel x:" + x + " y:" + y);

        if (_panzerDragging) {
            int col = (int) y / LandschaftPanel.BILDGROESSE_PIXEL;
            int row = (int) x / LandschaftPanel.BILDGROESSE_PIXEL;

            _landschaft.setztePanzerOnTile(row, col);
        }

        _panzerDragging = false;


    }
}
