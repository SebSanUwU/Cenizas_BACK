package arsw.CenizasDelPasado.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Level")
public class Level {
    @Id
    private Long ID;
    private String name;
    private int num_objects;
    private boolean complete;

    public Level(Long ID, String name, int num_objects, boolean complete) {
        this.ID = ID;
        this.name = name;
        this.num_objects = num_objects;
        this.complete = complete;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_objects() {
        return num_objects;
    }

    public void setNum_objects(int num_objects) {
        this.num_objects = num_objects;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Level{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", num_objects=" + num_objects +
                ", complete=" + complete +
                '}';
    }
}
