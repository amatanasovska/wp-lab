package mk.ukim.finki.wp.lab.model.exceptions;

public class CourseAddingErrorNameExists extends RuntimeException{
    public CourseAddingErrorNameExists(String name) {
        super(String.format("Course with name %s already exists!", name));

    }
}
