package arsw.CenizasDelPasado.demo.controler;

import arsw.CenizasDelPasado.demo.model.User;
import arsw.CenizasDelPasado.demo.persistence.exception.UserException;
import arsw.CenizasDelPasado.demo.persistence.exception.UserPersistenceException;
import arsw.CenizasDelPasado.demo.service.UserService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/users")
public class UserAPIController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Obtener todos los usuarios", description = "Este endpoint devuelve una lista de todos los usuarios.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> protocolGetUser() {
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
    public ResponseEntity<?> getUser(@PathVariable("mail") String mail){
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
    public ResponseEntity<?> getUserFriends(@PathVariable("mail") String mail){
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
    public ResponseEntity<?> getUserGameStats(@PathVariable("mail") String mail){
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
    public ResponseEntity<?> getUserRooms(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getUserRooms(mail), HttpStatus.ACCEPTED);
        } catch (UserException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Crear un nuevo usuario por inyección", description = "Este endpoint permite crear un nuevo usuario con un body que tenga toda la información del usuario.")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo crear el usuario", content = @Content)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> protocolPostUser(@RequestBody User user){
        try{
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Crear un nuevo usuario desde cero con correo y apodo", description = "Este endpoint permite crear un nuevo usuario con correo electrónico y apodo, generando todos sus valores iniciales automaticamente.")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo crear el usuario", content = @Content)
    @PostMapping(path = "/create")
    public ResponseEntity<?> createNewUser(@RequestBody Map<String, String> info){
        try{
            User user = new User(info.get("nickname"),info.get("mail"));
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
    public ResponseEntity<?> putUserNickname(@PathVariable("mail") String mail,@RequestParam("nickname") String nickname){
        try{
            userService.updateUserNickname(mail, nickname);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Actualizar amigos de un usuario", description = "Este endpoint permite actualizar la lista de amigos de un usuario basado en su dirección de correo electrónico.")
    @ApiResponse(responseCode = "202", description = "Lista de amigos del usuario actualizada exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar la lista de amigos del usuario", content = @Content)
    @PutMapping(path = "/{mail}/update-friends")
    public ResponseEntity<?> putUserFriends(@PathVariable("mail") String mail, @RequestBody List<String> friends){
        try{
            userService.updateUserFriends(mail, friends);
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
    public ResponseEntity<?> putUserRooms(@PathVariable("mail") String mail, @RequestBody List<String> rooms){
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
    public ResponseEntity<?> deleteUser(@PathVariable("mail") String mail){
        try{
            userService.deleteUser(mail);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @Operation(summary = "Inicia sesion de un usuario", description = "Este endpoint me premite revisar si un usuario ya existe, en caso de que no lo crea")
    @ApiResponse(responseCode = "203", description = "Se inicio secion correctamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo iniciar sesion", content = @Content)
    @GetMapping("startSession")
    public ResponseEntity<?> startSession(@RequestBody Map<String, String> user) throws UserException, UserPersistenceException {
        try {
            if (userService.getUser(user.get("mail")) != null) {
                return new ResponseEntity<>(userService.getUser(user.get("mail")), HttpStatus.FOUND);
            } else {
                User newUser = new User(user.get(user.get("nickname")), user.get("mail"));
                userService.saveUser(newUser);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }catch(UserPersistenceException e){
            User newUser = new User(user.get("nickname"), user.get("mail"));
            userService.saveUser(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
