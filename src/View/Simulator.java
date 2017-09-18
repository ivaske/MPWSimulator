package View;

import Controller.AktionenButtonController;
import Controller.ProgrammController;
import Model.*;
import javafx.application.*;
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
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.util.Optional;

/**
 * Klasse des Simulators, welcher das Spiel simuliert.
 *
 * @author Yannick Vaske
 * @version 17.08.2017
 * @see Landschaft
 * @see Panzer
 */
public class Simulator extends Application {

    //Konstanten
    private static final int PADDING_TILES = 50;


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


    // public static void main(String[] args) throws Exception {
    //     launch(args);
    // }

    @Override
    public void start(Stage primaryStage) {
        _root = new BorderPane();
        _landschaft = new Landschaft();
        _aktionenController = new AktionenButtonController(_landschaft, this);
        _placingItems = new PlacingItems();


        createMenu();
        createToolbar();
        addContentPane();
        zeichneSpielfeld();

        Scene scene = new Scene(_root);

        primaryStage.setTitle("The Tank Game");
        primaryStage.getIcons().add(new Image(getClass().getResource("/resources/Panzer24.png").toString()));
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
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
        _oeffnenMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Open16.gif").toString())));
        _oeffnenMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+O"));
        _kompilierenMenuItem = new MenuItem("Kompilieren");
        _kompilierenMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+K"));
        _druckenMenuItem = new MenuItem("Drucken");
        _druckenMenuItem.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Print16.gif").toString())));
        _druckenMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+P"));
        _beendenMenuItem = new MenuItem("Beenden");
        _beendenMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Q"));
        _beendenMenuItem.setOnAction(e -> Platform.exit());

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
        _turnleftMenuItem.setOnAction(event -> _aktionenController.Button_LinksUm_Click());
        _vorMenuItem = new MenuItem("vor");
        _vorMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Shift+V"));
        _vorMenuItem.setOnAction(event -> _aktionenController.Button_Vor_Click());
        _nimmMenuItem = new MenuItem("nimm");
        _nimmMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Shift+N"));
        _nimmMenuItem.setOnAction(event -> _aktionenController.Button_Nimm_Click());
        _gibMenuItem = new MenuItem("schiessen");
        _gibMenuItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Shift+S"));
        _gibMenuItem.setOnAction(event -> _aktionenController.Button_Schiessen_Click());
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
        _buttonNew.setGraphic(new ImageView(new Image(getClass().getResource("/resources/New24.gif").toString())));

        _buttonOpen = new Button();
        _buttonOpen.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Open24.gif").toString())));

        _buttonSave = new Button();
        _buttonSave.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Save24.gif").toString())));

        _buttonCompile = new Button();
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
        _buttonAbfrageCharakterPickUps.setOnAction(event -> _aktionenController.Button_Munitionleer_Click());

        _buttonAktionLinksUm = new Button();
        _buttonAktionLinksUm.setGraphic(new ImageView(new Image(getClass().getResource("/resources/tankleft24.png").toString())));
        _buttonAktionLinksUm.setOnAction(event -> _aktionenController.Button_LinksUm_Click());

        _buttonAktionVor = new Button();
        _buttonAktionVor.setGraphic(new ImageView(new Image(getClass().getResource("/resources/tankvor24.png").toString())));
        _buttonAktionVor.setOnAction(event -> _aktionenController.Button_Vor_Click());

        _buttonAktionNimmPickUp = new Button();
        _buttonAktionNimmPickUp.setGraphic(new ImageView(new Image(getClass().getResource("/resources/ammoplus24.png").toString())));
        _buttonAktionNimmPickUp.setOnAction(event -> _aktionenController.Button_Nimm_Click());

        _buttonAktionGibPickUp = new Button();
        _buttonAktionGibPickUp.setGraphic(new ImageView(new Image(getClass().getResource("/resources/fadenkreuz24.png").toString())));
        _buttonAktionGibPickUp.setOnAction(event -> _aktionenController.Button_Schiessen_Click());

        _buttonAktionGenerateRandomMap = new Button();
        _buttonAktionGenerateRandomMap.setGraphic(new ImageView(new Image(getClass().getResource("/resources/world24.png").toString())));
        _buttonAktionGenerateRandomMap.setOnAction(event -> _aktionenController.Button_RandomMap_Click());

        _buttonStartSimulation = new Button();
        _buttonStartSimulation.setGraphic(new ImageView(new Image(getClass().getResource("/resources/Play24.gif").toString())));

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
        _textarea.setText("public static void main(String[] args) {\n" +
                "\n" +
                "}");
        _splitPane.getItems().addAll(_textarea, _scrollPane);

        _contentPane.setCenter(_splitPane);
    }


    public void zeichneSpielfeld() {

        //Spielfeld zeichnen:
        //Spielfeld wird nach jedem Zug komplett neu gezeichnet!

        _landschaftpanel = new LandschaftPanel(_landschaft, _placingItems, _scrollPane);
        _landschaftpanel.zeichneSpielfeld();

        _scrollPane.setContent(_landschaftpanel);

    }

    public void openResizeDialog() {
        Dialog dialog = new Dialog();

        GridPane gridPane = new GridPane();
        TextField textField_Rows = new TextField(Integer.toString(_landschaft.get_spielfeldGroesseRows()));
        TextField textField_Cols = new TextField(Integer.toString(_landschaft.get_spielfeldGroesseCols()));

        ButtonType buttonTypeExit = new ButtonType("Setzte Spielfeldgröße", ButtonBar.ButtonData.APPLY);

        Label labelRows = new Label("Anzahl Rows:");
        Label labelCols = new Label("Anzahl Cols:");
        dialog.getDialogPane().getButtonTypes().add(buttonTypeExit);

        gridPane.add(labelRows, 0, 0);
        gridPane.add(textField_Rows, 0, 1);
        gridPane.add(labelCols, 1, 0);
        gridPane.add(textField_Cols, 1, 1);

        dialog.getDialogPane().setContent(gridPane);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/resources/Panzer24.png"));

        Optional<ButtonBar.ButtonData> result = dialog.showAndWait();
        //Herausfinden, ob APPLY-Button gedrückt wurde
        if (result != null && result.toString().substring(result.toString().indexOf("buttonData")).contains("APPLY")) {
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