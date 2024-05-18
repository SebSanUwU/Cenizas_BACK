package arsw.cenizasdelpasado.demo.persistence;


import arsw.cenizasdelpasado.demo.model.Room;
import arsw.cenizasdelpasado.demo.persistence.exception.RoomException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface RoomRepository extends MongoRepository<Room, Long> {
    @Query("{code:?0}")
    Room getRoomByCode(String code) throws RoomException;
    @Query("{ID:?0}")
    Room getRoomById(Long id) throws RoomException;
    @Query("{ID:?0}")
    @Update("{ '$set' : { 'code' : ?1 } }")
    void updateRoomServerCode(Long id,String newCode);
    @Query("{code:?0}")
    @Update("{ '$set' : { 'serverName' : ?1 } }")
    void updateRoomServerName(String code,String serverName) throws RoomException;

    @Query("{code:?0}")
    @Update("{ '$set' : { 'usersInRoom' : ?1 } }")
    void updateRoomUsersInRoom(String code,List<String> usersInRoom) throws RoomException;
    @Query("{code:?0}")
    @Update("{ '$set' : { 'levels' : ?1 } }")
    void updateRoomLevels(String code,List<Long> levels) throws RoomException;

    @Query("{code:?0}")
    @Update("{ '$set' : { 'online' : ?1 } }")
    void updateRoomOnline(String code,Boolean online) throws RoomException;

    void deleteRoomByCode(String code) throws RoomException;
}


