package View;

import Controller.LandschaftsMouseController;
import Model.*;
import Utils.Observer;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.binding.When;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yannick Vaske
 * @version 15.09.2017
 * <p>
 * Diese Klasse erbt von einere Region und wird im Content-Panel auf angezeigt. Sie gibt das Model visuell wieder.
 */
public class LandschaftPanel extends Region implements Observer {
    private Landschaft _landschaft;
    private PlacingItems _placingItems;
    private LandschaftsMouseController _mouseController;
    private ScrollPane _parent;
    private Canvas _canvas;
    private ContextMenu _contextMenu;

    public static final int BILDGROESSE_PIXEL = 32;
    public static final int ANZAHL_MUNITION_X_OFFSET = -8;
    public static final int ANZAHL_MUNITION_Y_OFFSET = 12;
    public static final String METHOD_POST_FIX = "();";


    /**
     * Konstruktur der LandschaftsPanel Klasse. Es wird das Model, das übergeordnete Pane, in welchem diese Region angezeigt werden soll und die Placing Items benötigt.
     *
     * @param landschaft   Das Model
     * @param placingItems Der aktuelle Zustand der ausgewählten Buttons
     * @param parent       Das übergeordnete Pane.
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

        //_canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> _mouseController.ClickedOnLandschaft(event));
        _canvas.setOnMouseClicked(event -> _mouseController.ClickedOnLandschaft(event));
        _canvas.setOnDragDetected(event -> _mouseController.DragDetectedLandschaft(event, _canvas));
        _canvas.setOnDragOver(event -> _mouseController.OnDragOver(event));
        _canvas.setOnDragDropped(event -> _mouseController.OnDragDropped(event));
        _canvas.setOnContextMenuRequested(event -> aktualisiereContextMenu(_landschaft.get_panzer(), event));

        _canvas.setWidth(_landschaft.get_spielfeldGroesseRows() * BILDGROESSE_PIXEL);
        _canvas.setHeight(_landschaft.get_spielfeldGroesseCols() * BILDGROESSE_PIXEL);
        _parent.setPrefSize(this.getWidth() + 64, this.getHeight() + 64);
        _canvas.layoutXProperty()
                .bind(new
                        When(_parent.widthProperty().subtract(_canvas.widthProperty()).divide(2).greaterThan(0))
                        .then(_parent.widthProperty().subtract(_canvas.widthProperty()).divide(2)).otherwise(0));
        _canvas.layoutYProperty()
                .bind(new
                        When(_parent.heightProperty().subtract(_canvas.heightProperty()).divide(2).greaterThan(0))
                        .then(_parent.heightProperty().subtract(_canvas.heightProperty()).divide(2)).otherwise(0));


        GraphicsContext graphicsContext = _canvas.getGraphicsContext2D();

        Image image = null;
        Image imageWall = new Image(getClass().getResource("/resources/Wall32.png").toString());
        Image imageBlood = new Image(getClass().getResource("/resources/Tile32blood.png").toString());
        Image imageAmmo = new Image(getClass().getResource("/resources/ammo1-32.png").toString());

        //Landschaft zeichnen:
        graphicsContext.setFill(Color.LIGHTBLUE);


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
        this.getChildren().add(new ImageView(new Image(getClass().getResource("/resources/tankWallpaper.jpg").toString())));

        getChildren().add(_canvas);
    }

    /**
     * Öffnet das ContextMenu
     *
     * @param panzer Der Panzer der zur Erstellung des Context-Menu verwendet werden soll.
     */
    public void aktualisiereContextMenu(Panzer panzer, ContextMenuEvent event) {

        if (_contextMenu != null) {
            _contextMenu.hide();
        }

        _contextMenu = new ContextMenu();

        List<MenuItem> menuItemList = new ArrayList<>();

        //Zuerst die default Methoden vom original Panzer implementieren

        for (Method method : Panzer.class.getDeclaredMethods()) {
            MenuItem item = createMenuItem(method, panzer);
            if (item != null) {
                menuItemList.add(item);
            }
        }

        //Falls es nicht der originale Hamster ist, der übergeben wird, werden die neuen Methoden auch hinzugefügt
        if (panzer.getClass() != Panzer.class) {
            for (Method method : panzer.getClass().getDeclaredMethods()) {
                MenuItem item = createMenuItem(method, panzer);
                if (item != null) {
                    menuItemList.add(item);
                }
            }
        }

        _contextMenu.getItems().addAll(menuItemList);

        _contextMenu.show(_canvas, event.getScreenX(), event.getScreenY());
    }

    private MenuItem createMenuItem(Method method, Panzer panzer) {
        //Nur Public und normale Modifier anzeigen
        if (method.getModifiers() == Modifier.PUBLIC || method.getModifiers() == 0) {
            MenuItem menuItem = new MenuItem(method.getName() + METHOD_POST_FIX);
            menuItem.setOnAction(event -> {
                try {
                    method.invoke(panzer);
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            });
            if (method.getParameterCount() > 0) {
                menuItem.setDisable(true);
            }
            if (!method.getReturnType().toString().equals("void")) {
                menuItem.setDisable(true);
            }

            if (method.isAnnotationPresent(Invisible.class)) {
                return null;
            }

            return menuItem;
        } else {
            return null;
        }
    }


    public double getLandschaftHeight() {
        return getPrefHeight();
    }

    public double getLandschaftWidth() {
        return getPrefWidth();
    }

    @Override
    public void update() {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(this::zeichneSpielfeld);
        } else {
            zeichneSpielfeld();
        }
    }
}
