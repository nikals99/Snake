package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import sample.models.*;
import sample.models.Object;

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

    Image wallbrick;
    Image slowMotionPowerUp;
    Image noWallsPowerUp;
    Image invinciblePowerUp;

    Image bodypartGreen;

    Image bodyPartBlue;

    Image[] headsBlue;
    Image[] headsGreen;

    ArrayList<PowerUp> activePowerUps;

    int looserSnake;

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
        for (int i = 0; i < activePowerUps.size(); i++) {
            if (activePowerUps.get(i).getEndTime() < System.currentTimeMillis()) {
                switch (activePowerUps.get(i).getType()) {
                    case POWERUP_NOWALLS:
                        GameSettings.walls = true;
                        break;
                    case POWERUP_SLOW:
                        GameSettings.speed = 100;
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
            for(int i = 0; i <snakes.get(player).getActicePowerUps().size(); i++){
                if (snakes.get(player).getActicePowerUps().get(i).getEndTime() < System.currentTimeMillis()) {
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
            main.endGame(-1);
        }
    }


    private void renderScene(){
        gc.clearRect(0, 0, GameSettings.gridWidth * GameSettings.snakeSize, GameSettings.gridHeight * GameSettings.snakeSize);
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
                    gc.drawImage(snakeHeads[1],tempPos.getX() * GameSettings.snakeSize, tempPos.getY() * GameSettings.snakeSize);
                    break;
                case UP:
                    gc.drawImage(snakeHeads[0],tempPos.getX() * GameSettings.snakeSize, tempPos.getY() * GameSettings.snakeSize);
                    break;
                case RIGHT:
                    gc.drawImage(snakeHeads[3],tempPos.getX() * GameSettings.snakeSize, tempPos.getY() * GameSettings.snakeSize);
                    break;
                case LEFT:
                    gc.drawImage(snakeHeads[2],tempPos.getX() * GameSettings.snakeSize, tempPos.getY() * GameSettings.snakeSize);
                    break;
            }

            for (int i = 1; i < snakes.get(player).getPositions().size(); i++) {
                tempPos = snakes.get(player).getPositions().get(i);
                gc.drawImage(snakeBody,tempPos.getX() * GameSettings.snakeSize, tempPos.getY() * GameSettings.snakeSize);


            }
        }

        for(int i = 0; i < objects.size(); i++){
            switch (objects.get(i).getType()) {
                case WALL:
                    if(GameSettings.walls){
                    gc.drawImage(wallbrick,objects.get(i).getPosition().getX() * GameSettings.snakeSize, objects.get(i).getPosition().getY() * GameSettings.snakeSize);
                    }else{
                        gc.setFill(Color.TRANSPARENT);
                    }
                    break;
                case FOOD:
                    gc.setFill(Color.RED);
                    gc.fillRect(objects.get(i).getPosition().getX() * GameSettings.snakeSize, objects.get(i).getPosition().getY() * GameSettings.snakeSize, GameSettings.snakeSize, GameSettings.snakeSize);
                    break;
                case POWERUP_INVINCIBLE:
                    gc.drawImage(invinciblePowerUp,objects.get(i).getPosition().getX() * GameSettings.snakeSize, objects.get(i).getPosition().getY() * GameSettings.snakeSize);
                    break;
                case POWERUP_NOWALLS:
                    gc.drawImage(noWallsPowerUp,objects.get(i).getPosition().getX() * GameSettings.snakeSize, objects.get(i).getPosition().getY() * GameSettings.snakeSize);
                    break;
                case POWERUP_SLOW:
                    gc.drawImage(slowMotionPowerUp,objects.get(i).getPosition().getX() * GameSettings.snakeSize, objects.get(i).getPosition().getY() * GameSettings.snakeSize);
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
                        }
                        break;
                    case POWERUP_NOWALLS:
                        if (objects.get(i).getPosition().getX() == headPos.getX() && objects.get(i).getPosition().getY() == headPos.getY()) {
                            activePowerUps.add(new PowerUp(ObjectType.POWERUP_NOWALLS, System.currentTimeMillis() + 5000));
                            System.out.println("Added activePowerUp");
                            objects.remove(i);
                        }
                        break;
                    case POWERUP_SLOW:
                        if (objects.get(i).getPosition().getX() == headPos.getX() && objects.get(i).getPosition().getY() == headPos.getY()) {
                            activePowerUps.add(new PowerUp(ObjectType.POWERUP_SLOW, System.currentTimeMillis() + 5000));
                            System.out.println("Added activePowerUp");
                            objects.remove(i);
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
                looserSnake = 3;
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


        main.endGame(looserSnake);


    }

    public void restartGame(){
        snakes = null;
        objects = null;
        initGame();
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setMain(Main main) {
        this.main = main;
    }


    public ArrayList<Snake> getSnakes() {
        return snakes;
    }
}
