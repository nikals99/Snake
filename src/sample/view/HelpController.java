package sample.view;

import javafx.fxml.FXML;
import sample.Main;

/**
 * Created by niklas.fassbender on 06.01.2017.
 */
public class HelpController {

    Main main;

    @FXML
    public void handleClose(){
        main.closeHelpDialog();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
