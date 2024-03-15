package arsw.CenizasDelPasado.demo.persistence.exception;

public class UserPersistenceException extends Exception{
    public UserPersistenceException(String message) {
        super("USER PERSISTENCE ERROR: "+message);
    }
}
