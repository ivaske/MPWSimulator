import Model.IO;
import Model.Landschaft;

/**
 * Ein Konsolen-Test-Programm um die den Simulator ohne grafische Oberflaeche zu testen.
 * @author Yannick Vaske
 * @version 17.08.2017
 */
public class KonsolenTest {

    public static void main(String[] args) throws Exception {
        Landschaft landschaft = new Landschaft();
        landschaft.printToConsole();

        while (true) {

            char input = leseUserEingabe();

            switch (input) {
                case 'q':
                    System.exit(1);
                case 'l':
                    landschaft.linksUm();
                    break;
                case 'v':
                    landschaft.vor();
                    break;
                case 'n':
                    landschaft.nimm();
                    break;
                case 's':
                    landschaft.schiessen();
                    break;
                case 'f':
                    if (landschaft.vornFrei()) {
                        IO.println("Vorne ist frei!");
                    } else {
                        IO.println("Vorne ist nicht frei!");
                    }
                    break;
                case 'm':
                    if (landschaft.munitionDa()) {
                        IO.println("Munition ist da!");
                    } else {
                        IO.println("Keine Munition da!");
                    }
                    break;
                case 'w':
                    if (landschaft.wandVoraus()) {
                        IO.println("Eine Wand ist voraus!");
                    } else {
                        IO.println("Keine Wand voraus!");
                    }
                    break;
                case 'a':
                    if (landschaft.munitionLeer()) {
                        IO.println("Model.Panzer hat keine Munition!");
                    } else {
                        IO.println("Model.Panzer ist geladen!");
                    }
                    break;
            }
            IO.println();
            landschaft.printToConsole();
        }

    }

    private static char leseUserEingabe() {
        IO.println();
        char input;

        do {
            input = IO.readChar("l:links um; v:vor; n:nimm; s:schiessen; f:vornfrei; m:munitionDa; a:munitionLeer; w:Wandvoraus; q:beenden");

        }
        while (input != 'l' && input != 'v' && input != 'n' && input != 's' && input != 'f' && input != 'm' && input != 'a' && input != 'w' && input != 'q');

        return input;
    }

}
