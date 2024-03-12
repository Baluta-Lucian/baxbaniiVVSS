package tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;
import tasks.model.Task;

public class ErrorController {
    private static final Logger log = Logger.getLogger(ErrorController.class.getName());
    @FXML
    private Label errorLabel;

    public void setErrorMessage(String errorMessage) {
        this.errorLabel.setText(errorMessage);

    }

    @FXML
    public void initialize(){
        log.info("error info window initializing");
    }
}
