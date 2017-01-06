package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.Main;
import sample.util.Soundfx;


/**
 * Created by niklas.fassbender on 04.01.2017.
 */
public class SinglePlayerController {

    Main main;

    @FXML
    private Label scoreLabel;


    @FXML
    public void initialize(){

    }

    @FXML
    public void handleRestart(){
        main.restartGame();
    }

    @FXML
    public void handleQuit(){
        Soundfx.quitGame();
        main.exitGame();
    }

    public void setMain(Main main) {
        this.main = main;
        scoreLabel.setText(String.valueOf(main.getGameLoop().getSnakes().get(0).getScore()));
    }
}
