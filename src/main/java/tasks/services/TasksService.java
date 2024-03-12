package tasks.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tasks.persistence.ArrayTaskList;
import tasks.model.Task;
import tasks.utils.TasksOperations;
import tasks.validator.TaskValidator;

import java.util.Date;


public class TasksService {

    private ArrayTaskList tasks;
    private TaskValidator taskValidator;

    public TasksService(ArrayTaskList tasks, TaskValidator taskValidator){
        this.tasks = tasks;
        this.taskValidator=taskValidator;
    }

    public ObservableList<Task> getObservableList(){
        return FXCollections.observableArrayList(tasks.getAll());
    }

    public Task addRepetitiveTask(String title, Date start, Date end, int interval, boolean isActive) {
        taskValidator.validate(title, start, end, interval, isActive);
        Task task = new Task(title, start, end, interval, isActive);
        tasks.add(task);
        return task;
    }

    public Task addOneTimeTask(String title, Date time, boolean isActive) {
        taskValidator.validate(title, time, isActive);
        Task task = new Task(title, time, isActive);
        tasks.add(task);
        return task;
    }

    public int parseFromStringToSeconds(String stringTime){//hh:MM
        String[] units = stringTime.split(":");
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        int result = (hours * DateService.MINUTES_IN_HOUR + minutes) * DateService.SECONDS_IN_MINUTE;
        return result;
    }

    public Iterable<Task> filterTasks(Date start, Date end){
        TasksOperations tasksOps = new TasksOperations(getObservableList());
        Iterable<Task> filtered = tasksOps.incoming(start,end);
        //Iterable<Task> filtered = tasks.incoming(start, end);

        return filtered;
    }
}
