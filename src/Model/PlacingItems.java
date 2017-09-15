package Model;

import Utils.Observable;

/**
 * @author Yannick Vaske
 * @version 15.09.2017
 *
 * Die Placing Items dienen als Observable und geben den aktuellen Status der Auswahl im Menu wieder.
 */
public class PlacingItems extends Observable {

private PlaceButtons _isSelected;

    public PlacingItems() {
        this._isSelected = PlaceButtons.Delete;
    }

    public void setisSelected(PlaceButtons isSelected) {
        this._isSelected = isSelected;
        notifyObserver();
    }

    public PlaceButtons get_isSelected() {
        return _isSelected;
    }
}
