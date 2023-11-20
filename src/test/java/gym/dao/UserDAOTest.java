package gym.dao;

import gym.entities.User;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserDAOTest extends DAOTest<User>{
    @Override
    protected DataAccessObject<User> getInstance() {
        return new UserDAO();
    }
    @Override
    protected void configureOwnMap(Map<Long, User> mockMap) {
        when(storage.getUsers()).thenReturn(mockMap);
    }

    @Override
    protected User entityWithDependencies(Long id) {
        return new User(id, "Adrian", "Ibarra", "Adrian", "Adrian.Ibarra", true);
    }

    @Override
    protected User entityWithNullValues(Long id) {
        return new User(id, null, null, null, null, true);
    }

    @Test
    public void givenUserMapWithUserId1_whenFindById1_thenReturnCorrectUser() {
        // arrange
        Map<Long, User> mapWithUser = new HashMap<>();
        User expectedUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "secure_password", true);
        mapWithUser.put(1L, expectedUser);
        configureOwnMap(mapWithUser);

        // act
        User actualUser = dao.findById(1L);

        // assert
        assertThat(actualUser).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("firstName", "Adrian")
                .hasFieldOrPropertyWithValue("lastName", "Ibarra")
                .hasFieldOrPropertyWithValue("username", "Adrian.Ibarra")
                .hasFieldOrPropertyWithValue("password", "secure_password")
                .hasFieldOrPropertyWithValue("active", true);
    }

    //FindAll
    @Test
    public void givenUserMapWithSomeUsers_whenFindAll_thenReturnListWithTheSameUsers() {
        //arrange
        User user1 = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "secret_password", true);
        User user2 = new User(2L,"John", "Doe", "john.doe", "123456",true);
        User user3 = new User(3L,"Jane", "Smith", "jane.smith", "password123", true);
        configureOwnMap(new HashMap<>(Map.of(1L, user1,2L, user2, 3L, user3)));
        //act
        List<User> users = dao.findAll();
        //assert
        assertThat(users).isNotNull().hasSize(3);
        assertThat(users.get(0)).usingRecursiveComparison().isEqualTo(user1);
        assertThat(users.get(1)).usingRecursiveComparison().isEqualTo(user2);
        assertThat(users.get(2)).usingRecursiveComparison().isEqualTo(user3);
    }

    //Save Tests
    @Test
    public void givenUserWithoutId_whenSaveUser_thenReturnIdNumber1OfAValidUser(){
        //arrange
        Long expectedId = 1L;
        User expectedUser = new User(null,"Adrian","Ibarra", "Adrian.Ibarra", "secret_password", false);
        when(storage.getNextId(User.class)).thenReturn(expectedId);
        configureOwnMap(new HashMap<>());
        //act
        User actualUser = dao.save(expectedUser);
        //assert
        assertThat(actualUser).isNotNull()
                .hasFieldOrPropertyWithValue("id", expectedId)
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
        configureOwnMap(new HashMap<>(Map.of(1L, userBefore)));
        // act
        User actualUser = dao.save(new User(expectedId, "Adrian", "Ibarra", "Adrian.Ibarra", "secret_password", false));
        // assert
        assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("firstName", "Adrian")
                .hasFieldOrPropertyWithValue("lastName", "Ibarra")
                .hasFieldOrPropertyWithValue("username", "Adrian.Ibarra")
                .hasFieldOrPropertyWithValue("password", "secret_password")
                .hasFieldOrPropertyWithValue("active", false);
    }
}
