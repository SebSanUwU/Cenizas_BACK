package arsw.CenizasDelPasado.demo.persistence;

import arsw.CenizasDelPasado.demo.model.Room;
import arsw.CenizasDelPasado.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
}
