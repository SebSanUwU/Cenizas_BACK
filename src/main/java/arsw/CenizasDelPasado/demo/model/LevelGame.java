package arsw.cenizasdelpasado.demo.model;

import arsw.cenizasdelpasado.demo.model.enemys.Enemy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Level")
public class LevelGame {
    @Id
    private Long iD;
    private String name;
    private int numObjects;
    private boolean complete;
    private List<Enemy> enemies;

    public LevelGame(Long ID, String name, int numObjects, boolean complete, List<Enemy> enemies) {
        this.iD = ID;
        this.name = name;
        this.numObjects = numObjects;
        this.complete = complete;
        this.enemies = enemies;
    }

    public Long getID() {
        return iD;
    }

    public void setID(Long ID) {
        this.iD = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getnumObjects() {
        return numObjects;
    }

    public void setnumObjects(int numObjects) {
        this.numObjects = numObjects;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
}
