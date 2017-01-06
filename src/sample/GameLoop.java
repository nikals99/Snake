package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.models.*;
import sample.models.Object;
import sample.util.soundfx;

import java.io.File;
import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static sample.Main.input;


public class GameLoop extends AnimationTimer{

    Main main;

    GraphicsContext gc;
    long lastLogicTime = 0;

    ArrayList<Snake> snakes;
    boolean snakeIsAlive = false;


    ArrayList<sample.models.Object> objects;

    soundfx soundfx;

    Image wallbrick;
    Image slowMotionPowerUp;
    Image noWallsPowerUp;
    Image invinciblePowerUp;

    Image bodypartGreen;

    Image bodyPartBlue;

    Image[] headsBlue;
    Image[] headsGreen;

    Image apple;

    ArrayList<PowerUp> activePowerUps;

    int looserSnake = -1;

    int canvasOffset = 0;

    @Override
    public void handle(long currentNanoTime){
        long timeElapsedLogic = currentNanoTime- lastLogicTime;
        timeElapsedLogic = TimeUnit.NANOSECONDS.toMillis(timeElapsedLogic);


        if (!snakeIsAlive && snakes == null) {
            initGame();
            snakeIsAlive = true;
            spawnRandomPowerUP();
        }

        setDirection();

        if(snakeIsAlive) {
            if (timeElapsedLogic > GameSettings.speed ) {
                lastLogicTime = currentNanoTime;


                if(Math.random()<0.005){
                    spawnRandomPowerUP();
                }

                //Move the snake
                moveSnake();
                detectCollisons();
                handleActivePowerUps();


                if(snakeIsAlive)
                    renderScene();

            }
        }

    }

    private void handleActivePowerUps() {
        if(activePowerUps.size() == 0){
            GameSettings.walls = true;
            GameSettings.speed = GameSettings.normalSpeed;
        }
        for (int i = 0; i < activePowerUps.size(); i++) {
            if (activePowerUps.get(i).getEndTime() < System.currentTimeMillis()) {
                switch (activePowerUps.get(i).getType()) {
                    case POWERUP_NOWALLS:
                        GameSettings.walls = true;
                        break;
                    case POWERUP_SLOW:
                        GameSettings.speed = GameSettings.normalSpeed;
                        break;
                    case POWERUP_INVINCIBLE:
                        break;
                }
                activePowerUps.remove(i);
                System.out.println("Removed Active PowerUp");
            } else {
                switch (activePowerUps.get(i).getType()) {
                    case POWERUP_NOWALLS:
                        GameSettings.walls = false;
                        break;
                    case POWERUP_SLOW:
                        GameSettings.speed = 300;
                        break;
                    case POWERUP_INVINCIBLE:
                       break;
                }
            }
        }

        for(int player = 0; player < snakes.size();player++){
            for(int i = 0; i <snakes.get(player).().size(); i++){
                if (snakes.get(player).getActivePowerUps().get(i).getEndTime() < System.currentTimeMillis()) {
                    switch (snakes.get(player).getActicePowerUps().get(i).getType()) {
                        case POWERUP_NOWALLS:

                            break;
                        case POWERUP_SLOW:

                            break;
                        case POWERUP_INVINCIBLE:
                            snakes.get(player).setInvincible(false);
                            break;
                    }
                    snakes.get(player).getActicePowerUps().remove(i);
                    System.out.println("Removed Active PowerUp");
                } else {
                    switch (snakes.get(player).getActicePowerUps().get(i).getType()) {
                        case POWERUP_NOWALLS:
                            GameSettings.walls = false;
                            break;
                        case POWERUP_SLOW:
                            GameSettings.speed = 300;
                            break;
                        case POWERUP_INVINCIBLE:
                            snakes.get(player).setInvincible(true);
                            break;
                    }
                }
            }
        }
    }

