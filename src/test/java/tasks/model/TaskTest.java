package tasks.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.persistence.ArrayTaskList;
import tasks.services.TasksService;
import tasks.validator.TaskValidator;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;

    private int interval1;

    private ArrayTaskList arrayTaskList = new ArrayTaskList();
    private TaskValidator taskValidator = new TaskValidator();
    private TasksService tasksService = new TasksService(arrayTaskList, taskValidator);


    @BeforeEach
    void setUp() throws ParseException {
        String stringTime = "02:00";
        interval1 = tasksService.parseFromStringToSeconds(stringTime);
    }


    @Test
    void addRepetitiveTask_Valid_BVA() throws ParseException {
        assert interval1 == 7200;
    }

    @Test
    void addRepetitiveTask_Valid_BVA_2() throws ParseException{
        String stringTime = "03:00";
        int interval2 = tasksService.parseFromStringToSeconds(stringTime);
        assert interval2 == 10800;
    }

    @Test
    void addRepetitiveTask_Invalid_BVA() throws ParseException{
        String stringTime = "25:00";
        assertThrows(
                ParseException.class,
                () -> tasksService.parseFromStringToSeconds(stringTime),
                "Expected parseFromStringToSeconds() to throw, but it didnt"
        );
    }

    @Test
    void addRepetitiveTask_Invalid_BVA_2() throws ParseException{
        String stringTime = "26:00";
        assertThrows(
                ParseException.class,
                () -> tasksService.parseFromStringToSeconds(stringTime),
                "Expected parseFromStringToSeconds() to throw, but it didnt"
        );
    }

    @Test
    void addRepetitiveTask_Valid_ESP() throws ParseException{
        String stringTime = "04:00";
        int interval4 = tasksService.parseFromStringToSeconds(stringTime);
        assert interval4 == 14400;
    }
    @Test
    void addRepetitiveTask_Valid_ESP_2() throws ParseException{
        String stringTime = "04:10";
        int interval4 = tasksService.parseFromStringToSeconds(stringTime);
        assert interval4 == 15000;
    }

    @Test
    void addRepetitiveTask_Invalid_ESP() throws ParseException{
        String stringTime = "-1:00";
        assertThrows(
                ParseException.class,
                () -> tasksService.parseFromStringToSeconds(stringTime),
                "Expected parseFromStringToSeconds() to throw, but it didnt"
        );
    }

    @AfterEach
    void tearDown() {
    }
}