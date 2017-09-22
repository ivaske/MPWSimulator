package View;

import Controller.AktionenButtonController;
import Controller.CompileController;
import Controller.ProgrammController;
import Model.*;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Klasse des Simulators, welcher das Spiel simuliert.
 *
 * @author Yannick Vaske
 * @version 17.08.2017
 * @see Landschaft
 * @see Panzer
 */
public class Simulator {

    //Konstanten
    private static final int PADDING_TILES = 50;

    private Stage _primaryStage;
    private Programm _programm;
    private BorderPane _root;
    private BorderPane _contentPane;
    private SplitPane _splitPane;
    private TextArea _textarea;
    private ScrollPane _scrollPane;
    private LandschaftPanel _landschaftpanel;

    //Landschaft
    private Landschaft _landschaft;


    //Controller
    private AktionenButtonController _aktionenController;

    //Ob Menu oder Toolbarbuttons selected sind
    private PlacingItems _placingItems;

    //Toolbar und Buttons
    private ToolBar _toolbar;
    private Button _buttonNew;
    private Button _buttonOpen;
    private Button _buttonSave;
    private Button _buttonCompile;
    private Button _buttonSpielfeld;

    private ObservedToggleButton _buttonCharakter;
    private ObservedToggleButton _buttonPickUp;
    private ObservedToggleButton _buttonWall;
    private ObservedToggleButton _buttonHamster;
    private ObservedToggleButton _buttonDelete;
    private Button _buttonAbfrageCharakterPickUps;
    private Button _buttonAktionLinksUm;
    private Button _buttonAktionVor;
    private Button _buttonAktionNimmPickUp;
    private Button _buttonAktionGibPickUp;
    private Button _buttonAktionGenerateRandomMap;
    private Button _buttonStartSimulation;
    private Button _buttonPauseSimulation;
    private Button _buttonStopSimulation;
    private Slider _slider;

    //MenuBar
    private MenuBar _menuBar;
    private Menu _editorMenu;
    private MenuItem _neuMenuItem;
    private MenuItem _oeffnenMenuItem;
    private MenuItem _kompilierenMenuItem;
    private MenuItem _druckenMenuItem;
    private MenuItem _beendenMenuItem;

    private Menu _teritoriumMenu;
    private Menu _speichernMenu;
    private MenuItem _speichernXMLMenuItem;
    private MenuItem _speichernJAXBMenuItem;
    private MenuItem _speichernSerialisierenMenuItem;
    private Menu _ladenMenu;
    private MenuItem _ladenXMLMenuItem;
    private MenuItem _ladenJAXBMenuItem;
    private MenuItem _ladenDeserialisierenMenuItem;
    private Menu _bildspeichernMenu;
    private MenuItem _bildAlsPNGMenuItem;
    private MenuItem _teritoriumDruckenMenuItem;
    private MenuItem _groesseAendernMenuItem;
    private ObservedRadioMenuItem _charakterPlaceMenuItem;
    private ObservedRadioMenuItem _pickUpPlaceMenuItem;
    private ObservedRadioMenuItem _wallPlaceMenuItem;
    private ObservedRadioMenuItem _hamsterPlaceMenuItem;
    private ObservedRadioMenuItem _deleteMenuItem;

    private Menu _charakterMenu;
    private MenuItem _pickUpAtCharacterMenuItem;
    private MenuItem _turnleftMenuItem;
    private MenuItem _vorMenuItem;
    private MenuItem _nimmMenuItem;
    private MenuItem _gibMenuItem;

    private Menu _simulationMenu;
    private MenuItem _startResumeMenuItem;
    private MenuItem _pauseMenuItem;
    private MenuItem _stopMenuItem;

    public void start(Stage primaryStage, Programm programm) {
        _root = new BorderPane();
        _landschaft = programm.get_landschaft();
        _aktionenController = new AktionenButtonController(_landschaft, this);
        _placingItems = new PlacingItems();
        _programm = programm;


        createMenu();
        createToolbar();
        addContentPane();
        zeichneSpielfeld();

        Scene scene = new Scene(_root);

        _primaryStage = primaryStage;
        _primaryStage.setOnCloseRequest(event -> ProgrammController.schliesseProgramm(_programm));
        _primaryStage.setTitle("The Tank Game: " + programm.get_name());
        _primaryStage.getIcons().add(new Image(getClass().getResource("/resources/Panzer24.png").toString()));
        _primaryStage.setScene(scene);
        _primaryStage.setMaximized(true);
        _primaryStage.show();
    }

