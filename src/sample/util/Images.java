package sample.util;

import javafx.scene.image.Image;

/**
 * Created by raza.ahmed on 06.01.2017.
 */
public class Images {

    public static Image  wallbrick = new Image("sample/images/wallbrick.png");
    public static Image   slowMotionPowerUp = new Image("sample/images/slowmotion_powerup.png");
    public static Image noWallsPowerUp = new Image("sample/images/nowalls_powerup.png");
    public static Image  invinciblePowerUp = new Image("sample/images/invincible_powerup.png");
    public static Image  bodypartGreen = new Image("sample/images/bodypart_green.png");
    public static Image  bodyPartBlue = new Image("sample/images/bodypart_blue.png");

    public static Image[]  headsGreen = new Image[4];
    public static Image[] headsBlue = new Image[4];

    public static Image apple = new Image("sample/images/apple.png");
    public static void initArrays(){
        headsGreen[0] = new Image("sample/images/head_green_up.png");
        headsGreen[1] = new Image("sample/images/head_green_down.png");
        headsGreen[2] = new Image("sample/images/head_green_left.png");
        headsGreen[3] = new Image("sample/images/head_green_right.png");


        headsBlue[0] = new Image("sample/images/head_blue_up.png");
        headsBlue[1] = new Image("sample/images/head_blue_down.png");
        headsBlue[2] = new Image("sample/images/head_blue_left.png");
        headsBlue[3] = new Image("sample/images/head_blue_right.png");


    }
}
