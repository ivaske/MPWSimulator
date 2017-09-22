package Controller;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class SoundController {
    private static SoundController ourInstance = new SoundController();

    public static SoundController getInstance() {
        return ourInstance;
    }

    private AudioClip errorClip;

    private SoundController() {
        URL resssource = getClass().getResource("/resources/death.wav");
        errorClip = new AudioClip(resssource.toString());

    }

    public void ErrorSoundAbspielen() {
        errorClip.play(1);
    }
}
