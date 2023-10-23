package gym.dao;

import gym.dao.DataAccessObject;
import gym.dao.UserDAO;
import gym.entities.User;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDAOTest {

    private DataAccessObject<User> userDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        userDAO = new UserDAO();
        ((UserDAO) userDAO).setStorage(storage);
    }

    @Test
    public void save_ShouldAddUserToStorage() {
        User user = createUser(1L, "John", "Doe");
        userDAO.save(user);
        assertThat(userDAO.findById(1L)).isEqualTo(user);
    }

    @Test
    public void findById_ShouldReturnUserById() {
        User user = createUser(1L, "John", "Doe");
        userDAO.save(user);
        User result = userDAO.findById(1L);
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void findAll_ShouldReturnAllUsers() {
        User user1 = createUser(1L, "John", "Doe");
        User user2 = createUser(2L, "Jane", "Smith");
        userDAO.save(user1);
        userDAO.save(user2);
        List<User> result = userDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(user1, user2);
    }

    @Test
    public void delete_ShouldRemoveUserFromStorage() {
        User user = createUser(1L, "John", "Doe");
        userDAO.save(user);
        userDAO.delete(1L);
        assertThat(userDAO.findById(1L)).isNull();
    }

    private User createUser(Long id, String firstName, String lastName) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
