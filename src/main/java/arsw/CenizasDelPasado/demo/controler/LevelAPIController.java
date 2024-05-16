package arsw.cenizasdelpasado.demo.controler;


import arsw.cenizasdelpasado.demo.model.LevelGame;
import arsw.cenizasdelpasado.demo.model.enemys.Enemy;
import arsw.cenizasdelpasado.demo.persistence.exception.LevelException;
import arsw.cenizasdelpasado.demo.persistence.exception.LevelPersistenceException;
import arsw.cenizasdelpasado.demo.service.LevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/levels")
public class LevelAPIController {
    private final LevelService levelService;

    public LevelAPIController(LevelService levelService){
        this.levelService = levelService;
    }

    @Operation(summary = "Obtener todos los niveles", description = "Este endpoint devuelve una lista de todos los niveles.")
    @ApiResponse(responseCode = "202", description = "Lista de niveles", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> protocolGetRoom(){
        try {
            return new ResponseEntity<>(levelService.showAllLevels(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Obtener un nivel por ID", description = "Este endpoint devuelve un nivel específico basado en su ID.")
    @ApiResponse(responseCode = "202", description = "Nivel encontrado", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getLevel(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(levelService.getLevel(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Obtener enemigos de un nivel", description = "Este endpoint devuelve la lista de enemigos de un nivel específico basado en su ID. Estos enemigos seran un JSON del objeto enemigo")
    @ApiResponse(responseCode = "202", description = "Lista de enemigos del nivel", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @GetMapping(value = "/{id}/enemies")
    public ResponseEntity<?> getLevelEnemies(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(levelService.getEnemies(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(LevelGame.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Crear un nuevo nivel", description = "Este endpoint permite crear un nuevo nivel.")
    @ApiResponse(responseCode = "201", description = "Nivel creado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo crear el nivel", content = @Content)
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
    @Operation(summary = "Actualizar enemigos de un nivel", description = "Este endpoint permite actualizar los enemigos de un nivel específico.")
    @ApiResponse(responseCode = "202", description = "Enemigos actualizados exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar los enemigos", content = @Content)
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
    @Operation(summary = "Actualizar estado de completitud de un nivel", description = "Este endpoint permite actualizar el estado de completitud de un nivel.")
    @ApiResponse(responseCode = "202", description = "Estado de completitud del nivel actualizado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar el estado de completitud del nivel", content = @Content)
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
    @Operation(summary = "Actualizar cantidad de objetos de un nivel", description = "Este endpoint permite actualizar la cantidad de objetos de un nivel.")
    @ApiResponse(responseCode = "202", description = "Cantidad de objetos del nivel actualizada exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo actualizar la cantidad de objetos del nivel", content = @Content)
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
    @Operation(summary = "Eliminar un nivel", description = "Este endpoint permite eliminar un nivel específico.")
    @ApiResponse(responseCode = "202", description = "Nivel eliminado exitosamente", content = @Content)
    @ApiResponse(responseCode = "406", description = "No se pudo eliminar el nivel", content = @Content)
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