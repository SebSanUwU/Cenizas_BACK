package arsw.CenizasDelPasado.demo.persistence;

import arsw.CenizasDelPasado.demo.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RoomRepository extends MongoRepository<Room, Long> {


}
