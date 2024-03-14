package arsw.CenizasDelPasado.demo.persistence;

import arsw.CenizasDelPasado.demo.model.LevelGame;
import arsw.CenizasDelPasado.demo.model.enemys.Enemy;
import arsw.CenizasDelPasado.demo.persistence.exception.LevelException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface LevelRepository extends MongoRepository<LevelGame, Long>{
    @Query("{ID:?0}")
    LevelGame getLevelById(Long id) throws LevelException;

    @Query("{ID:?0}")
    @Update("{ '$set' : { 'enemies' : ?1 } }")
    void updateLevelEnemies(Long id,List<Enemy> enemies) throws LevelException;
    @Query("{ID:?0}")
    @Update("{ '$set' : { 'complete' : ?1 } }")
    void updateLevelComplete(Long id,Boolean complete) throws LevelException;
    @Query("{ID:?0}")
    @Update("{ '$set' : { 'num_objects' : ?1 } }")
    void updateLevelNumObjects(Long id,int num_objects) throws LevelException;

    void deleteLevelByID(Long id) throws LevelException;
}
