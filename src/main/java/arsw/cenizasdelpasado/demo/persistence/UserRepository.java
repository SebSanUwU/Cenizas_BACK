package arsw.cenizasdelpasado.demo.persistence;


import arsw.cenizasdelpasado.demo.model.User;
import arsw.cenizasdelpasado.demo.persistence.exception.UserException;
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
    @Update("{ '$set' : { 'friends' : ?1 } }")
    void updateUserFriends(String mail,List<String> friends) throws UserException;
    @Query("{ 'mail' : ?0 }")
    @Update("{ '$set' : { 'rooms' : ?1 } }")
    void updateUserRooms(String mail,List<String> rooms) throws UserException;
    @Query("{ 'mail' : ?0 }")
    @Update("{ '$set' : { 'friendRequest' : ?1 } }")
    void updateUserFriendRequest(String mail,List<User.FriendRequest> list) throws UserException;
    void deleteUserByMail(String mail) throws UserException;

}
