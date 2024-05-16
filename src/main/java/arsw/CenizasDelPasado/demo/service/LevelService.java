package arsw.CenizasDelPasado.demo.service;


import arsw.CenizasDelPasado.demo.model.LevelGame;
import arsw.CenizasDelPasado.demo.model.enemys.Enemy;
import arsw.CenizasDelPasado.demo.persistence.LevelRepository;
import arsw.CenizasDelPasado.demo.persistence.exception.LevelException;
import arsw.CenizasDelPasado.demo.persistence.exception.LevelPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelService {

    private final LevelRepository levelRepository;

    public LevelService(LevelRepository levelRepository){
        this.levelRepository = levelRepository;
    }

    //CREATE
    public void saveLevel(LevelGame levelGame) throws LevelPersistenceException {
        try {
            verifyLevelExists(levelGame.getID());
            throw new LevelPersistenceException("Level already exists with ID: "+ levelGame.getID());
        } catch (LevelException e) {
            levelRepository.save(levelGame);
        }
    }

    //READ
    public List<LevelGame> showAllLevels(){
        return levelRepository.findAll();
    }

    public LevelGame getLevel(Long id) throws LevelException {
        verifyLevelExists(id);
        return levelRepository.getLevelById(id);
    }

    public List<Enemy> getEnemies(Long id) throws LevelException{
        verifyLevelExists(id);
        return levelRepository.getLevelById(id).getEnemies();
    }

    //UPDATE
    public void updateLevelEnemies(Long id,List<Enemy> enemies) throws LevelException {
        verifyLevelExists(id);
        levelRepository.updateLevelEnemies(id, enemies);
    }

    public void updateLevelComplete(Long id, boolean complete) throws LevelException{
        verifyLevelExists(id);
        levelRepository.updateLevelComplete(id,complete);
    }

    public void updateLevelNumObjects(Long id, int num_objects) throws LevelException{
        verifyLevelExists(id);
        levelRepository.updateLevelNumObjects(id, num_objects);
    }

    //DELETE
    public void deleteLevel(Long id) throws LevelException{
        verifyLevelExists(id);
        levelRepository.deleteLevelByID(id);
    }


    public void verifyLevelExists(Long id) throws LevelException {
        LevelGame levelById = levelRepository.getLevelById(id);
        if (levelById == null){
            throw new LevelException("Level not found by ID: "+id);
        }
    }

}
