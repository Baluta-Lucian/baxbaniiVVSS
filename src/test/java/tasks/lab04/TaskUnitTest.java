package tasks.lab04;

import org.junit.jupiter.api.Test;
import tasks.model.Task;

import java.util.Date;

public class TaskUnitTest {
    @Test
    public void test1() {
        Task task =  new Task("task test getter", new Date(2024,4,4), new Date(2024, 4, 10), 3600, true);
        Date dateTest1= new Date(2024,4,4);
        Date dateTest2= new Date(2024,4,10);

        assert task.getTitle().equals("task test getter");
        assert task.getEndTime().equals(dateTest2);
        assert task.getStartTime().equals(dateTest1);
        assert task.isActive();
        assert task.isRepeated();
    }

    @Test
    public void test2() {
        Task task =  new Task("task test getter", new Date(2024,4,4), new Date(2024, 4, 10), 3600, true);
        String taskToString = "Task{title='task test getter', time=Sun May 04 00:00:00 EEST 3924, start=Sun May 04 00:00:00 EEST 3924, end=Sat May 10 00:00:00 EEST 3924, interval=3600, active=true}";
        assert task.toString().equals(taskToString);

    }

}
