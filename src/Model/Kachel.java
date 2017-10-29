package Model;

import javafx.scene.image.ImageView;
import java.io.Serializable;

/**
 * Klasse der Kachel, aus denen die Landschaft besteht.
 *
 * @author Yannick Vaske
 * @version 17.08.2017
 * @see Landschaft
 */
public class Kachel implements Serializable {
    static final long serialVersionUID = 2;


    private KachelTyp _typ;
    private int _anzahlMunition;
    private ImageView view;

    public Kachel() {
        _typ = KachelTyp.leer;
    }

    public Kachel(KachelTyp typ) {
        _typ = typ;

    }


    public void set_Kacheltyp(KachelTyp _typ) {
        this._typ = _typ;
    }

    public KachelTyp get_Kacheltyp() {
        return _typ;
    }

    public int get_anzahlMunition() {
        return _anzahlMunition;
    }

    public void set_anzahlMunition(int _anzahlMunition) {
        this._anzahlMunition = _anzahlMunition;
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }
}
