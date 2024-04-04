package tasks.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import tasks.model.Task;
import tasks.services.DateService;
import tasks.persistence.TaskIO;
import tasks.services.TasksService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class NewEditController {

    private static Button clickedButton;

    private static final Logger log = Logger.getLogger(NewEditController.class.getName());

    public static void setClickedButton(Button clickedButton) {
        NewEditController.clickedButton = clickedButton;
    }

    public static void setCurrentStage(Stage currentStage) {
        NewEditController.currentStage = currentStage;
    }

    private static Stage currentStage;

    private Task currentTask;
    private ObservableList<Task> tasksList;
    private TasksService service;
    private DateService dateService;
    private boolean addModeOn = false;


    private boolean incorrectInputMade;
    @FXML
    private TextField fieldTitle;
    @FXML
    private DatePicker datePickerStart;
    @FXML
    private TextField txtFieldTimeStart;
    @FXML
    private DatePicker datePickerEnd;
    @FXML
    private TextField txtFieldTimeEnd;
    @FXML
    private TextField fieldInterval;
    @FXML
    private CheckBox checkBoxActive;
    @FXML
    private CheckBox checkBoxRepeated;

    private static final String DEFAULT_START_TIME = "8:00";
    private static final String DEFAULT_END_TIME = "10:00";
    private static final String DEFAULT_INTERVAL_TIME = "0:30";

    public void setTasksList(ObservableList<Task> tasksList){
        this.tasksList =tasksList;
    }

    public void setService(TasksService service){
        this.service =service;
        this.dateService =new DateService();
    }
    public void setCurrentTask(Task task){
        this.currentTask=task;
        switch (clickedButton.getId()){
            case  "btnNew" : initNewWindow("New Task");
                break;
            case "btnEdit" : initEditWindow("Edit Task");
                break;
        }
    }

    @FXML
    public void initialize(){
        log.info("new/edit window initializing");
//        switch (clickedButton.getId()){
//            case  "btnNew" : initNewWindow("New Task");
//                break;
//            case "btnEdit" : initEditWindow("Edit Task");
//                break;
//        }

    }
    private void initNewWindow(String title){
        this.addModeOn = true;
        currentStage.setTitle(title);
        datePickerStart.setValue(LocalDate.now());
        txtFieldTimeStart.setText(DEFAULT_START_TIME);
    }

    private void initEditWindow(String title){
        this.addModeOn = false;
        currentStage.setTitle(title);
        fieldTitle.setText(currentTask.getTitle());
        datePickerStart.setValue(dateService.getLocalDateValueFromDate(currentTask.getStartTime()));
        txtFieldTimeStart.setText(dateService.getTimeOfTheDayFromDate(currentTask.getStartTime()));

        if (currentTask.isRepeated()){
            checkBoxRepeated.setSelected(true);
            hideRepeatedTaskModule(false);
            datePickerEnd.setValue(dateService.getLocalDateValueFromDate(currentTask.getEndTime()));
            fieldInterval.setText(dateService.getIntervalInHours(currentTask));
            txtFieldTimeEnd.setText(dateService.getTimeOfTheDayFromDate(currentTask.getEndTime()));
        }
        if (currentTask.isActive()){
            checkBoxActive.setSelected(true);

        }
    }
    @FXML
    public void switchRepeatedCheckbox(ActionEvent actionEvent){
        CheckBox source = (CheckBox)actionEvent.getSource();
        if (source.isSelected()){
            hideRepeatedTaskModule(false);
        }
        else if (!source.isSelected()){
            hideRepeatedTaskModule(true);
        }
    }
    private void hideRepeatedTaskModule(boolean toShow){
        datePickerEnd.setDisable(toShow);
        fieldInterval.setDisable(toShow);
        txtFieldTimeEnd.setDisable(toShow);

        datePickerEnd.setValue(LocalDate.now());
        txtFieldTimeEnd.setText(DEFAULT_END_TIME);
        fieldInterval.setText(DEFAULT_INTERVAL_TIME);
    }

    private void showErrorWindow(String errorMessage) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/field-validator.fxml"));
            Parent root = loader.load();//getClass().getResource("/fxml/new-edit-task.fxml"));

            ErrorController errorController = loader.getController();
            errorController.setErrorMessage(errorMessage);

            stage.setScene(new Scene(root, 350, 150));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch (IOException ioe){
            log.error("error loading field-validator.fxml");
        }
    }

    @FXML
    public void saveChanges(){
        Task savedTask = null;

        boolean isActive = checkBoxActive.isSelected();
        String title = fieldTitle.getText();
        Date startDateWithNoTime = dateService.getDateValueFromLocalDate(datePickerStart.getValue());//ONLY date!!without time

        Date newStartDate=null;
        Date newEndDate=null;

        try {
            newStartDate = dateService.getDateMergedWithTime(txtFieldTimeStart.getText(), startDateWithNoTime);
        } catch (IllegalArgumentException ex) {
            showErrorWindow(ex.getMessage());
            return;
        }

        if (checkBoxRepeated.isSelected()){
            Date endDateWithNoTime = dateService.getDateValueFromLocalDate(datePickerEnd.getValue());
            try {
                newEndDate = dateService.getDateMergedWithTime(txtFieldTimeEnd.getText(), endDateWithNoTime);
            } catch (IllegalArgumentException ex) {
                showErrorWindow(ex.getMessage());
                return;
            }
            int newInterval = service.parseFromStringToSeconds(fieldInterval.getText());
            if (newStartDate.after(newEndDate))  {
                showErrorWindow("Start date should be before end");
                incorrectInputMade=true;
            }

            try {
                savedTask = service.addRepetitiveTask(title, newStartDate, newEndDate, newInterval, isActive);
            } catch (IllegalArgumentException ex) {
                showErrorWindow(ex.getMessage());
                incorrectInputMade=true;
            }
        }
        else {
            try {
                savedTask = service.addOneTimeTask(title, newStartDate, isActive);
            } catch (IllegalArgumentException ex) {
                showErrorWindow(ex.getMessage());
                incorrectInputMade=true;
            }
        }

        if (incorrectInputMade) return;

        if (addModeOn){
            tasksList.add(savedTask);
            System.out.println("add task: "+savedTask);
        }
        else {
            for (int i = 0; i < tasksList.size(); i++){
                if (currentTask.equals(tasksList.get(i))){
                    tasksList.set(i,savedTask);
                }
            }
            currentTask = null;
        }
        TaskIO.rewriteFile(tasksList);
        Controller.editNewStage.close();
    }
    @FXML
    public void closeDialogWindow(){
        Controller.editNewStage.close();
    }
}