    private void spawnFood(){
        boolean isSpawned = false;
        Position tempPos = null;
        while (!isSpawned) {
            isSpawned = true;
            tempPos = new Position(getRandomNumberInRange(1, GameSettings.gridWidth - 2), getRandomNumberInRange(1, GameSettings.gridHeight - 2), Direction.DOWN);
            for(int z = 0; z < snakes.size(); z++) {
                for (int i = 0; i < snakes.get(z).getPositions().size(); i++) {
                    if (tempPos.getX() == snakes.get(z).getPositions().get(i).getX() && tempPos.getY() == snakes.get(z).getPositions().get(i).getY()) {
                        isSpawned = false;
                    }
                }
            }
        }
        objects.add(new Object(tempPos,ObjectType.FOOD));
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    private void moveSnake(){

        for(int i = 0; i < snakes.size(); i++) {
            snakes.get(i).move(snakes.get(i).getDir());
        }

    }




    private void initGame(){
        objects = new ArrayList<Object>();

        wallbrick = new Image("sample/images/wallbrick.png");
        slowMotionPowerUp = new Image("sample/images/slowmotion_powerup.png");
        noWallsPowerUp = new Image("sample/images/nowalls_powerup.png");
        invinciblePowerUp = new Image("sample/images/invincible_powerup.png");
        bodypartGreen = new Image("sample/images/bodypart_green.png");
        bodyPartBlue = new Image("sample/images/bodypart_blue.png");

        headsGreen = new Image[4];
        headsGreen[0] = new Image("sample/images/head_green_up.png");
        headsGreen[1] = new Image("sample/images/head_green_down.png");
        headsGreen[2] = new Image("sample/images/head_green_left.png");
        headsGreen[3] = new Image("sample/images/head_green_right.png");

        headsBlue = new Image[4];
        headsBlue[0] = new Image("sample/images/head_blue_up.png");
        headsBlue[1] = new Image("sample/images/head_blue_down.png");
        headsBlue[2] = new Image("sample/images/head_blue_left.png");
        headsBlue[3] = new Image("sample/images/head_blue_right.png");

        apple = new Image("sample/images/apple.png");

        soundfx = new soundfx();
        soundfx.startGame();

        activePowerUps = new ArrayList<PowerUp>();

        gc.clearRect(0, 0, GameSettings.snakeSize * GameSettings.gridWidth, 15 * GameSettings.gridHeight);
        snakes = new ArrayList<Snake>();
        snakes.add(new Snake());

        snakes.get(0).getPositions().add(new Position(5, 5 ,snakes.get(0).getDir()));
        snakes.get(0).getPositions().add(new Position(5,4, snakes.get(0).getDir()));
        snakes.get(0).getPositions().add(new Position(5,4, snakes.get(0).getDir()));

        if(GameSettings.multiplayer) {
             snakes.add(new Snake());
             snakes.get(1).getPositions().add(new Position(10, 5 ,snakes.get(1).getDir()));
             snakes.get(1).getPositions().add(new Position(10,4, snakes.get(1).getDir()));
             snakes.get(1).getPositions().add(new Position(10,4, snakes.get(1).getDir()));
        }
        snakeIsAlive = true;



        spawnFood();
        initWall();
    }


    private void setDirection(){
        if(input.contains("DOWN")&& snakes.get(0).getDir() != Direction.UP)
            snakes.get(0).setDir(Direction.DOWN);
        else if (input.contains("UP")&& snakes.get(0).getDir() != Direction.DOWN)
            snakes.get(0).setDir(Direction.UP);
        else if(input.contains("LEFT") && snakes.get(0).getDir() != Direction.RIGHT)
            snakes.get(0).setDir(Direction.LEFT);
        else if(input.contains("RIGHT") && snakes.get(0).getDir() != Direction.LEFT)
            snakes.get(0).setDir(Direction.RIGHT);

        if(snakes.size() > 1) {
            if (input.contains("S") && snakes.get(1).getDir() != Direction.UP)
                snakes.get(1).setDir(Direction.DOWN);
            else if (input.contains("W") && snakes.get(1).getDir() != Direction.DOWN)
                snakes.get(1).setDir(Direction.UP);
            else if (input.contains("A") && snakes.get(1).getDir() != Direction.RIGHT)
                snakes.get(1).setDir(Direction.LEFT);
            else if (input.contains("D") && snakes.get(1).getDir() != Direction.LEFT)
                snakes.get(1).setDir(Direction.RIGHT);

        }

        if(input.contains("ESCAPE")){
            this.gameOver();
        }
    }


    private void renderScene(){
        gc.clearRect(0, 0, GameSettings.gridWidth * GameSettings.snakeSize+ 300, GameSettings.gridHeight * GameSettings.snakeSize);

        if(!GameSettings.multiplayer){
            canvasOffset = 0;
        }else {
            canvasOffset = 150;
        }

        renderSnakes();
        renderObjects();
        renderIngameUI();



        //TODO Ingame UI verschieben Tipp canvassize - x

    }

    private void renderSnakes(){
        Image[] snakeHeads = null;
        Image snakeBody = null;
        for (int player = 0; player < snakes.size(); player++) {
            if(player == 0) {
                snakeHeads = headsGreen;
                snakeBody = bodypartGreen;
            }else{
                snakeBody = bodyPartBlue;
                snakeHeads = headsBlue;
            }


            Position tempPos = snakes.get(player).getPositions().get(0);

            switch (tempPos.getDirection()) {
                case DOWN:
                    gc.drawImage(snakeHeads[1],tempPos.getX() * GameSettings.snakeSize + canvasOffset, tempPos.getY() * GameSettings.snakeSize);
                    break;
                case UP:
                    gc.drawImage(snakeHeads[0],tempPos.getX() * GameSettings.snakeSize + canvasOffset, tempPos.getY() * GameSettings.snakeSize);
                    break;
                case RIGHT:
                    gc.drawImage(snakeHeads[3],tempPos.getX() * GameSettings.snakeSize + canvasOffset, tempPos.getY() * GameSettings.snakeSize);
                    break;
                case LEFT:
                    gc.drawImage(snakeHeads[2],tempPos.getX() * GameSettings.snakeSize + canvasOffset, tempPos.getY() * GameSettings.snakeSize);
                    break;
            }

            for (int i = 1; i < snakes.get(player).getPositions().size(); i++) {
                tempPos = snakes.get(player).getPositions().get(i);
                gc.drawImage(snakeBody,tempPos.getX() * GameSettings.snakeSize + canvasOffset, tempPos.getY() * GameSettings.snakeSize);
            }
        }

    }

    private void renderObjects(){
        for(int i = 0; i < objects.size(); i++){
            switch (objects.get(i).getType()) {
                case WALL:
                    if(GameSettings.walls){
                        gc.drawImage(wallbrick,objects.get(i).getPosition().getX() * GameSettings.snakeSize + canvasOffset, objects.get(i).getPosition().getY() * GameSettings.snakeSize);
                    }
                    break;
                case FOOD:
                    gc.drawImage(apple,objects.get(i).getPosition().getX() * GameSettings.snakeSize +canvasOffset, objects.get(i).getPosition().getY() * GameSettings.snakeSize );
                    break;
                case POWERUP_INVINCIBLE:
                    gc.drawImage(invinciblePowerUp,objects.get(i).getPosition().getX() * GameSettings.snakeSize + canvasOffset, objects.get(i).getPosition().getY() * GameSettings.snakeSize);
                    break;
                case POWERUP_NOWALLS:
                    gc.drawImage(noWallsPowerUp,objects.get(i).getPosition().getX() * GameSettings.snakeSize + canvasOffset, objects.get(i).getPosition().getY() * GameSettings.snakeSize);
                    break;
                case POWERUP_SLOW:
                    gc.drawImage(slowMotionPowerUp,objects.get(i).getPosition().getX() * GameSettings.snakeSize + canvasOffset, objects.get(i).getPosition().getY() * GameSettings.snakeSize);
            }

        }
    }

    private void renderIngameUI(){
        Image image250 = new Image("sample/images/250.png");
        Image image500 = new Image("sample/images/500.png");
        Image image625 = new Image("sample/images/625.png");
        Image image875 = new Image("sample/images/875.png");


        if(GameSettings.multiplayer){
            switch (GameSettings.gridHeight){
                case 10:
                    gc.drawImage(image250, 0, 0);
                    gc.drawImage(image250, GameSettings.gridWidth * GameSettings.snakeSize + 150, 0);
                    break;
                case 20:
                    gc.drawImage(image500, 0, 0);
                    gc.drawImage(image500, GameSettings.gridWidth * GameSettings.snakeSize + 150, 0);
                    break;
                case 25:
                    gc.drawImage(image625, 0, 0);
                    gc.drawImage(image625, GameSettings.gridWidth * GameSettings.snakeSize + 150, 0);
                    break;
                case 35:
                    gc.drawImage(image875, 0, 0);
                    gc.drawImage(image875, GameSettings.gridWidth * GameSettings.snakeSize + 150, 0);
                    break;
                default:
                    break;
            }
        }else {
            switch (GameSettings.gridHeight){
                case 10:
                    gc.drawImage(image250, GameSettings.gridWidth * GameSettings.snakeSize, 0);
                    break;
                case 20:
                    gc.drawImage(image500, GameSettings.gridWidth * GameSettings.snakeSize, 0);
                    break;
                case 25:
                    gc.drawImage(image625, GameSettings.gridWidth * GameSettings.snakeSize, 0);
                    break;
                case 35:
                    gc.drawImage(image875, GameSettings.gridWidth * GameSettings.snakeSize, 0);
                    break;
                default:
                    break;
            }
        }


        gc.setFill(Color.BLACK);
        Font font = Font.loadFont(getClass().getResourceAsStream("kenvector_future_thin.ttf"), 25.0);
        if(!GameSettings.multiplayer) {
            gc.setFont(font);
            gc.fillText("Player 1", GameSettings.gridWidth * GameSettings.snakeSize + 10, 30);
        }else {
            gc.setFont(font);
            gc.fillText("Player 2", GameSettings.gridWidth * GameSettings.snakeSize +150 + 10 , 30);
            gc.fillText("Player 1", 10,30);
        }

       //Single Player Only
        if(!GameSettings.multiplayer) {
            ArrayList<PowerUp> tempPowerUPList = new ArrayList<PowerUp>();
            tempPowerUPList.addAll(snakes.get(0).getActicePowerUps());
            tempPowerUPList.addAll(activePowerUps);
            for (int i = 0; i < tempPowerUPList.size(); i++) {
                switch (tempPowerUPList.get(i).getType()) {
                    case POWERUP_INVINCIBLE:
                        gc.drawImage(invinciblePowerUp, GameSettings.gridWidth * GameSettings.snakeSize + 15, 60 + 30 * i);
                        gc.fillText(String.valueOf(tempPowerUPList.get(i).getEndTime() - System.currentTimeMillis()), GameSettings.gridWidth * GameSettings.snakeSize + 45, 60 + 30 * i + 20);
                        break;
                    case POWERUP_NOWALLS:
                        gc.drawImage(noWallsPowerUp, GameSettings.gridWidth * GameSettings.snakeSize + 15, 60 + 30 * i);
                        gc.fillText(String.valueOf(tempPowerUPList.get(i).getEndTime() - System.currentTimeMillis()), GameSettings.gridWidth * GameSettings.snakeSize + 45, 60 + 30 * i + 20);
                        break;
                    case POWERUP_SLOW:
                        gc.drawImage(slowMotionPowerUp, GameSettings.gridWidth * GameSettings.snakeSize + 15, 60 + 30 * i);
                        gc.fillText(String.valueOf(tempPowerUPList.get(i).getEndTime() - System.currentTimeMillis()), GameSettings.gridWidth * GameSettings.snakeSize + 45, 60 + 30 * i + 20);
                }
            }
        }

        if(GameSettings.multiplayer){

            //Player oneOnly
            ArrayList<PowerUp> tempPowerUPList1 = new ArrayList<PowerUp>();
            tempPowerUPList1.addAll(snakes.get(0).getActicePowerUps());
            tempPowerUPList1.addAll(activePowerUps);
            for(int i = 0;i < tempPowerUPList1.size(); i++) {
                switch (tempPowerUPList1.get(i).getType()) {
                    case POWERUP_INVINCIBLE:
                        gc.drawImage(invinciblePowerUp,   15, 60 + 30 * i);
                        gc.fillText(String.valueOf(tempPowerUPList1.get(i).getEndTime() - System.currentTimeMillis()),  45, 60 + 30 * i + 20);
                        break;
                    case POWERUP_NOWALLS:
                        gc.drawImage(noWallsPowerUp,  15, 60 + 30 * i);
                        gc.fillText(String.valueOf(tempPowerUPList1.get(i).getEndTime() - System.currentTimeMillis()), 45, 60 + 30 * i + 20);
                        break;
                    case POWERUP_SLOW:
                        gc.drawImage(slowMotionPowerUp,  + 15, 60 + 30 * i);
                        gc.fillText(String.valueOf(tempPowerUPList1.get(i).getEndTime() - System.currentTimeMillis()),  45, 60 + 30 * i + 20);
                }
            }

            ArrayList<PowerUp> tempPowerUPList2 = new ArrayList<PowerUp>();
            tempPowerUPList2.addAll(snakes.get(1).getActicePowerUps());
            tempPowerUPList2.addAll(activePowerUps);
            for(int i = 0;i < tempPowerUPList2.size(); i++){
                switch (tempPowerUPList2.get(i).getType()) {
                    case POWERUP_INVINCIBLE:
                        gc.drawImage(invinciblePowerUp,GameSettings.gridWidth * GameSettings.snakeSize + 150 + 15 ,60 + 30 *i);
                        gc.fillText(String.valueOf(tempPowerUPList2.get(i).getEndTime() -System.currentTimeMillis()), GameSettings.gridWidth * GameSettings.snakeSize + 150 + 45, 60+ 30*i + 20);
                        break;
                    case POWERUP_NOWALLS:
                        gc.drawImage(noWallsPowerUp,GameSettings.gridWidth * GameSettings.snakeSize + 150 + 15 ,60 + 30 *i);
                        gc.fillText(String.valueOf(tempPowerUPList2.get(i).getEndTime() -System.currentTimeMillis()), GameSettings.gridWidth * GameSettings.snakeSize + 150 + 45, 60+ 30*i + 20);
                        break;
                    case POWERUP_SLOW:
                        gc.drawImage(slowMotionPowerUp,GameSettings.gridWidth * GameSettings.snakeSize + 150 + 15,60 + 30 *i);
                        gc.fillText(String.valueOf(tempPowerUPList2.get(i).getEndTime() - System.currentTimeMillis()), GameSettings.gridWidth * GameSettings.snakeSize + 150 + 45, 60+ 30*i + 20);
                }
            }
        }
    }

    private void detectCollisons(){
        boolean player1isDead = false;
        boolean player2isDead = false;

        for(int player = 0; player < snakes.size(); player++) {
            Position headPos = snakes.get(player).getPositions().get(0);
            for (int i = 0; i < objects.size(); i++) {
                switch (objects.get(i).getType()) {
                    case FOOD:
                        if (objects.get(i).getPosition().getX() == headPos.getX() && objects.get(i).getPosition().getY() == headPos.getY()) {
                            snakes.get(player).extend();
                            objects.remove(i);
                            spawnFood();
                            soundfx.picked_apple();

                        }
                        break;
                    case WALL:
                        if (objects.get(i).getPosition().getX() == headPos.getX() && objects.get(i).getPosition().getY() == headPos.getY() && GameSettings.walls) {
                            looserSnake = player;
                            if(player == 0){
                                player1isDead = true;
                            }else {
                                player2isDead = true;
                            }

                        }

                        break;
                    case POWERUP_INVINCIBLE:
                        if (objects.get(i).getPosition().getX() == headPos.getX() && objects.get(i).getPosition().getY() == headPos.getY()) {
                            snakes.get(player).getActicePowerUps().add(new PowerUp(ObjectType.POWERUP_INVINCIBLE, System.currentTimeMillis() + 5000));
                            System.out.println("Added activePowerUp");
                            objects.remove(i);
                            soundfx.picked_invincible();
                        }
                        break;
                    case POWERUP_NOWALLS:
                        if (objects.get(i).getPosition().getX() == headPos.getX() && objects.get(i).getPosition().getY() == headPos.getY()) {
                            activePowerUps.add(new PowerUp(ObjectType.POWERUP_NOWALLS, System.currentTimeMillis() + 5000));
                            System.out.println("Added activePowerUp");
                            objects.remove(i);
                            soundfx.picked_nowalls();
                        }
                        break;
                    case POWERUP_SLOW:
                        if (objects.get(i).getPosition().getX() == headPos.getX() && objects.get(i).getPosition().getY() == headPos.getY()) {
                            activePowerUps.add(new PowerUp(ObjectType.POWERUP_SLOW, System.currentTimeMillis() + 5000));
                            System.out.println("Added activePowerUp");
                            objects.remove(i);
                            soundfx.picked_slowmotion();
                        }
                        break;
                    case SNAKE:
                        //This should never happen
                        break;
                }
            }

            //Handle self collision
            for (int i = 1; i < snakes.get(player).getPositions().size(); i++) {
                if (snakes.get(player).getPositions().get(i).getX() == headPos.getX() && snakes.get(player).getPositions().get(i).getY() == headPos.getY() && !snakes.get(player).isInvincible()) {
                    if(player == 0){
                        player1isDead = true;
                    }else {
                        player2isDead = true;
                    }
                }
            }

            //Handle other snake collisions
            for(int otherSnakes = 0; otherSnakes < snakes.size(); otherSnakes++) {
                if(otherSnakes != player) {
                    for (int i = 0; i < snakes.get(otherSnakes).getPositions().size(); i++) {
                        if (snakes.get(otherSnakes).getPositions().get(i).getX() == headPos.getX() && snakes.get(otherSnakes).getPositions().get(i).getY() == headPos.getY() && !snakes.get(player).isInvincible()){
                            if(player == 0){
                                player1isDead = true;
                            }else
                                player2isDead = true;
                        }
                    }
                }
            }
        }
        if(GameSettings.multiplayer) {
            if (player1isDead && !player2isDead) {
                looserSnake = 0;
                gameOver();
            } else if (player2isDead && !player1isDead) {
                looserSnake = 1;
                gameOver();
            } else if(player1isDead && player2isDead){
                looserSnake = 2;
                gameOver();
            }
        }else {
            if(player1isDead){
                looserSnake = 0;
                gameOver();
            }
        }
    }

    private void initWall(){
        for(int x = 0; x< GameSettings.gridWidth; x++){
            objects.add(new Object(new Position(x,0,Direction.DOWN),ObjectType.WALL));
            objects.add(new Object(new Position(x,GameSettings.gridHeight-1,Direction.DOWN),ObjectType.WALL));
        }
        for(int y = 0; y< GameSettings.gridWidth; y++){
            objects.add(new Object(new Position(0,y,Direction.DOWN),ObjectType.WALL));
            objects.add(new Object(new Position(GameSettings.gridWidth-1,y,Direction.DOWN),ObjectType.WALL));
        }
    }

    private void spawnRandomPowerUP(){
        int t = getRandomNumberInRange(1,3);
        ObjectType type = null;
        switch (t){
            case 1: type = ObjectType.POWERUP_INVINCIBLE;
            break;
            case 2: type = ObjectType.POWERUP_NOWALLS;
            break;
            case 3: type = ObjectType.POWERUP_SLOW;
        }

        boolean isSpawned = false;
        Position tempPos = null;
        while (!isSpawned) {
            isSpawned = true;
            tempPos = new Position(getRandomNumberInRange(1, GameSettings.gridWidth - 2), getRandomNumberInRange(1, GameSettings.gridHeight - 2), Direction.DOWN);
            for(int z = 0; z < snakes.size(); z++) {
                for (int i = 0; i < snakes.get(z).getPositions().size(); i++) {
                    if (tempPos.getX() == snakes.get(z).getPositions().get(i).getX() && tempPos.getY() == snakes.get(z).getPositions().get(i).getY()) {
                        isSpawned = false;
                    }
                }
            }
        }
        objects.add(new Object(tempPos,type));


    }

    private void gameOver(){
        snakeIsAlive = false;
        soundfx.quitGame();

        main.endGame(looserSnake);


    }

    public void restartGame(){
        snakes = null;
        objects = null;
        initGame();
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public ArrayList<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }
}
