package mk.ukim.finki.wp.lab.model.exceptions;

public class EmptyFieldsException extends RuntimeException{
    public EmptyFieldsException() {
        super("All the fields are mandatory.");
    }
}
