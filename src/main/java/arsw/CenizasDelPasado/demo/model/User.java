package arsw.cenizasdelpasado.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document("User")
public class User {

    @Id
    private Long iD;
    private String nickname;
    @Indexed(unique = true)
    private String mail;
    private GameStats gameStats;
    private List<FriendRequest> friendRequest;
    private List<String> friends;
    private List<String> rooms;

    @PersistenceCreator
    public User(Long ID, String nickname, String mail, GameStats gameStats, List<String> friends, List<String> rooms) {
        this.iD = ID;
        this.nickname = nickname;
        this.mail = mail;
        this.gameStats = gameStats;
        this.friends = friends;
        this.rooms = rooms;
        this.friendRequest = new ArrayList<>();
    }

    public User(String nickname, String mail) {
        this.iD = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.nickname = nickname;
        this.mail = mail;
        this.gameStats = new GameStats(0,0,0,0,0);
        this.friendRequest = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }


    public List<FriendRequest> getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(List<FriendRequest> friendRequest) {
        this.friendRequest = friendRequest;
    }

    public Long getID() {
        return iD;
    }

    public void setID(Long ID) {
        this.iD = ID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public GameStats getGameStats() {
        return gameStats;
    }

    public void setGameStats(GameStats gameStats) {
        this.gameStats = gameStats;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + iD +
                ", nickname='" + nickname + '\'' +
                ", mail='" + mail + '\'' +
                ", gameStats=" + gameStats +
                ", friendRequest=" + friendRequest +
                ", friends=" + friends +
                ", rooms=" + rooms +
                '}';
    }

    public static class GameStats{
        private int levels_complete;
        private int total_score;
        private int objects_found;
        private int num_deaths;
        private int games_played;

        public GameStats(int levels_complete, int total_score, int objects_found, int num_deaths, int games_played) {
            this.levels_complete = levels_complete;
            this.total_score = total_score;
            this.objects_found = objects_found;
            this.num_deaths = num_deaths;
            this.games_played = games_played;
        }

        public int getLevels_complete() {
            return levels_complete;
        }

        public void setLevels_complete(int levels_complete) {
            this.levels_complete = levels_complete;
        }

        public int getTotal_score() {
            return total_score;
        }

        public void setTotal_score(int total_score) {
            this.total_score = total_score;
        }

        public int getObjects_found() {
            return objects_found;
        }

        public void setObjects_found(int objects_found) {
            this.objects_found = objects_found;
        }

        public int getNum_deaths() {
            return num_deaths;
        }

        public void setNum_deaths(int num_deaths) {
            this.num_deaths = num_deaths;
        }

        public int getGames_played() {
            return games_played;
        }

        public void setGames_played(int games_played) {
            this.games_played = games_played;
        }


        @Override
        public String toString() {
            return "GameStats{" +
                    "levels_complete=" + levels_complete +
                    ", total_score=" + total_score +
                    ", objects_found=" + objects_found +
                    ", num_deaths=" + num_deaths +
                    ", games_played=" + games_played +
                    '}';
        }
    }
    public static class FriendRequest {
        private String sender;
        private String receiver;
        private RequestState state;

        public FriendRequest(String sender, String receiver) {
            this.sender = sender;
            this.receiver = receiver;
            this.state = RequestState.PENDING;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public RequestState getState() {
            return state;
        }

        public void setState(RequestState state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "friendRequest{" +
                    "sender='" + sender + '\'' +
                    ", receiver='" + receiver + '\'' +
                    ", state=" + state +
                    '}';
        }
    }
    public enum RequestState {
        PENDING,
        ACCEPTED,
        REFUSED
    }
}
