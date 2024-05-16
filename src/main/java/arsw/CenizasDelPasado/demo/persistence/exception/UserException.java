package arsw.cenizasdelpasado.demo.persistence.exception;

public class UserException extends Exception {
    public UserException(String message) {
        super("USER ERROR: "+message);
    }
}
