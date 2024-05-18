package arsw.cenizasdelpasado.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.*;

@Document("Room")
public class Room {
    public static final int NUMBERPLAYERS = 5;
    @Id
    private Long iD;
    private String serverName;
    @Indexed(unique = true)
    private String code;
    private Date creationDate;
    private List<String> usersInRoom;
    private RoomStats roomStats;
    private List<Long> levels;
    private boolean isPublic;
    private boolean online;

    @PersistenceCreator
    public Room(Long iD, String serverName, String code, Date creationDate, List<String> usersInRoom, RoomStats roomStats, List<Long> levels, boolean isPublic,boolean online) {
        this.iD = iD;
        this.serverName = serverName;
        this.code = code;
        this.creationDate = creationDate;
        this.usersInRoom = usersInRoom;
        this.roomStats = roomStats;
        this.levels = levels;
        this.isPublic = isPublic;
        this.online = online;
    }

    public Room(String serverName, String code, boolean isPublic) {
        this.iD = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.serverName = serverName;
        this.code = code;
        this.creationDate = new Date();
        this.usersInRoom = new ArrayList<>(NUMBERPLAYERS);
        this.roomStats = new RoomStats(0,0,0);
        this.levels = new ArrayList<>();
        this.isPublic = isPublic;
        this.online = false;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Long getID() {
        return iD;
    }

    public void setID(Long iD) {
        this.iD = iD;
    }

    public String getserverName() {
        return serverName;
    }

    public void setserverName(String serverName) {
        this.serverName = serverName;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getcreationDate() {
        return creationDate;
    }

    public void setcreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getusersInRoom() {
        return usersInRoom;
    }

    public void setusersInRoom(List<String> usersInRoom) {
        this.usersInRoom = usersInRoom;
    }

    public RoomStats getRoomStats() {
        return roomStats;
    }

    public void setRoomStats(RoomStats roomStats) {
        this.roomStats = roomStats;
    }

    public List<Long> getLevels() {
        return levels;
    }

    public void setLevels(List<Long> levels) {
        this.levels = levels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return Objects.equals(getCode(), room.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public String toString() {
        return "Room{" +
                "ID=" + iD +
                ", serverName='" + serverName + '\'' +
                ", code='" + code + '\'' +
                ", creationDate=" + creationDate +
                ", usersInRoom=" + usersInRoom +
                ", roomStats=" + roomStats +
                ", levels=" + levels +
                ", isPublic=" + isPublic +
                ", online=" + online +
                '}';
    }

    public static class RoomStats{
        private int totalDeaths;
        private int time;
        private int objectsFound;

        public RoomStats(int totalDeaths, int time, int objectsFound) {
            this.totalDeaths = totalDeaths;
            this.time = time;
            this.objectsFound = objectsFound;
        }

        public int gettotalDeaths() {
            return totalDeaths;
        }

        public void settotalDeaths(int totalDeaths) {
            this.totalDeaths = totalDeaths;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getobjectsFound() {
            return objectsFound;
        }

        public void setobjectsFound(int objectsFound) {
            this.objectsFound = objectsFound;
        }

        @Override
        public String toString() {
            return "RoomStats{" +
                    "totalDeaths=" + totalDeaths +
                    ", time=" + time +
                    ", objectsFound=" + objectsFound +
                    '}';
        }
    }
}
