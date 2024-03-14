package arsw.CenizasDelPasado.demo.controler;

import arsw.CenizasDelPasado.demo.model.User;
import arsw.CenizasDelPasado.demo.persistence.exception.UserException;
import arsw.CenizasDelPasado.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/users")
public class UserAPIController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> protocolGetUser() {
        try {
            return new ResponseEntity<>(userService.showAllUsers(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{mail}")
    public ResponseEntity<?> getUser(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getUser(mail), HttpStatus.FOUND);
        } catch (UserException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{mail}/friends")
    public ResponseEntity<?> getUserFriends(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getUserFriends(mail), HttpStatus.FOUND);
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{mail}/game-stats")
    public ResponseEntity<?> getUserGameStats(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getUserGameStats(mail), HttpStatus.FOUND);
        } catch (UserException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{mail}/rooms")
    public ResponseEntity<?> getUserRooms(@PathVariable("mail") String mail){
        try {
            return new ResponseEntity<>(userService.getUserRooms(mail), HttpStatus.FOUND);
        } catch (UserException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> protocolPostUser(@RequestBody User user){
        try{
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception ex){
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
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
}
