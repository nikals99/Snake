package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    public void setMain(Main main){
        this.main = main;
        scorePlayer1.setText(String.valueOf(main.getGameLoop().getSnakes().get(0).getScore()));
        scorePlayer2.setText(String.valueOf(main.getGameLoop().getSnakes().get(1).getScore()));


    }



}
