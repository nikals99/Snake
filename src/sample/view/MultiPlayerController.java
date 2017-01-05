package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import sample.Main;

/**
 * Created by niklas.fassbender on 05.01.2017.
 */
public class MultiPlayerController {

    Main main;

    @FXML
    Label winnerLabel;

    @FXML
    Label scorePlayer1;

    @FXML
    Label scorePlayer2;


    @FXML
    public void initialize(){

    }

    public void setWinnerLabelText(String text){
        winnerLabel.setText(text);

    }

    public void setMain(Main main){
        this.main = main;
        scorePlayer1.setText(String.valueOf(main.getGameLoop().getSnakes().get(0).getScore()));
        scorePlayer2.setText(String.valueOf(main.getGameLoop().getSnakes().get(1).getScore()));


    }

    @FXML
    public void handleRestart(){
        main.restartGame();
    }

    @FXML
    public void handleQuit(){
        main.exitGame();
    }




}
