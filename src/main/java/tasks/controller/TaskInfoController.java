package tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tasks.model.Task;

import org.apache.log4j.Logger;

public class TaskInfoController {
    private static final Logger log = Logger.getLogger(TaskInfoController.class.getName());
    @FXML
    private Label labelTitle;
    @FXML
    private Label labelStart;
    @FXML
    private Label labelEnd;
    @FXML
    private Label labelInterval;
    @FXML
    private Label labelIsActive;

    private Task currentTask;

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
        initializeLabels();

    }

    private void initializeLabels() {
        labelTitle.setText("Title: " + currentTask.getTitle());
        labelStart.setText("Start time: " + currentTask.getFormattedDateStart());
        labelEnd.setText("End time: " + currentTask.getFormattedDateEnd());
        labelInterval.setText("Interval: " + currentTask.getFormattedRepeated());
        labelIsActive.setText("Is active: " + (currentTask.isActive() ? "Yes" : "No"));
    }

    @FXML
    public void initialize(){
        log.info("task info window initializing");
    }
    @FXML
    public void closeWindow(){
        Controller.infoStage.close();
    }

}

