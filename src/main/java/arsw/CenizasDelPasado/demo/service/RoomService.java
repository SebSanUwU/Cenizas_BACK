package arsw.CenizasDelPasado.demo.service;

import arsw.CenizasDelPasado.demo.exception.CenizasDelPasadpException;
import arsw.CenizasDelPasado.demo.model.Room;
import arsw.CenizasDelPasado.demo.persistence.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
@Service
public class RoomService {

    @Autowired
    RoomRepository rp;

    public RoomService(RoomRepository rp) {
        this.rp = rp;
    }

    public void addNewRoom(Room room) throws CenizasDelPasadpException {
       rp.save(room);
    }

    public ArrayList<Room> getAllRooms() throws CenizasDelPasadpException {
        return new ArrayList<>(rp.findAll());
    }

    public Room getRoomCode(String code)throws CenizasDelPasadpException{
        return null;
    }

    public String generateCode(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String code = null;
        for (int i = 0; i < 7; i++) {
            int indice = random.nextInt(caracteres.length());
            char caracter = caracteres.charAt(indice);
            code += caracter;
        }
        return code;
    }
}
