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
    private Date creation_date;
    private List<String> usersInRoom;
    private RoomStats roomStats;
    private List<Long> levels;
    private boolean isPublic;
    private boolean online;

    @PersistenceCreator
    public Room(Long ID, String serverName, String code, Date creation_date, List<String> usersInRoom, RoomStats roomStats, List<Long> levels, boolean isPublic,boolean online) {
        this.iD = ID;
        this.serverName = serverName;
        this.code = code;
        this.creation_date = creation_date;
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
        this.creation_date = new Date();
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

    public void setID(Long ID) {
        this.iD = ID;
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

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
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
                ", creation_date=" + creation_date +
                ", usersInRoom=" + usersInRoom +
                ", roomStats=" + roomStats +
                ", levels=" + levels +
                ", isPublic=" + isPublic +
                ", online=" + online +
                '}';
    }

    public static class RoomStats{
        private int total_deaths;
        private int time;
        private int objects_found;

        public RoomStats(int total_deaths, int time, int objects_found) {
            this.total_deaths = total_deaths;
            this.time = time;
            this.objects_found = objects_found;
        }

        public int getTotal_deaths() {
            return total_deaths;
        }

        public void setTotal_deaths(int total_deaths) {
            this.total_deaths = total_deaths;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getObjects_found() {
            return objects_found;
        }

        public void setObjects_found(int objects_found) {
            this.objects_found = objects_found;
        }

        @Override
        public String toString() {
            return "RoomStats{" +
                    "total_deaths=" + total_deaths +
                    ", time=" + time +
                    ", objects_found=" + objects_found +
                    '}';
        }
    }
}
