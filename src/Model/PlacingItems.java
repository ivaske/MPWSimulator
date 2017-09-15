package Model;

import Utils.Observable;

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
