package arsw.CenizasDelPasado.demo.service;

import arsw.CenizasDelPasado.demo.model.User;
import arsw.CenizasDelPasado.demo.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository =  userRepository;
        createUsers();
    }

    void createUsers(){
        System.out.println("Data creation started  USERS ...");
        userRepository.save(new User(1L,"AlpinitoDeSandia","juancamargo@gmail.com",new User.GameStats(5,1000,12,15,2),null,null));
        userRepository.save(new User(2L, "UsuarioEjemplo1", "ejemplo1@gmail.com", new User.GameStats(8, 1500, 20, 25, 3), null, null));
        userRepository.save(new User(3L, "UsuarioEjemplo2", "ejemplo2@gmail.com", new User.GameStats(10, 2000, 18, 30, 5), null, null));
        userRepository.save(new User(4L, "UsuarioEjemplo3", "ejemplo3@gmail.com", new User.GameStats(3, 800, 8, 10, 1), null, null));
        userRepository.save(new User(5L, "UsuarioEjemplo4", "ejemplo4@gmail.com", new User.GameStats(15, 3000, 25, 35, 7), null, null));
        System.out.println("Data creation USERS complete...");
    }


    //READ
    public List<User> showAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String mail){
        return userRepository.findUserByMail(mail);
    }

    public User.GameStats getGameStats(String mail){
        return userRepository.findUserByMail(mail).getGameStats();
    }

    public List<String> getUserFriends(String mail){
        return userRepository.findUserByMail(mail).getFriends();
    }

    public List<Long> getUserRooms(String mail){
        return userRepository.findUserByMail(mail).getRooms();
    }

}
