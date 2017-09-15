package Model;

import Utils.Observer;
import javafx.scene.control.RadioMenuItem;

public class ObservedRadioMenuItem extends RadioMenuItem implements Observer {
    private PlacingItems placingItems;
    private PlaceButtons buttons;

    public ObservedRadioMenuItem(PlacingItems placingItems, PlaceButtons typ, String text) {
        super(text);
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
