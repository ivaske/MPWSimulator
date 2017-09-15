package Model;

import Utils.Observer;
import javafx.scene.control.ToggleButton;

public class ObservedToggleButton extends ToggleButton implements Observer {
    private PlacingItems placingItems;
    private PlaceButtons buttons;

    public ObservedToggleButton(PlacingItems placingItems, PlaceButtons typ) {
        this.placingItems = placingItems;
        this.buttons = typ;
        placingItems.addObserver(this);

    }

    @Override
    public void update() {
        super.setSelected(buttons == placingItems.get_isSelected());
    }

    public PlaceButtons getButtons() {
        return buttons;
    }
}
