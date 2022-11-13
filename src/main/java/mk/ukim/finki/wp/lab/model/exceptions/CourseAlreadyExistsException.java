package mk.ukim.finki.wp.lab.model.exceptions;

public class CourseAlreadyExistsException extends RuntimeException{
    public CourseAlreadyExistsException(String name) {
        super(String.format("Course with name %s already exists!", name));
    }
}
