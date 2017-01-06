package snake.util;

import javafx.scene.image.Image;

/**
 * Created by raza.ahmed on 06.01.2017.
 */
public class Images {

    public static Image  wallbrick = new Image("snake/images/wallbrick.png");
    public static Image   slowMotionPowerUp = new Image("snake/images/slowmotion_powerup.png");
    public static Image noWallsPowerUp = new Image("snake/images/nowalls_powerup.png");
    public static Image  invinciblePowerUp = new Image("snake/images/invincible_powerup.png");
    public static Image  bodypartGreen = new Image("snake/images/bodypart_green.png");
    public static Image  bodyPartBlue = new Image("snake/images/bodypart_blue.png");

    public static Image[]  headsGreen = new Image[4];
    public static Image[] headsBlue = new Image[4];

    public static Image apple = new Image("snake/images/apple.png");
    public static void initArrays(){
        headsGreen[0] = new Image("snake/images/head_green_up.png");
        headsGreen[1] = new Image("snake/images/head_green_down.png");
        headsGreen[2] = new Image("snake/images/head_green_left.png");
        headsGreen[3] = new Image("snake/images/head_green_right.png");


        headsBlue[0] = new Image("snake/images/head_blue_up.png");
        headsBlue[1] = new Image("snake/images/head_blue_down.png");
        headsBlue[2] = new Image("snake/images/head_blue_left.png");
        headsBlue[3] = new Image("snake/images/head_blue_right.png");


    }
}
