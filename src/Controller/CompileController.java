package Controller;

import Model.IO;
import Model.Landschaft;
import Model.Panzer;
import Model.Programm;
import Simulation.SimulationState;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import javax.tools.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Yannick Vaske
 * @version 20.09.2017
 * Diese Klasse ist der Controller für alle Compiliervorgänge.
 */
public class CompileController {

    private static final String DATEIENDUNG_COMPILED = ".class";

    /**
     * Compiliert ein Programm. Speichert es zuerst.
     *
     * @param programm Programm, welches compiliert werden soll.
     */
    public static void compile(Programm programm) {
        if (programm.get_manager().get_state() == SimulationState.STOPED) {

            File file = ProgrammController.speichereProgramm(programm);
            compile(file);
            programm.get_landschaft().setzePanzer(createInstanceFromProgramm(programm));
        } else {
            programm.get_manager().pauseSimulation();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Wollen Sie die aktuelle Simulation abbrechen und neu kompilieren?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                programm.get_manager().stopSimulation();
                compile(programm);
            } else {
                programm.get_manager().startSimulation();
            }
        }

    }

    /**
     * Compiliert ein Programm. Speichert es zuerst.
     *
     * @param programm Programm, welches compiliert werden soll.
     */
    public static boolean trycompileSilent(Programm programm) {
        File file = ProgrammController.speichereProgramm(programm);
        boolean erfolg = compileSilent(file, null);
        if (erfolg) {
            programm.get_landschaft().setzePanzer(createInstanceFromProgramm(programm));
        }
        return erfolg;
    }

    /**
     * Diese Methode compiliert eine Java-Datei. Sie wird nach Abschluss des Vorgangs eine Meldung ausgeben, je nachdem ob der Compiliervorgang erfolgreich war oder nicht.
     *
     * @param file Die zu compilierende Java-Datei
     */
    public static void compile(File file) {
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();

        boolean erfolg = compileSilent(file, diagnosticCollector);

        if (erfolg) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Das Kompilieren war erfolgreich.");
            alert.showAndWait();
        } else {
            String fehler = "Es ist ein Fehler aufgetreten: \n";

            for (Diagnostic<?> diagnostic : diagnosticCollector.getDiagnostics()) {
                fehler = fehler + "Typ: " + diagnostic.getKind() + "\n Zeile: " + (diagnostic.getLineNumber() - 6) + "\n" + "Meldung: " + diagnostic.getMessage(Locale.GERMAN) + "\n\n";
            }

            Alert alert = new Alert(Alert.AlertType.ERROR, fehler);
            alert.showAndWait();
        }

    }

    /**
     * Diese Methode hat die selbe Funktionalität wie die andere compile-Methode, nur wirft diese keine Meldungen.
     *
     * @param file                Die zu compilierende Java-Datei
     * @param diagnosticCollector enthält mögliche compilier-Fehler
     * @return gibt zurück, ob das compilieren erfolgreich war.
     */
    public static boolean compileSilent(File file, DiagnosticCollector<JavaFileObject> diagnosticCollector) {
        if (diagnosticCollector == null) {
            diagnosticCollector = new DiagnosticCollector<>();
        }
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = javac.getStandardFileManager(diagnosticCollector, null, null);

        List<File> javaFiles = new ArrayList<>();
        javaFiles.add(file);

        Iterable<? extends JavaFileObject> units = manager.getJavaFileObjectsFromFiles(javaFiles);

        JavaCompiler.CompilationTask task = javac.getTask(null, manager, diagnosticCollector, null, null, units);

        return task.call();
    }

    /**
     * Erstellt eine Instanz mithilfe des Classloaders eines Panzers und gibt diese Zurück. Erforder vorheriges Compilieren.
     *
     * @param programm Das Programm welches instanziert werden soll.
     * @return Gibt eine Instanz eines Panzers zurück
     */
    public static Panzer createInstanceFromProgramm(Programm programm) {
        try {

            String nameCompiled = ProgrammController.PFAD_DATEIEN;
            Path pathCompiled = Paths.get(nameCompiled);

            if (Files.exists(pathCompiled)) {

                URL[] urls = new URL[]{pathCompiled.toUri().toURL()};

                ClassLoader classLoader = new URLClassLoader(urls);

                Class<?> cls = classLoader.loadClass(programm.get_name());

                return (Panzer) cls.getDeclaredConstructor(Landschaft.class,
                        AktionenButtonController.class).newInstance(programm.get_landschaft(),
                        new AktionenButtonController(programm.get_landschaft(), programm.get_simulator()));


            } else {
                return null;
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
