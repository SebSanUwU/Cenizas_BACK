package arsw.cenizasdelpasado.demo.persistence;

import arsw.cenizasdelpasado.demo.model.LevelGame;
import arsw.cenizasdelpasado.demo.persistence.exception.LevelException;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface LevelRepository extends MongoRepository<LevelGame, Long>{
    @Query("{ID:?0}")
    LevelGame getLevelById(Long id) throws LevelException;

    @Query("{ID:?0}")
    @Update("{ '$set' : { 'complete' : ?1 } }")
    void updateLevelComplete(Long id,Boolean complete) throws LevelException;
    @Query("{ID:?0}")
    @Update("{ '$set' : { 'numObjects' : ?1 } }")
    void updateLevelNumObjects(Long id,int numObjects) throws LevelException;

    void deleteLevelByID(Long id) throws LevelException;
}
