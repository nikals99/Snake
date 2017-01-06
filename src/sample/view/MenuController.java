package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.Main;
import sample.models.GameSettings;

import java.io.File;

/**
 * Created by niklas.fassbender on 04.01.2017.
 */
public class MenuController {

    private Main main;

    @FXML
    private ComboBox<String> difficulty;

    @FXML
    private ComboBox<String> levelSize;

    @FXML
    private ComboBox<Integer> growthPerFood;


    @FXML
    public void initialize(){
        difficulty.getItems().add("Easy");
        difficulty.getItems().add("Medium");
        difficulty.getItems().add("Hard");

        difficulty.getSelectionModel().select(1);

        levelSize.getItems().add("Extra-Small");
        levelSize.getItems().add("Small");
        levelSize.getItems().add("Medium");
        levelSize.getItems().add("Large");

        levelSize.getSelectionModel().select(2);

        growthPerFood.getItems().add(1);
        growthPerFood.getItems().add(3);
        growthPerFood.getItems().add(5);

        growthPerFood.getSelectionModel().select(0);


    }

    @FXML
    public void handleSinglePlayer(){
        GameSettings.multiplayer = false;

        startGame();
    }

    public void startGame(){
        switch (difficulty.getSelectionModel().getSelectedItem()){
            case "Easy":
                GameSettings.normalSpeed = 150;
                break;
            case "Medium":
                GameSettings.normalSpeed = 100;
                break;
            case "Hard":
                GameSettings.normalSpeed = 50;
                break;
            default:
                GameSettings.normalSpeed = 100;
                break;
        }

        switch (levelSize.getSelectionModel().getSelectedItem()){
            case "Extra-Small":
                GameSettings.gridWidth = 15;
                GameSettings.gridHeight = 10;
                break;
            case "Small":
                GameSettings.gridWidth = 30;
                GameSettings.gridHeight = 20;
                break;
            case "Medium":
                GameSettings.gridWidth = 40;
                GameSettings.gridHeight = 25;
                break;
            case "Large":
                GameSettings.gridWidth = 60;
                GameSettings.gridHeight = 35;
                break;
            default:
                break;
        }



        switch (growthPerFood.getSelectionModel().getSelectedItem()){
            case 1:
                GameSettings.growthPerFood = 1;
                break;
            case 3:
                GameSettings.growthPerFood = 3;
                break;
            case 5:
                GameSettings.growthPerFood = 5;
                break;

            default:
                GameSettings.growthPerFood = 1;
        }
        main.loadGame();
    }

    @FXML
    public void handleMultiplayer(){
        GameSettings.multiplayer = true;

        startGame();

    }


    public void setMain(Main main){
        this.main = main;
    }

}
