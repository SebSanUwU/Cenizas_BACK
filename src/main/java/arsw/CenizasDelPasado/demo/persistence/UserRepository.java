package arsw.CenizasDelPasado.demo.persistence;


import arsw.CenizasDelPasado.demo.model.User;
import arsw.CenizasDelPasado.demo.persistence.exception.UserException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Long> {
    @Query("{mail:?0}")
    User getUserByMail(String mail) throws UserException;
    @Query("{ID:?0}")
    User getUserById(Long id) throws UserException;
    @Query("{ 'mail' : ?0 }")
    @Update("{ '$set' : { 'nickname' : ?1 } }")
    void updateUserNickname(String mail,String nickname) throws UserException;
    @Query("{ 'mail' : ?0 }")
    @Update("{ '$set' : { 'gameStats' : ?1 } }")
    void updateUserGameStats(String mail,User.GameStats gameStats) throws UserException;
    @Query("{ 'mail' : ?0 }")
    @Update("{ '$set' : { 'friends' : ?1 } }")
    void updateUserFriends(String mail,List<String> friends) throws UserException;
    @Query("{ 'mail' : ?0 }")
    @Update("{ '$set' : { 'friends' : ?1 } }")
    void updateFriendRequest(String mail,List<String> friends) throws UserException;
    @Query("{ 'mail' : ?0 }")
    @Update("{ '$set' : { 'rooms' : ?1 } }")
    void updateUserRooms(String mail,List<String> rooms) throws UserException;
    void deleteUserByMail(String mail) throws UserException;

}
