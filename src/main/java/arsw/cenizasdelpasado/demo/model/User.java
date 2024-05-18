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

    private List<FriendRequest> friendRequest;
    private List<String> friends;
    private List<String> rooms;

    @PersistenceCreator
    public User(Long iD, String nickname, String mail, List<String> friends, List<String> rooms) {
        this.iD = iD;
        this.nickname = nickname;
        this.mail = mail;
        this.friends = friends;
        this.rooms = rooms;
        this.friendRequest = new ArrayList<>();
    }

    public User(String nickname, String mail) {
        this.iD = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.nickname = nickname;
        this.mail = mail;
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
                ", friendRequest=" + friendRequest +
                ", friends=" + friends +
                ", rooms=" + rooms +
                '}';
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
