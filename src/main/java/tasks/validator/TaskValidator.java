package tasks.validator;

import org.apache.log4j.Logger;
import tasks.model.Task;

import java.util.Date;

public class TaskValidator {
    private static final Logger log = Logger.getLogger(TaskValidator.class.getName());

    public void validate (String title, Date time, boolean isActive){
        if (time.getTime() < 0) {
            log.error("time below bound");
            throw new IllegalArgumentException("Time cannot be negative");
        }
    }

    public void validate(String title, Date start, Date end, int interval, boolean isActive){
        if (start.getTime() < 0 || end.getTime() < 0) {
            log.error("time below bound");
            throw new IllegalArgumentException("Time cannot be negative");
        }
        if (interval < 1) {
            log.error("interval < than 1");
            throw new IllegalArgumentException("interval should me > 1");
        }
    }
}
