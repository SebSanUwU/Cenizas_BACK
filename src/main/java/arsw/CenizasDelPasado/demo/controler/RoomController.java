package arsw.CenizasDelPasado.demo.controler;

import arsw.CenizasDelPasado.demo.exception.CenizasDelPasadpException;
import arsw.CenizasDelPasado.demo.model.Room;
import arsw.CenizasDelPasado.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/room")
public class RoomController {
    @Autowired
    RoomService rs;

    @PostMapping(value = "/create")
    public ResponseEntity<?> manageCreateRoom() throws CenizasDelPasadpException {
        try {
            Room room = new Room();
            rs.addNewRoom(room);
            ArrayList<Room> sala = new ArrayList<>();
            sala.add(room);
            return new ResponseEntity<>(sala, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al crear la sala: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value ="/list")
    public ResponseEntity<?> manageGetAllRooms(){
        try{
            ArrayList<Room> rooms = rs.getAllRooms();
            return new ResponseEntity<>(rooms,HttpStatus.ACCEPTED);
        }catch (Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al obtener los planos: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getRoom")
    public  ResponseEntity<?> manageGetRoom(@RequestParam String codigo) {
        try {
            ArrayList<Room> sala = new ArrayList<>();
            sala.add(rs.getRoomCode(codigo));
            return new ResponseEntity<>(sala, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al crear la sala: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
