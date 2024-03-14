package arsw.CenizasDelPasado.demo.persistence.exception;

public class UserException extends Exception {
    public UserException(String message) {
        super("USER ERROR: "+message);
    }
}
