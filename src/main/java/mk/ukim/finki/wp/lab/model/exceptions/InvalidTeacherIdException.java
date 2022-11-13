package mk.ukim.finki.wp.lab.model.exceptions;

public class InvalidTeacherIdException extends RuntimeException{
    public InvalidTeacherIdException() {
        super("Invalid teacher id");
    }
}
