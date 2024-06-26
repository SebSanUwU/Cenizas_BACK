package arsw.cenizasdelpasado.demo.controler;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import arsw.cenizasdelpasado.demo.model.Room;
import arsw.cenizasdelpasado.demo.persistence.exception.UserException;
import arsw.cenizasdelpasado.demo.service.RoomService;
import arsw.cenizasdelpasado.demo.service.UserService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/rooms")
public class RoomAPIController {
    private final RoomService roomService;

    private final UserService userService;

    public RoomAPIController(UserService userService, RoomService roomService){
        this.roomService = roomService;
        this.userService = userService;
    }

    @Operation(summary = "Obtener todas las salas", description = "Este endpoint devuelve una lista de todas las salas.")
    @ApiResponse(responseCode = "200", description = "Lista de salas", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @GetMapping
    public ResponseEntity<Object> protocolGetRoom(){
        try {
            return new ResponseEntity<>(roomService.showAllRooms(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Obtener una sala por código", description = "Este endpoint devuelve una sala específica basada en su código.")
    @ApiResponse(responseCode = "302", description = "Sala encontrada", content = @Content)
    @ApiResponse(responseCode = "404", description = "Sala no encontrada", content = @Content)
    @GetMapping(value = "/{code}")
    public ResponseEntity<Object> getRoom(@PathVariable("code") String code) {
        try {
            return new ResponseEntity<>(roomService.getRoom(code), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener usuarios de una sala", description = "Este endpoint devuelve la lista de usuarios de una sala basada en su código.")
    @ApiResponse(responseCode = "302", description = "Usuarios en la sala", content = @Content)
    @ApiResponse(responseCode = "404", description = "Sala no encontrada", content = @Content)
    @GetMapping(value = "/{code}/users-in-room")
    public ResponseEntity<Object> getRoomUsers(@PathVariable("code") String code) {
        try {
            return new ResponseEntity<>(roomService.getRoomUsers(code), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Obtener niveles de una sala", description = "Este endpoint devuelve la lista de niveles de una sala basada en su código, esto sera un lista de identificadores de Level donde se guardan la persistencia de cada nivel.")
    @ApiResponse(responseCode = "302", description = "Lista de niveles de la sala", content = @Content)
    @ApiResponse(responseCode = "404", description = "Sala no encontrada", content = @Content)
    @GetMapping(value = "/{code}/levels")
    public ResponseEntity<Object> getRoomLevels(@PathVariable("code") String code) {
        try {
            return new ResponseEntity<>(roomService.getRoomLevels(code), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear una nueva sala desde cero con nombre de servidor", description = "Este endpoint permite crear una nueva sala con un nombre de servidor creando los demas parametros automaticamente.")
    @ApiResponse(responseCode = "201", description = "Sala creada exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo crear la sala", content = @Content)
    @PostMapping(value = "/create")
    public ResponseEntity<Object> createRoom(@RequestParam("serverName") String serverName, @RequestParam("isPublic") boolean isPublic,@RequestParam("user_creator") String user){
        try{
            String code = roomService.generateCode();
            Room room = new Room(serverName,code,isPublic);
            roomService.saveRoom(room);
            putRoomUsers(code,user);
            List<String> userRooms = userService.getUserRooms(user);
            userRooms.add(code);
            userService.updateUserRooms(user,userRooms);
            return new ResponseEntity<>(new String[]{code},HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Actualizar código de una sala", description = "Este endpoint permite actualizar el código de una sala. Devuelve el nuevo codigo generado, GUARDENLO.")
    @ApiResponse(responseCode = "202", description = "Código de sala actualizado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar el código de la sala", content = @Content)
    @PutMapping(value = "/{code}/new-code")
    public ResponseEntity<Object> putRoomNewCode(@PathVariable("code") String code){
        try{
            return new ResponseEntity<>(roomService.updateRoomCode(code),HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Actualizar nombre de servidor de una sala", description = "Este endpoint permite actualizar el nombre de servidor de una sala.")
    @ApiResponse(responseCode = "202", description = "Nombre de servidor de sala actualizado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar el nombre de servidor de la sala", content = @Content)
    @PutMapping(value = "/{code}/update-name")
    public ResponseEntity<Object> putRoomServerName(@PathVariable("code") String code,@RequestParam("serverName") String serverName){
        try{
            roomService.updateRoomServerName(code, serverName);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Actualizar usuarios de una sala", description = "Este endpoint permite actualizar los usuarios de una sala.")
    @ApiResponse(responseCode = "202", description = "Usuarios de sala actualizados exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar los usuarios de la sala", content = @Content)
    @PutMapping(value = "/{code}/update-users")
    public void putRoomUsers(@PathVariable("code") String code, @RequestParam("user") String user){
        try{
            List<String> users = (List<String>) getRoomUsers(code).getBody();
            assert users != null;
            if(!users.contains(user)){
                users.add(user);
                roomService.updateRoomUsers(code,users);
            }else{
                throw new UserException("Usuario ya esta en la sala.");
            }

            new ResponseEntity<>(HttpStatus.ACCEPTED);


        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Actualizar niveles de una sala", description = "Este endpoint permite actualizar los niveles de una sala.")
    @ApiResponse(responseCode = "202", description = "Niveles de sala actualizados exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar los niveles de la sala", content = @Content)
    @PutMapping(value = "/{code}/update-levels")
    public ResponseEntity<Object> putRoomLevels(@PathVariable("code") String code, @RequestBody List<Long> levels){
        try{
            roomService.updateRoomLevels(code,levels);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Eliminar una sala", description = "Este endpoint permite eliminar una sala.")
    @ApiResponse(responseCode = "202", description = "Sala eliminada exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo eliminar la sala", content = @Content)
    @DeleteMapping(path = "/{code}/delete")
    public ResponseEntity<Object> deleteRoom(@PathVariable("code") String code){
        try{
            roomService.deleteRoom(code);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Operation(summary = "Actualizar sala en linea", description = "Este endpoint permite poner la sala en linea para que sea visto por los jugadores.")
    @ApiResponse(responseCode = "202", description = "Sala en linea exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo poner en linea a la sala", content = @Content)
    @PutMapping(path = "/{code}/RoomOn")
    public ResponseEntity<Object> putRoomOnlineOn(@PathVariable("code") String code){
        try{
            roomService.updateRoomOnline(code,true);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Operation(summary = "Actualizar sala en fuera de linea", description = "Este endpoint permite poner la sala en fuera de linea para que no sea visto por los jugadores.")
    @ApiResponse(responseCode = "202", description = "Sala fuera de linea exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo poner en fuera de linea a la sala", content = @Content)
    @PutMapping(path = "/{code}/RoomOff")
    public ResponseEntity<Object> putRoomOnlineOff(@PathVariable("code") String code){
        try{
            roomService.updateRoomOnline(code,false);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Operation(summary = "Obtener salas publicas y en linea", description = "Este endpoint devuelve la lista de salas piblicas y que esten en linea.")
    @ApiResponse(responseCode = "302", description = "Lista de salas publicas", content = @Content)
    @ApiResponse(responseCode = "404", description = "Salas publicas no encontrada", content = @Content)
    @GetMapping(value = "/publicRooms")
    public ResponseEntity<Object> getPublicRooms() {
        try {
            return new ResponseEntity<>(roomService.getPublicRoomsOnline(), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
