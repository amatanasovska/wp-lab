package mk.ukim.finki.wp.lab.model.exceptions;

public class EmptyFieldsOnExistingCourseException extends RuntimeException{
    public EmptyFieldsOnExistingCourseException() {
        super("All the fields are mandatory.");
    }
}
