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
    public User(Long iD, String nickname, String mail, List<String> friends, List<String> rooms) {
        this.iD = iD;
        this.nickname = nickname;
        this.mail = mail;
        this.gameStats = new GameStats(0,0,0,0,0);
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

    public void setID(Long iD) {
        this.iD = iD;
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
        private int lid;
        private int totalScore;
        private int objectsFound;
        private int numDeaths;
        private int gamesPlayed;

        public GameStats(int lid, int totalScore, int objectsFound, int numDeaths, int gamesPlayed) {
            this.lid = lid;
            this.totalScore = totalScore;
            this.objectsFound = objectsFound;
            this.numDeaths = numDeaths;
            this.gamesPlayed = gamesPlayed;
        }

        public int getLid() {
            return lid;
        }

        public void setLid(int lid) {
            this.lid = lid;
        }

        public int gettotalScore() {
            return totalScore;
        }

        public void settotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public int getobjectsFound() {
            return objectsFound;
        }

        public void setobjectsFound(int objectsFound) {
            this.objectsFound = objectsFound;
        }

        public int getnumDeaths() {
            return numDeaths;
        }

        public void setnumDeaths(int numDeaths) {
            this.numDeaths = numDeaths;
        }

        public int getgamesPlayed() {
            return gamesPlayed;
        }

        public void setgamesPlayed(int gamesPlayed) {
            this.gamesPlayed = gamesPlayed;
        }


        @Override
        public String toString() {
            return "GameStats{" +
                    "lid=" + lid +
                    ", totalScore=" + totalScore +
                    ", objectsFound=" + objectsFound +
                    ", numDeaths=" + numDeaths +
                    ", gamesPlayed=" + gamesPlayed +
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
