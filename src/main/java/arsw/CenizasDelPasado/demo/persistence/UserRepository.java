package arsw.CenizasDelPasado.demo.persistence;


import arsw.CenizasDelPasado.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Long> {
    @Query("{mail:'?0'}")
    User findUserByMail(String mail);
}
