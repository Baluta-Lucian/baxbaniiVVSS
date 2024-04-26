package tasks.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tasks.persistence.ArrayTaskList;
import tasks.model.Task;
import tasks.utils.TasksOperations;
import tasks.validator.TaskValidator;

import java.text.ParseException;
import java.util.Date;


public class TasksService {

    private ArrayTaskList tasks;
    private TaskValidator taskValidator;

    public TasksService(ArrayTaskList tasks, TaskValidator taskValidator){
        this.tasks = tasks;
        this.taskValidator=taskValidator;
    }

    public ArrayTaskList getTasks() {
        return tasks;
    }

    public TaskValidator getTaskValidator() {
        return taskValidator;
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
//00:00 // len(stringTime 01, 34 0-9 0 0 1 2, 1: 0-9, 0-4, 34- 0-5, 0-9
    // 0: 0-2
    // 1: -case [0] = 0,1 -> 0-9
    //    -case [0] = 2 -> 0-4
    //2: :
    //3: 0-5
    //4: 0-9
    public int parseFromStringToSeconds(String stringTime) throws ParseException {//hh:MM
        String[] units = stringTime.split(":");
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        int result = (hours * DateService.MINUTES_IN_HOUR + minutes) * DateService.SECONDS_IN_MINUTE;
        if(result > 86400)
            throw new ParseException("Out of range!", 12);
        if(result < 0)
            throw new ParseException("Out of range!", 11);
        return result;
    }

    public Iterable<Task> filterTasks(Date start, Date end){
        TasksOperations tasksOps = new TasksOperations(getObservableList());
        Iterable<Task> filtered = tasksOps.incoming(start,end);
        //Iterable<Task> filtered = tasks.incoming(start, end);

        return filtered;
    }
}
