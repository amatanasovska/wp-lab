package mk.ukim.finki.wp.lab.model.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException{
    public UsernameAlreadyTakenException() {
        super("Username already taken.");
    }
}
