package arsw.CenizasDelPasado.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document("Room")
public class Room {
    public static final int NUMBERPLAYERS = 5;
    @Id
    private Long ID;
    private String server_name;
    @Indexed(unique = true)
    private String code;
    private Date creation_date;
    private List<String> users_in_room;
    private RoomStats roomStats;
    private List<Long> levels;

    public Room() {
    }

    public Room(Long ID, String server_name, String code, Date creation_date, List<String> users_in_room, RoomStats roomStats, List<Long> levels) {
        this.ID = ID;
        this.server_name = server_name;
        this.code = code;
        this.creation_date = creation_date;
        this.users_in_room = users_in_room;
        this.roomStats = roomStats;
        this.levels = levels;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
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

    public List<String> getUsers_in_room() {
        return users_in_room;
    }

    public void setUsers_in_room(List<String> users_in_room) {
        this.users_in_room = users_in_room;
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
                "ID=" + ID +
                ", server_name='" + server_name + '\'' +
                ", code='" + code + '\'' +
                ", creation_date=" + creation_date +
                ", users_in_room=" + users_in_room +
                ", roomStats=" + roomStats +
                ", levels=" + levels +
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
