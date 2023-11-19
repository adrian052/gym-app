package gym.dao;

import gym.entities.User;
import gym.storage.GymStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {
    @InjectMocks
    private DataAccessObject<User> userDAO = new UserDAO();
    @Mock
    private GymStorage storage;

    //Tests for FindById
    @Test
    public void givenNullId_whenFindById_thenThrowsExceptionWithCorrectMessage() {
        //arrange

        //act
        Throwable thrown = catchThrowable(() -> userDAO.findById(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to findById: Entity must not be null");
    }

    @Test
    public void givenEmptyUserMap_whenFindByIdWithNonexistentId_thenReturnsNull() {
        //arrange
        when(storage.getUsers()).thenReturn(Collections.emptyMap());
        //act
        User actualUser = userDAO.findById(0L);
        //assert
        assertThat(actualUser).isNull();
    }

    @Test
    public void givenUserMapWithUserId1_whenFindById1_thenReturnCorrectUser() {
        // arrange
        Map<Long, User> mapWithUser = new HashMap<>();
        User expectedUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "secure_password", true);
        mapWithUser.put(1L, expectedUser);
        when(storage.getUsers()).thenReturn(mapWithUser);

        // act
        User actualUser = userDAO.findById(1L);

        // assert
        assertThat(actualUser).isNotNull();
        assertThat(actualUser)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("firstName", "Adrian")
                .hasFieldOrPropertyWithValue("lastName", "Ibarra")
                .hasFieldOrPropertyWithValue("username", "Adrian.Ibarra")
                .hasFieldOrPropertyWithValue("password", "secure_password")
                .hasFieldOrPropertyWithValue("active", true);
    }


    //FindAll
    @Test
    public void givenEmptyUserMap_whenFindAll_thenReturnEmptyUserList() {
        //arrange
        when(storage.getUsers()).thenReturn(Collections.emptyMap());
        //act
        List<User> users = userDAO.findAll();
        //assert
        assertThat(users).isNotNull().isEmpty();
    }

    @Test
    public void givenUserMapWithSomeUsers_whenFindAll_thenReturnListWithTheSameUsers() {
        //arrange
        Map<Long, User> userMap = new HashMap<>();
        User user1 = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "secret_password", true);
        User user2 = new User(2L,"John", "Doe", "john.doe", "123456",true);
        User user3 = new User(3L,"Jane", "Smith", "jane.smith", "password123", true);
        userMap.put(1L, user1);
        userMap.put(2L, user2);
        userMap.put(3L, user3);
        when(storage.getUsers()).thenReturn(userMap);
        //act
        List<User> users = userDAO.findAll();
        //assert
        assertThat(users).isNotNull().hasSize(3);
        assertThat(users.get(0)).usingRecursiveComparison().isEqualTo(user1);
        assertThat(users.get(1)).usingRecursiveComparison().isEqualTo(user2);
        assertThat(users.get(2)).usingRecursiveComparison().isEqualTo(user3);
    }


    //Delete
    @Test
    public void givenNullId_whenDelete_thenThrowsExceptionWithCorrectMessage(){
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> userDAO.delete(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to delete entity with null id");
    }

    @Test
    public void givenEmptyUserMap_whenDelete_thenReturnFalse(){
        //arrange
        when(storage.getUsers()).thenReturn(Collections.emptyMap());
        //act
        boolean result = userDAO.delete(1L);
        //assert
        assertThat(result).isFalse();
    }

    @Test
    public void givenUserMapWithElementId1_whenDeleteId1_thenReturnTrue(){
        //arrange
        Map<Long, User> userMap = new HashMap<>();
        User user = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "secret_password", true);
        userMap.put(1L,user);
        when(storage.getUsers()).thenReturn(userMap);
        //act
        assertThat(userDAO.findById(1L)).isNotNull();
        boolean result = userDAO.delete(1L);
        //assert
        assertThat(result).isTrue();
        assertThat(userDAO.findById(1L)).isNull();
    }

    //Save Tests
    @Test
    public void givenNullUser_whenSave_thenThrowIllegalArgumentException(){
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> userDAO.save(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Entity must not be null");
    }

    @Test
    public void givenUserWithNullFields_whenSaveUser_thenThrowIllegalArgumentException(){
        //arrange
        User user = new User(null, null, null,null,null,false);
        //act
        Throwable thrown = catchThrowable(() -> userDAO.save(user));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Null values are not allowed for certain attributes");
    }

    @Test
    public void givenUserWithoutId_whenSaveUser_thenReturnIdNumber1OfAValidUser(){
        //arrange
        Long expectedId = 1L;
        User expectedUser = new User(null,"Adrian","Ibarra", "Adrian.Ibarra", "secret_password", false);
        when(storage.getNextId(User.class)).thenReturn(expectedId);
        Map<Long, User> userMap = new HashMap<>();
        when(storage.getUsers()).thenReturn(userMap);
        //act
        User actualUser = userDAO.save(expectedUser);
        //assert
        assertThat(actualUser).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("firstName", "Adrian")
                .hasFieldOrPropertyWithValue("lastName", "Ibarra")
                .hasFieldOrPropertyWithValue("username", "Adrian.Ibarra")
                .hasFieldOrPropertyWithValue("password", "secret_password")
                .hasFieldOrPropertyWithValue("active", false);
    }

    @Test
    public void givenValidUserCreatedBefore_whenSaveUser_thenReturnAValidUserUpdated() {
        // arrange
        Long expectedId = 1L;
        User userBefore = new User(expectedId, "Fernando", "Robles", "Fernando.Robles", "secret_password_xyz", false);
        Map<Long, User> userMap = new HashMap<>();
        userMap.put(expectedId, userBefore);
        when(storage.getUsers()).thenReturn(userMap);
        // act
        User actualUser = userDAO.save(new User(expectedId, "Adrian", "Ibarra", "Adrian.Ibarra", "secret_password", false));
        // assert
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("firstName", "Adrian")
                .hasFieldOrPropertyWithValue("lastName", "Ibarra")
                .hasFieldOrPropertyWithValue("username", "Adrian.Ibarra")
                .hasFieldOrPropertyWithValue("password", "secret_password")
                .hasFieldOrPropertyWithValue("active", false);
    }
}
