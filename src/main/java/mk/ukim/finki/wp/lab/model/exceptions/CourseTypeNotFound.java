package mk.ukim.finki.wp.lab.model.exceptions;

public class CourseTypeNotFound extends RuntimeException {
    public CourseTypeNotFound(String courseType) {
        super("Course type " + courseType + " not found.");
    }
}
