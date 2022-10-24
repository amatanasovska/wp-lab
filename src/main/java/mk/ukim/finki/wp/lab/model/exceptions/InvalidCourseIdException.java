package mk.ukim.finki.wp.lab.model.exceptions;

public class InvalidCourseIdException extends RuntimeException{
    public InvalidCourseIdException() {
        super("Invalid course ID.");
    }
}
