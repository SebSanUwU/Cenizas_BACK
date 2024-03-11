package arsw.CenizasDelPasado.demo.persistence;

import arsw.CenizasDelPasado.demo.model.Level;
import arsw.CenizasDelPasado.demo.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LevelRepository extends MongoRepository<Level, Long> {
}
