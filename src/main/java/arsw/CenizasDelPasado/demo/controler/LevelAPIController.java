package arsw.CenizasDelPasado.demo.controler;




import arsw.CenizasDelPasado.demo.model.LevelGame;
import arsw.CenizasDelPasado.demo.model.enemys.Enemy;
import arsw.CenizasDelPasado.demo.persistence.exception.LevelException;
import arsw.CenizasDelPasado.demo.persistence.exception.LevelPersistenceException;
import arsw.CenizasDelPasado.demo.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/levels")
public class LevelAPIController {
    @Autowired
    private LevelService levelService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> protocolGetRoom(){
        try {
            return new ResponseEntity<>(levelService.showAllLevels(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getLevel(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(levelService.getLevel(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{id}/enemies")
    public ResponseEntity<?> getLevelEnemies(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(levelService.getEnemies(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> protocolPostLevel(@RequestBody LevelGame levelGame){
        try {
            levelService.saveLevel(levelGame);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (LevelPersistenceException e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(value = "/{id}/update-name")
    public ResponseEntity<?> putLevelEnemies(@PathVariable("id") Long id,@RequestBody List<Enemy> enemeis){
        try {
            levelService.updateLevelEnemies(id,enemeis);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (LevelException e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping(value = "/{id}/update-complete")
    public ResponseEntity<?> putLevelComplete(@PathVariable("id") Long id,@RequestParam("complete") boolean complete){
        try {
            levelService.updateLevelComplete(id,complete);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (LevelException e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping(value = "/{id}/update-num-objects")
    public ResponseEntity<?> putLevelNumObjects(@PathVariable("id") Long id,@RequestParam("num_objects") int num_objects){
        try {
            levelService.updateLevelNumObjects(id,num_objects);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (LevelException e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> deleteLevel(@PathVariable("id") Long id){
        try{
            levelService.deleteLevel(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception ex){
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
