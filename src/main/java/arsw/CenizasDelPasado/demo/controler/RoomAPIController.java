package arsw.CenizasDelPasado.demo.controler;


import arsw.CenizasDelPasado.demo.model.Room;
import arsw.CenizasDelPasado.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/rooms")
public class RoomAPIController {
    @Autowired
    private RoomService roomService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> protocolGetRoom(){
        try {
            return new ResponseEntity<>(roomService.showAllRooms(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{code}")
    public ResponseEntity<?> getRoom(@PathVariable("code") String code) {
        try {
            return new ResponseEntity<>(roomService.getRoom(code), HttpStatus.FOUND);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/{code}/room-stats")
    public ResponseEntity<?> getRoomStats(@PathVariable("code") String code) {
        try {
            return new ResponseEntity<>(roomService.getRoomStats(code), HttpStatus.FOUND);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/{code}/users-in-room")
    public ResponseEntity<?> getRoomUsers(@PathVariable("code") String code) {
        try {
            return new ResponseEntity<>(roomService.getRoomUsers(code), HttpStatus.FOUND);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/{code}/levels")
    public ResponseEntity<?> getRoomLevels(@PathVariable("code") String code) {
        try {
            return new ResponseEntity<>(roomService.getRoomLevels(code), HttpStatus.FOUND);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> protocolPostRoom(@RequestBody Room room){
        try{
            roomService.saveRoom(room);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PostMapping(value = "/create")
    public ResponseEntity<?> createRoom(@RequestParam("server_name") String server_name){
        try{
            String code = roomService.generateCode();
            Room room = new Room(server_name,code);
            roomService.saveRoom(room);
            return new ResponseEntity<>(code,HttpStatus.CREATED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping(value = "/{code}/new-code")
    public ResponseEntity<?> putRoomNewCode(@PathVariable("code") String code){
        try{
            return new ResponseEntity<>(roomService.updateRoomCode(code),HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping(value = "/{code}/update-name")
    public ResponseEntity<?> putRoomServerName(@PathVariable("code") String code,@RequestParam("server_name") String server_name){
        try{
            roomService.updateRoomServerName(code, server_name);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping(value = "/{code}/update-users")
    public ResponseEntity<?> putRoomUsers(@PathVariable("code") String code, @RequestBody List<String> users){
        try{
            roomService.updateRoomUsers(code,users);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping(value = "/{code}/update-levels")
    public ResponseEntity<?> putRoomLevels(@PathVariable("code") String code, @RequestBody List<Long> levels){
        try{
            roomService.updateRoomLevels(code,levels);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @DeleteMapping(path = "/{code}/delete")
    public ResponseEntity<?> deleteRoom(@PathVariable("code") String code){
        try{
            roomService.deleteRoom(code);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
