package mk.ukim.finki.wp.lab.model.exceptions;


public class InvalidCourseNameException extends RuntimeException{
    public InvalidCourseNameException(String name) {
        super(String.format("No course found with name %s", name));
    }
}
