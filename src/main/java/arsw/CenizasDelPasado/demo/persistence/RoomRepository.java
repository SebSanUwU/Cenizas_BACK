package arsw.CenizasDelPasado.demo.persistence;

import arsw.CenizasDelPasado.demo.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, Long> {

}
