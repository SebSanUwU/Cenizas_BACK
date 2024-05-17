package arsw.cenizasdelpasado.demo;

import arsw.cenizasdelpasado.demo.model.LevelGame;
import arsw.cenizasdelpasado.demo.model.Room;
import arsw.cenizasdelpasado.demo.model.User;
import arsw.cenizasdelpasado.demo.persistence.LevelRepository;
import arsw.cenizasdelpasado.demo.persistence.RoomRepository;
import arsw.cenizasdelpasado.demo.persistence.UserRepository;
import arsw.cenizasdelpasado.demo.persistence.exception.LevelException;
import arsw.cenizasdelpasado.demo.persistence.exception.LevelPersistenceException;
import arsw.cenizasdelpasado.demo.persistence.exception.RoomException;
import arsw.cenizasdelpasado.demo.persistence.exception.UserException;
import arsw.cenizasdelpasado.demo.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CenizasDelPasadoApplicationTests {
    @Mock
    private LevelRepository levelRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @InjectMocks
    private RoomService roomService;

    @InjectMocks
    private LevelService levelService;

    @Test
    public void testVerifyLevelExists_NotFound() throws LevelException, LevelPersistenceException {
        LevelService service = mock(LevelService.class);
        LevelRepository mockRepo = mock(LevelRepository.class);
        when(mockRepo.getLevelById(1L)).thenReturn(null);
        service.verifyLevelExists(1L);
    }


    @Test
    void saveLevel_WhenLevelDoesNotExist_ShouldSaveLevel() throws LevelException {
        // Arrange
        LevelGame levelGame = new LevelGame(1L,"prueba",5,false);
        when(levelRepository.getLevelById(anyLong())).thenReturn(null);

        // Act
        assertDoesNotThrow(() -> levelService.saveLevel(levelGame));
    }

    @Test
    void saveLevel_WhenLevelExists_ShouldThrowException() throws LevelException {
        // Arrange
        LevelGame levelGame = new LevelGame(1L,"prueba",5,false);
        levelGame.setID(1L);
        when(levelRepository.getLevelById(1L)).thenReturn(new LevelGame(1L,"prueba2",5,false));

        // Act & Assert
        assertThrows(LevelPersistenceException.class, () -> levelService.saveLevel(levelGame));
    }

    @Test
    void showAllLevels_ShouldReturnAllLevels() {
        // Arrange
        List<LevelGame> levels = new ArrayList<>();
        when(levelRepository.findAll()).thenReturn(levels);

        // Act
        List<LevelGame> result = levelService.showAllLevels();

        // Assert
        assertSame(levels, result);
    }

    @Test
    void saveRoom_WhenRoomDoesNotExist_ShouldSaveRoom() throws RoomException {
        // Arrange
        Room room = new Room("prueba_server","ABC1SDAS",false);
        when(roomRepository.getRoomByCode(anyString())).thenReturn(null);

        // Act
        assertDoesNotThrow(() -> roomService.saveRoom(room));

        // Assert
        verify(roomRepository, times(1)).save(room);
    }



    @Test
    public void testSaveUser_NewUser() throws UserException {
        // Prepare
        User newUser = new User(2L, "UsuarioEjemplo1", "ejemplo1@gmail.com", new User.GameStats(8, 1500, 20, 25, 3), Arrays.asList("ejemplo2@gmail.com", "ejemplo3@gmail.com"), Arrays.asList("XTS2", "XTS3"));
        when(userRepository.getUserById(newUser.getID())).thenReturn(null);
        when(userRepository.getUserByMail(newUser.getMail())).thenReturn(null);

        // Verify and assert
        assertDoesNotThrow(() -> {
            userService.saveUser(newUser);
        });

        // Ensure that userRepository.save() is called once
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    public void testShowAllUsers() {
        // Prepare
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L,"AlpinitoDeSandia","juancamargo@gmail.com",new User.GameStats(5,1000,12,15,2), List.of(new String[]{"ejemplo1@gmail.com", "ejemplo3@gmail.com"}), List.of(new String[]{"XTS1"})));
        userList.add(new User(2L, "UsuarioEjemplo1", "ejemplo1@gmail.com", new User.GameStats(8, 1500, 20, 25, 3), Arrays.asList("ejemplo2@gmail.com", "ejemplo3@gmail.com"), Arrays.asList("XTS2", "XTS3")));
        when(userRepository.findAll()).thenReturn(userList);

        // Verify and assert
        List<User> returnedUsers = userService.showAllUsers();
        assertEquals(userList.size(), returnedUsers.size());
        assertEquals(userList, returnedUsers);
    }

    @Test
    public void testGetUser_ExistingUser() throws UserException {
        // Prepare
        String mail = "example@example.com";
        User existingUser = new User(2L, "UsuarioEjemplo1", "ejemplo1@gmail.com", new User.GameStats(8, 1500, 20, 25, 3), Arrays.asList("ejemplo2@gmail.com", "ejemplo3@gmail.com"), Arrays.asList("XTS2", "XTS3"));
        when(userRepository.getUserByMail(mail)).thenReturn(existingUser);

        // Verify and assert
        User returnedUser = userService.getUser(mail);
        assertNotNull(returnedUser);
        assertEquals(existingUser, returnedUser);
    }

    @Test
    public void testGetUser_NonExistingUser() throws UserException {
        // Prepare
        String mail = "nonexisting@example.com";
        when(userRepository.getUserByMail(mail)).thenReturn(null);

        // Verify and assert
        UserException exception = assertThrows(UserException.class, () -> {
            userService.getUser(mail);
        });
        assertEquals("USER ERROR: User not found " + mail, exception.getMessage());
    }

    @Test
    public void testGetUserGameStats_ExistingUser() throws UserException {
        // Prepare
        String mail = "example@example.com";
        User existingUser = new User(2L, "UsuarioEjemplo1", "ejemplo1@gmail.com", new User.GameStats(8, 1500, 20, 25, 3), Arrays.asList("ejemplo2@gmail.com", "ejemplo3@gmail.com"), Arrays.asList("XTS2", "XTS3"));
        when(userRepository.getUserByMail(mail)).thenReturn(existingUser);

        // Verify and assert
        User.GameStats gameStats = userService.getUserGameStats(mail);
        assertNotNull(gameStats);
        // Añade más aserciones según corresponda
    }

    @Test
    public void testGetUserGameStats_NonExistingUser() throws UserException {
        // Prepare
        String mail = "nonexisting@example.com";
        when(userRepository.getUserByMail(mail)).thenReturn(null);

        // Verify and assert
        UserException exception = assertThrows(UserException.class, () -> {
            userService.getUserGameStats(mail);
        });
        assertEquals("USER ERROR: User not found " + mail, exception.getMessage());
    }

    @Test
    public void testUpdateUserNickname_ExistingUser() throws UserException {
        // Prepare
        String mail = "example@example.com";
        String newNickname = "NewNickname";
        User existingUser = new User(3L, newNickname, mail, new User.GameStats(10, 2000, 18, 30, 5), Arrays.asList("ejemplo1@gmail.com", "ejemplo3@gmail.com"), Arrays.asList("XTS1", "XTS3"));
        when(userRepository.getUserByMail(mail)).thenReturn(existingUser);

        // Verify and assert
        assertDoesNotThrow(() -> {
            userService.updateUserNickname(mail, newNickname);
        });
        assertEquals(newNickname, existingUser.getNickname());
        verify(userRepository, times(1)).updateUserNickname(mail, newNickname);
    }

    @Test
    public void testUpdateUserNickname_NonExistingUser() throws UserException {
        // Prepare
        String mail = "nonexisting@example.com";
        String newNickname = "NewNickname";
        when(userRepository.getUserByMail(mail)).thenReturn(null);

        // Verify and assert
        UserException exception = assertThrows(UserException.class, () -> {
            userService.updateUserNickname(mail, newNickname);
        });
        assertEquals("USER ERROR: User not found " + mail, exception.getMessage());
        // Asegúrate de que userRepository.updateUserNickname() no se llame
        verify(userRepository, never()).updateUserNickname(any(), any());
    }

}
