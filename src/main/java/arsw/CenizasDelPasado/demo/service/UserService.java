package arsw.CenizasDelPasado.demo.service;

import arsw.CenizasDelPasado.demo.model.User;
import arsw.CenizasDelPasado.demo.persistence.UserRepository;
import arsw.CenizasDelPasado.demo.persistence.exception.UserException;
import arsw.CenizasDelPasado.demo.persistence.exception.UserPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository =  userRepository;
        createUsers();
    }

    void createUsers(){
        userRepository.deleteAll();
        System.out.println("Data creation started  USERS ...");
        userRepository.save(new User(1L,"AlpinitoDeSandia","juancamargo@gmail.com",new User.GameStats(5,1000,12,15,2), List.of(new String[]{"ejemplo1@gmail.com", "ejemplo3@gmail.com"}), List.of(new String[]{"XTS1"})));
        userRepository.save(new User(2L, "UsuarioEjemplo1", "ejemplo1@gmail.com", new User.GameStats(8, 1500, 20, 25, 3), Arrays.asList("ejemplo2@gmail.com", "ejemplo3@gmail.com"), Arrays.asList("XTS2", "XTS3")));
        userRepository.save(new User(3L, "UsuarioEjemplo2", "ejemplo2@gmail.com", new User.GameStats(10, 2000, 18, 30, 5), Arrays.asList("ejemplo1@gmail.com", "ejemplo3@gmail.com"), Arrays.asList("XTS1", "XTS3")));
        userRepository.save(new User(4L, "UsuarioEjemplo3", "ejemplo3@gmail.com", new User.GameStats(3, 800, 8, 10, 1), Arrays.asList("ejemplo1@gmail.com", "ejemplo2@gmail.com"), Arrays.asList("XTS1", "XTS2")));
        userRepository.save(new User(5L, "UsuarioEjemplo4", "ejemplo4@gmail.com", new User.GameStats(15, 3000, 25, 35, 7), Arrays.asList("ejemplo1@gmail.com", "ejemplo2@gmail.com", "ejemplo3@gmail.com"), Arrays.asList("XTS1", "XTS2", "XTS3")));
        userRepository.save(new User(6L,"SEBASTIAN ZAMORA URREGO","millossebas@hotmail.es",new User.GameStats(0,0,0,0,0),Arrays.asList("ejemplo1@gmail.com"),Arrays.asList("XTS1", "XTS2", "XTS3")));
        System.out.println("Data creation USERS complete...");
    }

    //CREATE
    public void saveUser(User user) throws UserPersistenceException {
        try {
            verifyUserExistsByID(user.getID());
            throw new UserPersistenceException("User already exists with ID: " + user.getID());
        } catch (UserException e) {
            try {
                verifyUserExists(user.getMail());
                throw new UserPersistenceException("User already exist with mail: "+user.getMail());
            } catch (UserException ex) {
                userRepository.save(user);
            }
        }
    }

    //READ
    public List<User> showAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String mail) throws UserException {
        verifyUserExists(mail);
        return userRepository.getUserByMail(mail);
    }

    public User.GameStats getUserGameStats(String mail) throws UserException {
        verifyUserExists(mail);
        return userRepository.getUserByMail(mail).getGameStats();
    }

    public List<String> getUserFriends(String mail) throws UserException {
        verifyUserExists(mail);
        return userRepository.getUserByMail(mail).getFriends();
    }

    public List<String> getUserRooms(String mail) throws UserException {
        verifyUserExists(mail);
        return userRepository.getUserByMail(mail).getRooms();
    }

    public List<User.FriendRequest> getFriendRequest(String mail) throws  UserException{
        verifyUserExists(mail);
        return userRepository.getUserByMail(mail).getFriendRequest();
    }

    public List<User.FriendRequest> getFriendRequestReceivedPending(String mail) throws  UserException{
        verifyUserExists(mail);
        List<User.FriendRequest> allFriendRequests = getFriendRequest(mail);
        return allFriendRequests.stream()
                .filter(request -> request.getState() == User.RequestState.PENDING)
                .filter(request -> !Objects.equals(request.getSender(), mail))
                .collect(Collectors.toList());
    }

    public List<User.FriendRequest> getFriendRequestSendPending(String mail) throws  UserException{
        verifyUserExists(mail);
        List<User.FriendRequest> allFriendRequests = getFriendRequest(mail);
        return allFriendRequests.stream()
                .filter(request -> request.getState() != User.RequestState.ACCEPTED)
                .filter(request -> Objects.equals(request.getSender(), mail))
                .collect(Collectors.toList());
    }

    //UPDATE
    public void updateUserNickname(String mail,String nickname) throws UserException {
        verifyUserExists(mail);
        userRepository.updateUserNickname(mail,nickname);
    }

    public void updateAddUserFriends(String mail,String friendMail) throws UserException {
        List<String> listUserFriends = getUserFriends(mail);
        listUserFriends.add(friendMail);
        userRepository.updateUserFriends(mail, listUserFriends);
    }

    public void updateUserRooms(String mail,List<String> rooms) throws UserException {
        verifyUserExists(mail);
        userRepository.updateUserRooms(mail, rooms);
    }

    public void updateUserAddFriendRequest(String mail, String friendMail) throws UserException{
        verifyFriendRequestDoesNotExist(mail,friendMail);
        List<User.FriendRequest> listFriendRequestSender = getFriendRequest(mail);
        List<User.FriendRequest> listFriendRequestReceiver = getFriendRequest(friendMail);
        listFriendRequestSender.add(new User.FriendRequest(mail,friendMail));
        listFriendRequestReceiver.add(new User.FriendRequest(mail,friendMail));
        userRepository.updateUserFriendRequest(mail,listFriendRequestSender);
        userRepository.updateUserFriendRequest(friendMail,listFriendRequestReceiver);
    }

    public void updateUserResponseFriendRequest(String mail, String friendMail, String response) throws UserException{
        List<User.FriendRequest> listFriendRequestSender = getFriendRequest(mail);
        List<User.FriendRequest> listFriendRequestReceiver = getFriendRequest(friendMail);
        Optional<User.FriendRequest> friendRequestSender = listFriendRequestSender.stream()
                .filter(request -> request.getSender().equals(friendMail))
                .filter(request -> request.getState().equals(User.RequestState.PENDING))
                .findFirst();

        Optional<User.FriendRequest> friendRequestReceiver = listFriendRequestReceiver.stream()
                .filter(request -> request.getReceiver().equals(mail))
                .filter(request -> request.getState().equals(User.RequestState.PENDING))
                .findFirst();

        if (friendRequestSender.isPresent() && friendRequestReceiver.isPresent()) {
            User.FriendRequest sender = friendRequestSender.get();
            User.FriendRequest receiver = friendRequestReceiver.get();
            if(response.equals("accepted")){
                sender.setState(User.RequestState.ACCEPTED);
                receiver.setState(User.RequestState.ACCEPTED);
                updateAddUserFriends(mail,friendMail);
                updateAddUserFriends(friendMail,mail);
            } else if (response.equals("refused")) {
                sender.setState(User.RequestState.REFUSED);
                receiver.setState(User.RequestState.REFUSED);
            }else {
                sender.setState(User.RequestState.PENDING);
                receiver.setState(User.RequestState.PENDING);
            }
            userRepository.updateUserFriendRequest(mail,listFriendRequestSender);
            userRepository.updateUserFriendRequest(friendMail,listFriendRequestReceiver);
        }else {
            throw new UserException("Friend request not found");
        }
    }

    /*USER-STATS*/

    //DELETE
    public void deleteUser(String mail) throws UserException {
        verifyUserExists(mail);
        userRepository.deleteUserByMail(mail);
    }

    public void deleteFriendRequest(String mail,String friendMail) throws UserException{
        List<User.FriendRequest> listFriendRequestSender = getFriendRequest(mail);
        List<User.FriendRequest> listFriendRequestReceiver = getFriendRequest(friendMail);
        listFriendRequestSender.add(new User.FriendRequest(mail,friendMail));
        listFriendRequestReceiver.add(new User.FriendRequest(mail,friendMail));
        userRepository.updateUserFriendRequest(mail,listFriendRequestSender);
        userRepository.updateUserFriendRequest(mail,listFriendRequestReceiver);
    }

    public void verifyUserExists(String mail) throws UserException {
        User user = userRepository.getUserByMail(mail);
        if (user == null){
            throw new UserException("User not found "+ mail);
        }
    }

    public void verifyUserExistsByID(Long id) throws UserException {
        User userById = userRepository.getUserById(id);
        if (userById == null){
            throw new UserException("User not found by ID "+ id);
        }
    }

    public void verifyFriendRequestDoesNotExist(String mail, String friendMail) throws UserException {
        List<User.FriendRequest> listFriendRequestSender = getFriendRequest(mail);
        List<User.FriendRequest> listFriendRequestReceiver = getFriendRequest(friendMail);
        List<String> friends = getUserFriends(mail);

        boolean senderExists = listFriendRequestSender.stream()
                .anyMatch(request -> request.getSender().equals(friendMail) && request.getReceiver().equals(mail) && request.getState().equals(User.RequestState.PENDING) && request.getState().equals(User.RequestState.ACCEPTED));

        boolean receiverExists = listFriendRequestReceiver.stream()
                .anyMatch(request -> request.getSender().equals(mail) && request.getReceiver().equals(friendMail) && request.getState().equals(User.RequestState.PENDING) && request.getState().equals(User.RequestState.ACCEPTED));

        if (senderExists || receiverExists || friends.contains(friendMail)) {
            throw new UserException("Friend request already sent or received or accepted");
        }
    }
}
