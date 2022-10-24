package mk.ukim.finki.wp.lab.model.exceptions;

public class StudentAlreadyInCourseException extends RuntimeException{
    public StudentAlreadyInCourseException() {
        super("Student is already in the course.");
    }
}
