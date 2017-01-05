package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.models.GameSettings;
import sample.view.MenuController;
import sample.view.MultiPlayerController;
import sample.view.SinglePlayerController;


import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {

    Stage endOfGameStage;
    public static ArrayList<String> input;
    private Stage primaryStage;
    private Scene mainScene;
    private Scene gameScene;
    private AnchorPane pane;
    GameLoop gameLoop;
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Snake");



        initMenuLayout();


        primaryStage.show();
    }



    public void loadGame(){
        Group root = new Group();
        gameScene = new Scene(root);


        Canvas canvas = new Canvas(GameSettings.gridWidth*GameSettings.snakeSize,GameSettings.gridHeight*GameSettings.snakeSize);
        root.getChildren().add(canvas);

        primaryStage.setScene(gameScene);
        handleInput();

        gameLoop = new GameLoop();
        gameLoop.setMain(this);
        gameLoop.setGc(canvas.getGraphicsContext2D());
        gameLoop.start();

    }

    public void initMenuLayout(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Menu.fxml"));
            pane = (AnchorPane) loader.load();

            MenuController controller = loader.getController();
            controller.setMain(this);

            // Show the scene containing the root layout.
            mainScene = new Scene(pane);
            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endGame(int looserSnake) {
        gameLoop.stop();

        endOfGameStage = new Stage();

        if (!GameSettings.multiplayer) {
            endOfGameStage.setTitle("Game Over");

            try {
                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/SinglePlayer.fxml"));
                AnchorPane endOfGamePane = (AnchorPane) loader.load();

                SinglePlayerController controller = loader.getController();
                controller.setMain(this);


                // Show the scene containing the root layout.
                Scene endOfGameScene = new Scene(endOfGamePane);
                endOfGameStage.setScene(endOfGameScene);
                endOfGameStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/Multiplayer.fxml"));
                AnchorPane endOfGamePane = (AnchorPane) loader.load();

                MultiPlayerController controller = loader.getController();
                controller.setMain(this);
                if(looserSnake==0){
                    controller.setWinnerLabelText("Player 1");
                }else if(looserSnake == 1) {
                    controller.setWinnerLabelText("Player 2");
                }else {

                }

                // Show the scene containing the root layout.
                Scene endOfGameScene = new Scene(endOfGamePane);
                endOfGameStage.setScene(endOfGameScene);
                endOfGameStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void restartGame(){
        endOfGameStage.close();
        gameLoop.restartGame();
        gameLoop.start();
    }


    public void exitGame(){
        endOfGameStage.close();
        gameLoop = null;
        initMenuLayout();
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    private void handleInput(){
        input = new ArrayList<String>();

        gameScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    // only add once... prevent duplicates
                    if ( !input.contains(code) )
                        input.add( code );
                });

        gameScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    input.remove( code );
                });
    }




    //Start Method
    public static void main(String[] args) {
        launch(args);
    }
}
