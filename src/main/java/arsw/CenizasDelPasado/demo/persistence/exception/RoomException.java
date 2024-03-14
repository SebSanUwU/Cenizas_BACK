package arsw.CenizasDelPasado.demo.persistence.exception;

public class RoomException extends Exception{
    public RoomException(String message) {
        super("ROOM ERROR: "+message);
    }
}
