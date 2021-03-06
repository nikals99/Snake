package snake.view;

import javafx.fxml.FXML;
import snake.Main;
import snake.util.Soundfx;

/**
 * Created by niklas.fassbender on 06.01.2017.
 */
public class HelpController {

    Main main;

    @FXML
    public void handleClose(){
        main.closeHelpDialog();
        Soundfx.quitGame();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
