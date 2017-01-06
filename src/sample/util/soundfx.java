package sample.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Created by raza.ahmed on 06.01.2017.
 */
public class Soundfx {
    public static void startGame(){
        String musicFile4 = "sounds/start_game.wav";
        Media start_game = new Media(new File(musicFile4).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(start_game);
        mediaPlayer.play();
    }

    public static void quitGame() {
        String musicFile4 = "sounds/quit_game.wav";
        Media quit_game = new Media(new File(musicFile4).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(quit_game);
        mediaPlayer.play();
    }

    public static void picked_apple() {
        String musicFile4 = "sounds/picked_apple.wav";
        Media picked_apple = new Media(new File(musicFile4).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(picked_apple);
        mediaPlayer.play();
    }

    public static void picked_invincible() {
        String musicFile4 = "sounds/picked_invincible.wav";
        Media picked_invincible = new Media(new File(musicFile4).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(picked_invincible);
        mediaPlayer.play();
    }

    public static void picked_nowalls() {
        String musicFile4 = "sounds/picked_nowalls.wav";
        Media picked_nowalls = new Media(new File(musicFile4).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(picked_nowalls);
        mediaPlayer.play();
    }

    public static void picked_slowmotion() {
        String musicFile4 = "sounds/picked_slowmotion.wav";
        Media picked_slowmotion = new Media(new File(musicFile4).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(picked_slowmotion);
        mediaPlayer.play();
    }
}
