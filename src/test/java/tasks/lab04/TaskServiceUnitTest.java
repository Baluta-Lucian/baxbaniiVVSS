package tasks.lab04;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tasks.model.Task;
import tasks.persistence.ArrayTaskList;
import tasks.services.TasksService;
import tasks.validator.TaskValidator;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

public class TaskServiceUnitTest {
    @Mock
    ArrayTaskList arrayTaskList;

    @Mock
    TaskValidator taskValidator;

    @InjectMocks
    private TasksService tasksService;

    private Task validTask;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        String title = "One time task1";
        Date date = new Date(2024, 04, 04);
        boolean isActive = true;

        validTask = new Task(title, date, isActive);
    }


    @Mock
    Task task1;

    //add task valid
    @Test
    public void test1() {
        String title = "One time task1";
        Date date = new Date(2024, 04, 04);
        boolean isActive = true;

        Task validTaskToAdd = spy(validTask);
        Mockito.doNothing().when(taskValidator).validate(title, date, isActive);
        Mockito.doNothing().when(arrayTaskList).add(validTaskToAdd);

        Task result = tasksService.addOneTimeTask(title, date, isActive);
        assert result.equals(validTaskToAdd);
    }

    //add task invalid
    @Test
    public void test2() {
        String title = "One time task1";
        Date start = new Date(2024, 04, 04);
        Date end = new Date(2024, 04, 10);
        int interval =-1;
        boolean isActive = true;

        Mockito.doThrow(IllegalArgumentException.class).when(taskValidator).validate(title, start, end, interval, isActive);
        assertThrows(
                IllegalArgumentException.class,
                () -> tasksService.addRepetitiveTask(title, start, end , interval, isActive),
                "interval should be > 1"
        );
    }
}
