package arsw.CenizasDelPasado.demo.persistence.exception;

public class RoomPersistenceException extends Exception{
    public RoomPersistenceException(String message) {
        super("ROOM PERSISTENCE ERROR: "+message);
    }
}
