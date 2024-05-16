package arsw.cenizasdelpasado.demo.service;

import arsw.cenizasdelpasado.demo.model.Room;
import arsw.cenizasdelpasado.demo.persistence.RoomRepository;
import arsw.cenizasdelpasado.demo.persistence.exception.RoomException;
import arsw.cenizasdelpasado.demo.persistence.exception.RoomPersistenceException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class RoomService {


    private final RoomRepository roomRepository;

    public RoomService(RoomRepository rp) {
        this.roomRepository = rp;
        createUsers();
    }

    void createUsers(){
        roomRepository.deleteAll();
        roomRepository.save(new Room(1L,"Among us",generateCode(),new Date(),Arrays.asList("ejemplo2@gmail.com", "ejemplo3@gmail.com"),new Room.RoomStats(15,1425,3),Arrays.asList(1L,2L),true,true));
        roomRepository.save(new Room(2L, "Minecraft", generateCode(), new Date(), Arrays.asList("amigo1@gmail.com", "amigo2@gmail.com"), new Room.RoomStats(20, 3000, 5), Arrays.asList(3L, 4L),true,true));
        roomRepository.save(new Room(3L, "Fortnite", generateCode(), new Date(), Arrays.asList("usuario1@gmail.com", "usuario2@gmail.com"), new Room.RoomStats(10, 500, 2), Arrays.asList(5L, 6L, 7L),true,false));
        roomRepository.save(new Room(4L, "League of Legends", generateCode(), new Date(), Arrays.asList("player1@gmail.com", "player2@gmail.com", "player3@gmail.com"), new Room.RoomStats(30, 7000, 10), Arrays.asList(8L),true,false));
        roomRepository.save(new Room(5L, "Call of Duty", generateCode(), new Date(), Arrays.asList("gamer1@gmail.com", "gamer2@gmail.com"), new Room.RoomStats(25, 4000, 8), Arrays.asList(9L, 10L, 11L),true,false));
    }

    //CREATE
    public void saveRoom(Room room) throws RoomPersistenceException {
        try {
            verifyRoomExists(room.getID());
            throw new RoomPersistenceException("Room already exists with ID: " + room.getID());
        } catch (RoomException e) {
            try {
                verifyRoomExists(room.getCode());
                throw new RoomPersistenceException("Room already exists with code:" + room.getCode());
            } catch (RoomException ex) {
                roomRepository.save(room);
            }
        }
    }

    //READ
    public List<Room> showAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoom(String code) throws RoomException {
        verifyRoomExists(code);
        return roomRepository.getRoomByCode(code);
    }

    public Room.RoomStats getRoomStats(String code) throws RoomException {
        verifyRoomExists(code);
        return roomRepository.getRoomByCode(code).getRoomStats();
    }

    public List<String> getRoomUsers(String code) throws RoomException{
        verifyRoomExists(code);
        return roomRepository.getRoomByCode(code).getusersInRoom();
    }

    public List<Room> getPublicRoomsOnline(){
        return showAllRooms().stream().filter(Room::isPublic).filter(Room::isOnline).toList();
    }

    public List<Long> getRoomLevels(String code) throws RoomException{
        verifyRoomExists(code);
        return roomRepository.getRoomByCode(code).getLevels();
    }

    //UPDATE
    public String updateRoomCode(String code) throws RoomException {
        verifyRoomExists(code);
        Room room = roomRepository.getRoomByCode(code);
        String newCode = generateCode();
        roomRepository.updateRoomServerCode(room.getID(),newCode);
        return newCode;
    }
    public void updateRoomServerName(String code,String serverName) throws RoomException{
        verifyRoomExists(code);
        roomRepository.updateRoomServerName(code,serverName);
    }

    public void updateRoomUsers(String code,List<String> usersInRoom) throws RoomException {
        verifyRoomExists(code);
        roomRepository.updateRoomUsersInRoom(code,usersInRoom);
    }

    public void updateRoomLevels(String code,List<Long> levels) throws RoomException {
        verifyRoomExists(code);
        roomRepository.updateRoomLevels(code,levels);
    }

    public void updateRoomOnline(String code, Boolean online) throws RoomException {
        verifyRoomExists(code);
        roomRepository.updateRoomOnline(code, online);
    }

    /*ROOM-STATS*/

    //DELETE
    public void deleteRoom(String code) throws RoomException {
        verifyRoomExists(code);
        roomRepository.deleteRoomByCode(code);
    }


    public String generateCode(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String code = "";
        try {
            do {
                code = "";
                for (int i = 0; i < 7; i++) {
                    int indice = random.nextInt(caracteres.length());
                    char caracter = caracteres.charAt(indice);
                    code += caracter;
                }
                verifyRoomExists(code);
            } while (verifyRoomExistsBreak(code));
        } catch (RoomException e) {
            return code;
        }
        return code;
    }

    public void verifyRoomExists(String code) throws RoomException{
        Room room = roomRepository.getRoomByCode(code);
        if (room == null){
            throw new RoomException("Room not found by code: "+ code);
        }
    }

    public boolean verifyRoomExistsBreak(String code) throws RoomException{
        Room room = roomRepository.getRoomByCode(code);
        return room != null;
    }

    public void verifyRoomExists(Long id) throws RoomException{
        Room roomById = roomRepository.getRoomById(id);
        if(roomById == null){
            throw new RoomException("Room not found by: "+id);
        }
    }
}
