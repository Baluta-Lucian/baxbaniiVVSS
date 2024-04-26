package tasks.lab04;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.model.Task;
import tasks.persistence.ArrayTaskList;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;

public class ArrayTaskListUnitTest {
    private Task task;


    @BeforeEach
    public void setUp() {
        task = new Task("task test getter", new Date(2024,4,4), new Date(2024, 4, 10), 3600, true);
    }

    @Test
    public void test1() {
        ArrayTaskList arrayTaskList =  new ArrayTaskList();
        Task task1 = spy(task);
        System.out.println(task1);
        int prev_size = arrayTaskList.size();
        arrayTaskList.add(task1);
        assert arrayTaskList.size() == prev_size+1;
    }

    @Test
    public void test2() {
        ArrayTaskList arrayTaskList =  new ArrayTaskList();
        assertThrows(
                NullPointerException.class,
                () -> arrayTaskList.add(null),
                "Task shouldn't be null"
        );
    }

}
