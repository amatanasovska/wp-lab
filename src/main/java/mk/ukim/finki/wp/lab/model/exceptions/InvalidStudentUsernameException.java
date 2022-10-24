package mk.ukim.finki.wp.lab.model.exceptions;

public class InvalidStudentUsernameException extends RuntimeException{
    public InvalidStudentUsernameException() {
        super("Invalid student username.");
    }
}
