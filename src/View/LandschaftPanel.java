package View;

import Controller.LandschaftsMouseController;
import Model.Kachel;
import Model.KachelTyp;
import Model.Landschaft;
import Model.PlacingItems;
import Utils.Observer;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

/**
 * @author Yannick Vaske
 * @version 15.09.2017
 *
 * Diese Klasse erbt von einere Region und wird im Content-Panel auf angezeigt. Sie gibt das Model visuell wieder.
 *
 */
public class LandschaftPanel extends Region implements Observer {
    private Landschaft _landschaft;
    private PlacingItems _placingItems;
    private LandschaftsMouseController _mouseController;
    private ScrollPane _parent;
    private Canvas _canvas;

    public static final int BILDGROESSE_PIXEL = 32;
    public static final int ANZAHL_MUNITION_X_OFFSET = -8;
    public static final int ANZAHL_MUNITION_Y_OFFSET = 12;


    /**
     * Konstruktur der LandschaftsPanel Klasse. Es wird das Model, das übergeordnete Pane, in welchem diese Region angezeigt werden soll und die Placing Items benötigt.
     * @param landschaft Das Model
     * @param placingItems Der aktuelle Zustand der ausgewählten Buttons
     * @param parent Das übergeordnete Pane.
     */
    public LandschaftPanel(Landschaft landschaft, PlacingItems placingItems, ScrollPane parent) {
        this._landschaft = landschaft;
        this._placingItems = placingItems;
        this._parent = parent;
        this._mouseController = new LandschaftsMouseController(_landschaft, _placingItems);


        this._landschaft.addObserver(this);
    }

    /**
     * Diese Methode zeichnet das Model auf eine Canvas und fügt das Canvas als Node der aktuellen Instanz dieser Klasse zu.
     */
    public void zeichneSpielfeld() {
        _canvas = new Canvas();

        _canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> _mouseController.ClickedOnLandschaft(event.getX(), event.getY()));
        _canvas.setOnDragDetected(event -> _mouseController.DragDetectedLandschaft(event, _canvas));
        _canvas.setOnDragOver(event -> _mouseController.OnDragOver(event));
        _canvas.setOnDragDropped(event -> _mouseController.OnDragDropped(event));


        _canvas.setWidth(_landschaft.get_spielfeldGroesseRows() * BILDGROESSE_PIXEL);
        _canvas.setHeight(_landschaft.get_spielfeldGroesseCols() * BILDGROESSE_PIXEL);
        _parent.setPrefSize(this.getWidth() + 64, this.getHeight() + 64);


        GraphicsContext graphicsContext = _canvas.getGraphicsContext2D();

        Image image = null;
        Image imageWall = new Image(getClass().getResource("/resources/Wall32.png").toString());
        Image imageBlood = new Image(getClass().getResource("/resources/Tile32blood.png").toString());
        Image imageAmmo = new Image(getClass().getResource("/resources/ammo1-32.png").toString());

        //Landschaft zeichnen:
        graphicsContext.setFill(Color.LIGHTGREEN);


        for (int col = 0; col < _landschaft.get_spielfeldGroesseCols(); col++) {
            for (int row = 0; row < _landschaft.get_spielfeldGroesseRows(); row++) {
                //leere Kacheln zeichnen:
                graphicsContext.fillRect(row * BILDGROESSE_PIXEL, col * BILDGROESSE_PIXEL, BILDGROESSE_PIXEL, BILDGROESSE_PIXEL);
                graphicsContext.strokeRect(row * BILDGROESSE_PIXEL, col * BILDGROESSE_PIXEL, BILDGROESSE_PIXEL, BILDGROESSE_PIXEL);

                //Falls BLut auf der Kachel ist, wird sie hier gezeichnet, damit in der nächsten Schleife die Gegenstände darüber sein können
                if (_landschaft.gebeKachelTyp(row, col) == KachelTyp.leerBlood) {
                    image = imageBlood;
                    graphicsContext.drawImage(image, row * BILDGROESSE_PIXEL, col * BILDGROESSE_PIXEL);
                }
            }
        }

        //Gegenstaende hinzufuegen
        graphicsContext.setFill(Color.BLACK);
        for (int col = 0; col < _landschaft.get_spielfeldGroesseCols(); col++) {
            for (int row = 0; row < _landschaft.get_spielfeldGroesseRows(); row++) {
                Kachel kachel = _landschaft.get_spielFeld()[row][col];
                image = null;
                //Panzer je nach Ausrichtung laden und dann anzeigen:
                if (kachel == _landschaft.get_positionPanzer()) {
                    //Je nach Ausrichtung richtiges Bild laden. Müssen vorher nicht alle Ausrichtungen geladen werden, da man dadurch mehr I/O Zugriffe hätte
                    image = new Image(getClass().getResource("/resources/Panzer32" + _landschaft.get_ausrichtung().toString() + ".png").toString());

                } else {
                    //Andere Gegenstaende zeichnen:
                    switch (kachel.get_Kacheltyp()) {
                        case Wand:
                            image = imageWall;
                            break;
                        case Munition:
                            image = imageAmmo;
                            break;
                        case Hamster:
                            //todo: Hamster ausrichtung implementieren
                            image = new Image(getClass().getResource("/resources/2Hamster32.png").toString());
                            break;
                        case leerBlood:
                        case leer:
                            continue;
                    }
                }

                //Das Bild auf der aktuellen Kachel zeichnen.
                graphicsContext.drawImage(image, row * BILDGROESSE_PIXEL, col * BILDGROESSE_PIXEL);
                graphicsContext.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));

                //Anzahl an Munition auf das Spielfeld zeichnen
                if (kachel.get_Kacheltyp() == KachelTyp.Munition && kachel.get_anzahlMunition() > 0) {
                    String anzahl = Integer.toString(kachel.get_anzahlMunition());
                    graphicsContext.fillText(anzahl, (row + 1) * BILDGROESSE_PIXEL + ANZAHL_MUNITION_X_OFFSET, col * BILDGROESSE_PIXEL + ANZAHL_MUNITION_Y_OFFSET);
                }

            }
        }

        //Das Canvas in die Region zeichnen
        getChildren().clear();
        getChildren().add(_canvas);
    }


    public double getLandschaftHeight() {
        return getPrefHeight();
    }

    public double getLandschaftWidth() {
        return getPrefWidth();
    }

    @Override
    public void update() {
        zeichneSpielfeld();
    }
}
