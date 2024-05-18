package arsw.cenizasdelpasado.demo.controler;

import arsw.cenizasdelpasado.demo.service.UserService;
import arsw.cenizasdelpasado.demo.model.User;
import arsw.cenizasdelpasado.demo.persistence.exception.UserException;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/users")
public class UserAPIController {

    private final UserService userService;

    public UserAPIController(UserService userService){
        this.userService = userService;
    }
    @Operation(summary = "Obtener todos los usuarios", description = "Este endpoint devuelve una lista de todos los usuarios.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @GetMapping
    public ResponseEntity<Object> protocolGetUser() {
        try {
            return new ResponseEntity<>(userService.showAllUsers(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Obtener un usuario por correo electrónico", description = "Este endpoint devuelve un usuario específico basado en su dirección de correo electrónico registrado.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @GetMapping("/{mail}")
    public ResponseEntity<Object> getUser(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getUser(mail), HttpStatus.ACCEPTED);
        } catch (UserException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Obtener amigos de un usuario", description = "Este endpoint devuelve la lista de amigos de un usuario basado en su dirección de correo electrónico.")
    @ApiResponse(responseCode = "200", description = "Lista de amigos del usuario", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @GetMapping("/{mail}/friends")
    public ResponseEntity<Object> getUserFriends(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getUserFriends(mail), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Obtener estadísticas de juego de un usuario", description = "Este endpoint devuelve las estadísticas de juego de un usuario basado en su dirección de correo electrónico.")
    @ApiResponse(responseCode = "200", description = "Estadísticas de juego del usuario como JSON del objeto GameStats", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @GetMapping("/{mail}/game-stats")
    public ResponseEntity<Object> getUserGameStats(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getUserGameStats(mail), HttpStatus.ACCEPTED);
        } catch (UserException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Obtener salas de un usuario", description = "Este endpoint devuelve las salas de un usuario basado en su dirección de correo electrónico.")
    @ApiResponse(responseCode = "200", description = "Lista de salas del usuario", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @GetMapping("/{mail}/rooms")
    public ResponseEntity<Object> getUserRooms(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getUserRooms(mail), HttpStatus.ACCEPTED);
        } catch (UserException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear un nuevo usuario desde cero con correo y apodo", description = "Este endpoint permite crear un nuevo usuario con correo electrónico y apodo, generando todos sus valores iniciales automaticamente.")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo crear el usuario", content = @Content)
    @PostMapping(path = "/create")
    public ResponseEntity<Object> createNewUser(@RequestParam("nickname") String nickname,@RequestParam("mail") String mail){
        try{
            User user = new User(nickname,mail);
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Actualizar el apodo de un usuario", description = "Este endpoint permite actualizar el apodo de un usuario basado en su dirección de correo electrónico.")
    @ApiResponse(responseCode = "202", description = "Apodo del usuario actualizado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar el apodo del usuario", content = @Content)
    @PutMapping(path = "/{mail}/update-nickname")
    public ResponseEntity<Object> putUserNickname(@PathVariable("mail") String mail,@RequestParam("nickname") String nickname){
        try{
            userService.updateUserNickname(mail, nickname);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Operation(summary = "Actualizar salas de un usuario", description = "Este endpoint permite actualizar la lista de salas de un usuario basado en su dirección de correo electrónico.")
    @ApiResponse(responseCode = "202", description = "Lista de salas del usuario actualizada exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar la lista de salas del usuario", content = @Content)
    @PutMapping(path = "/{mail}/update-rooms")
    public ResponseEntity<Object> putUserRooms(@PathVariable("mail") String mail, @RequestBody List<String> rooms){
        try{
            userService.updateUserRooms(mail, rooms);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Eliminar un usuario", description = "Este endpoint permite eliminar un usuario basado en su dirección de correo electrónico.")
    @ApiResponse(responseCode = "202", description = "Usuario eliminado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo eliminar el usuario", content = @Content)
    @DeleteMapping(path = "/{mail}/delete")
    public ResponseEntity<Object> deleteUser(@PathVariable("mail") String mail){
        try{
            userService.deleteUser(mail);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Operation(summary = "Mandar Solicitud de amistad", description = "Este endpoint permite mandarle una solicitud de amistad a otro usuario")
    @ApiResponse(responseCode ="203", description = "Se mando correctamente la solicitud")
    @ApiResponse(responseCode = "406", description = "No se pudo enviar la solicitud correctamente")
    @PutMapping(value = "{mail}/sendFriendRequest")
    public ResponseEntity<Object> putCreateFriendRequest(@PathVariable("mail") String mail,@RequestParam("friendMail") String friendMail){
        try {
            userService.updateUserAddFriendRequest(mail,friendMail);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(UserException ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Operation(summary = "Responder solicitud de amistad", description = "Este endpoint me permite responder una solicitud de amistad a otro usuario")
    @ApiResponse(responseCode ="203", description = "Se respondio correctamente la solicitud")
    @ApiResponse(responseCode = "406", description = "No se pudo enviar la respuesta correctamente")
    @PutMapping(value = "{mail}/ResponseFriendRequest")
    public ResponseEntity<Object> putResponseFriendRequest(@PathVariable("mail") String mail,@RequestParam("friendMail") String friendMail,@RequestParam("response") String response){
        try {
            userService.updateUserResponseFriendRequest(mail, friendMail, response);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(UserException ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Obtener solicitudes pendientes enviadas de un usuario", description = "Este endpoint devuelve las salas de un usuario basado en su dirección de correo electrónico.")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes pendientes", content = @Content)
    @ApiResponse(responseCode = "404", description = "Lista de solicitudes pendientes no encontrado", content = @Content)
    @GetMapping("/{mail}/SendFriendRequestPending")
    public ResponseEntity<Object> getUserFriendRequestSendPending(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getFriendRequestSendPending(mail), HttpStatus.ACCEPTED);
        } catch (UserException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Obtener solicitudes pendientes recibidas de un usuario", description = "Este endpoint devuelve las salas de un usuario basado en su dirección de correo electrónico.")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes recibidas", content = @Content)
    @ApiResponse(responseCode = "404", description = "Lista de solicitudes recibidas no encontrado", content = @Content)
    @GetMapping("/{mail}/ReceivedFriendRequestPending")
    public ResponseEntity<Object> getUserFriendRequestReceivedPending(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getFriendRequestReceivedPending(mail), HttpStatus.ACCEPTED);
        } catch (UserException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
