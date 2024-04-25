package tasks.lab04;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import tasks.model.Task;
import tasks.persistence.ArrayTaskList;
import tasks.services.TasksService;
import tasks.validator.TaskValidator;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;

public class Step2IntegrationTests {
    private ArrayTaskList arrayTaskList = new ArrayTaskList();
    private TaskValidator taskValidator =  new TaskValidator();

    private TasksService tasksService = new TasksService(arrayTaskList, taskValidator);

    private Task validTask;

    @BeforeEach
    public void setUp(){

        String title = "One time task1";
        Date date = new Date(2024, 04, 04);
        boolean isActive = true;
        validTask = new Task(title, date, isActive);
    }

    //ad valid task
    @Test
    public void test1() {
        String title = "One time task1";
        Date date = new Date(2024, 04, 04);
        boolean isActive = true;

//        Mockito.when(validTask.getTitle()).thenReturn(title);
//        Mockito.when(validTask.getTime()).thenReturn(date);
//        Mockito.when(validTask.isActive()).thenReturn(isActive);
//
//        Task addResult = tasksService.addOneTimeTask(title, date, isActive);
//        assert addResult.getTitle().equals(validTask.getTitle());
//        assert addResult.getTime().equals(validTask.getTime());
//        assert addResult.isActive()==validTask.isActive();

        Task addResult = tasksService.addOneTimeTask(title, date, isActive);
        Task validTaskAdded = spy(validTask);
        assert addResult.getTitle().equals(validTaskAdded.getTitle());
        assert addResult.getTime().equals(validTaskAdded.getTime());
        assert addResult.isActive()==validTaskAdded.isActive();
    }

    @Test
    public void test2() {
        String title = "One time task1";
        Date start = new Date(2024, 04, 04);
        Date end = new Date(2024, 04, 10);
        int interval =-1;
        boolean isActive = true;

        assertThrows(
                IllegalArgumentException.class,
                () -> tasksService.addRepetitiveTask(title, start, end , interval, isActive),
                "interval should be > 1"
        );
    }
}