    /**
     * Erstellt das Menu mit den Buttons
     */
    private void createMenu() {

        _menuBar = new MenuBar();


        _editorMenu = new Menu("_Editor");
        _neuMenuItem = new MenuItem("Neu");
        _neuMenuItem.setOnAction(event -> ProgrammController.neuesProgramm());
        _neuMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("/resources/New16.gif").toString())));
        _neuMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+N"));

        _oeffnenMenuItem = new MenuItem("Öffnen");
        _oeffnenMenuItem.setOnAction(event -> ProgrammController.oeffneProgramm(_primaryStage));
        _oeffnenMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Open16.gif").toString())));
        _oeffnenMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+O"));

        _kompilierenMenuItem = new MenuItem("Kompilieren");
        _kompilierenMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+K"));


        _druckenMenuItem = new MenuItem("Drucken");
        _druckenMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Print16.gif").toString())));
        _druckenMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+P"));

        _beendenMenuItem = new MenuItem("Beenden");
        _beendenMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Q"));
        _beendenMenuItem.setOnAction(e -> ProgrammController.schliesseProgramm(_programm));

        _editorMenu.getItems().addAll(_neuMenuItem, _oeffnenMenuItem, new SeparatorMenuItem(), _kompilierenMenuItem,
                _druckenMenuItem, new SeparatorMenuItem(), _beendenMenuItem);

        _teritoriumMenu = new Menu("_Teritorium");
        _speichernMenu = new Menu("_Speichern");
        _speichernXMLMenuItem = new MenuItem("XML");
        _speichernJAXBMenuItem = new MenuItem("JAXB");
        _speichernSerialisierenMenuItem = new MenuItem("Serialisieren");
        _speichernMenu.getItems().addAll(_speichernXMLMenuItem, _speichernJAXBMenuItem, _speichernSerialisierenMenuItem);
        _ladenMenu = new Menu("_Laden");
        _ladenXMLMenuItem = new MenuItem("XML");
        _ladenJAXBMenuItem = new MenuItem("JAXB");
        _ladenDeserialisierenMenuItem = new MenuItem("Deserialisieren");
        _ladenMenu.getItems().addAll(_ladenXMLMenuItem, _ladenJAXBMenuItem, _ladenDeserialisierenMenuItem);
        _bildspeichernMenu = new Menu("Als Bild speichern");
        _bildAlsPNGMenuItem = new MenuItem("PNG");
        _bildspeichernMenu.getItems().addAll(_bildAlsPNGMenuItem);
        _groesseAendernMenuItem = new MenuItem("Größe ändern...");

        ToggleGroup group = new ToggleGroup();

        _charakterPlaceMenuItem = new ObservedRadioMenuItem(_placingItems, PlaceButtons.Panzer, "Panzer plazieren");
        _charakterPlaceMenuItem.setToggleGroup(group);
        _charakterPlaceMenuItem.setOnAction(event -> _placingItems.setisSelected(_charakterPlaceMenuItem.getButtons()));

        _pickUpPlaceMenuItem = new ObservedRadioMenuItem(_placingItems, PlaceButtons.Munition, "Munition plazieren");
        _pickUpPlaceMenuItem.setToggleGroup(group);
        _pickUpPlaceMenuItem.setOnAction(event -> _placingItems.setisSelected(_pickUpPlaceMenuItem.getButtons()));

        _wallPlaceMenuItem = new ObservedRadioMenuItem(_placingItems, PlaceButtons.Wall, "Wand plazieren");
        _wallPlaceMenuItem.setToggleGroup(group);
        _wallPlaceMenuItem.setOnAction(event -> _placingItems.setisSelected(_wallPlaceMenuItem.getButtons()));

        _hamsterPlaceMenuItem = new ObservedRadioMenuItem(_placingItems, PlaceButtons.Hamster, "Hamster plazieren");
        _hamsterPlaceMenuItem.setToggleGroup(group);
        _hamsterPlaceMenuItem.setOnAction(event -> _placingItems.setisSelected(_hamsterPlaceMenuItem.getButtons()));

        _deleteMenuItem = new ObservedRadioMenuItem(_placingItems, PlaceButtons.Delete, "Kachel Löschen");
        _deleteMenuItem.setToggleGroup(group);
        _deleteMenuItem.setOnAction(event -> _placingItems.setisSelected(_deleteMenuItem.getButtons()));

        _teritoriumMenu.getItems().addAll(_speichernMenu, _ladenMenu, _bildspeichernMenu, _groesseAendernMenuItem, new SeparatorMenuItem(),
                _charakterPlaceMenuItem, _pickUpPlaceMenuItem, _wallPlaceMenuItem, _hamsterPlaceMenuItem, _deleteMenuItem);

        _charakterMenu = new Menu("_Panzer");
        _pickUpAtCharacterMenuItem = new MenuItem("Muniton im Model.Panzer");
        _turnleftMenuItem = new MenuItem("links um");
        _turnleftMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Shift+L"));
        _turnleftMenuItem.setOnAction(event -> _aktionenController.Aktion_Links_Um());
        _vorMenuItem = new MenuItem("vor");
        _vorMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Shift+V"));
        _vorMenuItem.setOnAction(event -> _aktionenController.Aktion_Vor());
        _nimmMenuItem = new MenuItem("nimm");
        _nimmMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Shift+N"));
        _nimmMenuItem.setOnAction(event -> _aktionenController.Aktion_Nimm());
        _gibMenuItem = new MenuItem("schiessen");
        _gibMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Shift+S"));
        _gibMenuItem.setOnAction(event -> _aktionenController.Aktion_Schissen());
        _charakterMenu.getItems().addAll(_pickUpAtCharacterMenuItem, _turnleftMenuItem, _vorMenuItem, _nimmMenuItem, _gibMenuItem);

        _simulationMenu = new Menu("_Simulation");
        _startResumeMenuItem = new MenuItem("Start/Fortsetzten");
        _startResumeMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Play16.gif").toString())));
        _startResumeMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+F11"));
        _pauseMenuItem = new MenuItem("Pause");
        _pauseMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Pause16.gif").toString())));
        _stopMenuItem = new MenuItem("Stopp");
        _stopMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Stop16.gif").toString())));
        _stopMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+F12"));
        _simulationMenu.getItems().addAll(_startResumeMenuItem, _pauseMenuItem, _stopMenuItem);

        _menuBar.getMenus().addAll(_editorMenu, _teritoriumMenu, _charakterMenu, _simulationMenu);

        _root.setTop(_menuBar);
    }

    /**
     * Erstellt die Toolbar des Programms.
     */
    private void createToolbar() {
        _contentPane = new BorderPane();
        _root.setCenter(_contentPane);

        _buttonNew = new Button();
        _buttonNew.setOnAction(event -> ProgrammController.neuesProgramm());
        _buttonNew.setGraphic(new ImageView(new Image(getClass().getResource("/resources/New24.gif").toString())));

        _buttonOpen = new Button();
        _buttonOpen.setOnAction(event -> ProgrammController.oeffneProgramm(_primaryStage));
        _buttonOpen.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Open24.gif").toString())));

        _buttonSave = new Button();
        _buttonSave.setOnAction(event -> ProgrammController.speichereProgramm(_programm));
        _buttonSave.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Save24.gif").toString())));

        _buttonCompile = new Button();
        _buttonCompile.setOnAction(event -> CompileController.compile(_programm));
        _buttonCompile.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Compile24.gif").toString())));

        _buttonSpielfeld = new Button();
        _buttonSpielfeld.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Terrain24.gif").toString())));
        _buttonSpielfeld.setOnAction(event -> openResizeDialog());


        _buttonCharakter = new ObservedToggleButton(_placingItems, PlaceButtons.Panzer);
        _buttonCharakter.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Panzer24.png").toString())));
        _buttonCharakter.setOnAction(event -> _placingItems.setisSelected(_buttonCharakter.getButtons()));

        _buttonPickUp = new ObservedToggleButton(_placingItems, PlaceButtons.Munition);
        _buttonPickUp.setGraphic(new ImageView(new Image(getClass().getResource("/resources/ammo1-24.png").toString())));
        _buttonPickUp.setOnAction(event -> _placingItems.setisSelected(_buttonPickUp.getButtons()));

        _buttonWall = new ObservedToggleButton(_placingItems, PlaceButtons.Wall);
        _buttonWall.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Wall24.gif").toString())));
        _buttonWall.setOnAction(event -> _placingItems.setisSelected(_buttonWall.getButtons()));

        _buttonHamster = new ObservedToggleButton(_placingItems, PlaceButtons.Hamster);
        _buttonHamster.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Hamster24.png").toString())));
        _buttonHamster.setOnAction(event -> _placingItems.setisSelected(_buttonHamster.getButtons()));

        _buttonDelete = new ObservedToggleButton(_placingItems, PlaceButtons.Delete);
        _buttonDelete.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Delete24.gif").toString())));
        _buttonDelete.setOnAction(event -> _placingItems.setisSelected(_buttonDelete.getButtons()));

        _buttonAbfrageCharakterPickUps = new Button();
        _buttonAbfrageCharakterPickUps.setGraphic(new ImageView(new Image(getClass().getResource("/resources/tankammo24.png").toString())));
        _buttonAbfrageCharakterPickUps.setOnAction(event -> _aktionenController.Abfrage_Munition_leer());

        _buttonAktionLinksUm = new Button();
        _buttonAktionLinksUm.setGraphic(new ImageView(new Image(getClass().getResource("/resources/tankleft24.png").toString())));
        _buttonAktionLinksUm.setOnAction(event -> _aktionenController.Aktion_Links_Um());

        _buttonAktionVor = new Button();
        _buttonAktionVor.setGraphic(new ImageView(new Image(getClass().getResource("/resources/tankvor24.png").toString())));
        _buttonAktionVor.setOnAction(event -> _aktionenController.Aktion_Vor());

        _buttonAktionNimmPickUp = new Button();
        _buttonAktionNimmPickUp.setGraphic(new ImageView(new Image(getClass().getResource("/resources/ammoplus24.png").toString())));
        _buttonAktionNimmPickUp.setOnAction(event -> _aktionenController.Aktion_Nimm());

        _buttonAktionGibPickUp = new Button();
        _buttonAktionGibPickUp.setGraphic(new ImageView(new Image(getClass().getResource("/resources/fadenkreuz24.png").toString())));
        _buttonAktionGibPickUp.setOnAction(event -> _aktionenController.Aktion_Schissen());

        _buttonAktionGenerateRandomMap = new Button();
        _buttonAktionGenerateRandomMap.setGraphic(new ImageView(new Image(getClass().getResource("/resources/world24.png").toString())));
        _buttonAktionGenerateRandomMap.setOnAction(event -> _aktionenController.Button_RandomMap_Click());

        _buttonStartSimulation = new Button();
        _buttonStartSimulation.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Play24.gif").toString())));
        _buttonStartSimulation.setOnAction(event -> _programm.get_landschaft().get_panzer().main());

        _buttonPauseSimulation = new Button();
        _buttonPauseSimulation.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Pause24.gif").toString())));

        _buttonStopSimulation = new Button();
        _buttonStopSimulation.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Stop24.gif").toString())));

        _slider = new Slider();

        _toolbar = new ToolBar();
        _toolbar.getItems().addAll(_buttonNew, _buttonOpen, new Separator(), _buttonSave, _buttonCompile,
                new Separator(), _buttonSpielfeld, _buttonCharakter, _buttonPickUp, _buttonWall, _buttonHamster, _buttonDelete, new Separator(),
                _buttonAbfrageCharakterPickUps, _buttonAktionLinksUm, _buttonAktionVor, _buttonAktionNimmPickUp, _buttonAktionGibPickUp, _buttonAktionGenerateRandomMap,
                new Separator(), _buttonStartSimulation, _buttonPauseSimulation, _buttonStopSimulation, _slider);

        _contentPane.setTop(_toolbar);
    }


    private void addContentPane() {
        _splitPane = new SplitPane();
        _splitPane.setOrientation(Orientation.HORIZONTAL);
        _scrollPane = new ScrollPane();
        _scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        _scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        _textarea = new TextArea();
        _textarea.setText(_programm.get_textFieldContent());
        _textarea.addEventHandler(KeyEvent.KEY_RELEASED, event -> _programm.set_textFieldContent(_textarea.getText()));

        _splitPane.getItems().addAll(_textarea, _scrollPane);

        _contentPane.setCenter(_splitPane);
    }


    public void zeichneSpielfeld() {

        //Spielfeld zeichnen:
        //Spielfeld wird nach jedem Zug komplett neu gezeichnet!

        _programm.set_landschaftPanel(new LandschaftPanel(_landschaft, _placingItems, _scrollPane));
        _programm.get_landschaftPanel().zeichneSpielfeld();

        _scrollPane.setContent(_programm.get_landschaftPanel());

    }

    public void openResizeDialog() {
        Dialog dialog = new Dialog();

        GridPane gridPane = new GridPane();
        TextField textField_Rows = new TextField(Integer.toString(_landschaft.get_spielfeldGroesseRows()));
        TextField textField_Cols = new TextField(Integer.toString(_landschaft.get_spielfeldGroesseCols()));


        Label labelRows = new Label("Anzahl Rows:");
        Label labelCols = new Label("Anzahl Cols:");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        gridPane.add(labelRows, 0, 0);
        gridPane.add(textField_Rows, 0, 1);
        gridPane.add(labelCols, 1, 0);
        gridPane.add(textField_Cols, 1, 1);

        dialog.getDialogPane().setContent(gridPane);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/resources/Panzer24.png"));

        Optional<ButtonType> result = dialog.showAndWait();
        //Herausfinden, ob APPLY-Button gedrückt wurde
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (isNumeric(textField_Rows.getText()) && isNumeric(textField_Cols.getText())) {
                int rows = Integer.parseInt(textField_Rows.getText());
                int cols = Integer.parseInt(textField_Cols.getText());
                _landschaft.aendereSpielfeldGroesse(rows, cols);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fehlerhafte eingabe.", ButtonType.OK);
                alert.show();
            }

        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }

    private void DialogResizeSaveButtonClick(Button button, Dialog dialog) {
        button.setUserData(true);
        dialog.close();
    }

    private void centerScroll() {

    }

}