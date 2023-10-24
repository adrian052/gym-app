package gym.dao;

import gym.entities.User;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDAOTest {

    private GenericDAO<User> userDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        userDAO = new UserDAO();
        ((UserDAO) userDAO).setStorage(storage);
    }

    @Test
    public void givenUserDAOWithInitializedData_whenSaveUserWithId_thenUserShouldBeAddedToStorage() {
        User user = createUser("John", "Doe");
        Long newId = userDAO.save(user);
        assertThat(userDAO.findById(newId)).isEqualTo(user);
    }

    @Test
    public void givenUserDAOWithInitializedData_whenFindAllUsers_thenShouldReturnAllUsers() {
        User user1 = createUser("John", "Doe");
        User user2 = createUser("Jane", "Smith");
        userDAO.save(user1);
        userDAO.save(user2);
        List<User> result = userDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(user1, user2);
    }

    @Test
    public void givenUserInStorage_whenSaveUserWithoutId_thenUserShouldBeAddedWithNewId() {
        User user1 = createUser("John", "Doe");
        Long newId = userDAO.save(user1);
        assertThat(newId).isNotNull();
        User result = userDAO.findById(newId);
        assertThat(result).isEqualTo(user1);
    }

    @Test
    public void givenUserInStorage_whenDeleteNonExistentUser_thenShouldReturnFalse() {
        boolean deleted = userDAO.delete(123L); // Assuming 123L doesn't exist
        assertFalse(deleted);
    }

    @Test
    public void givenUserInStorage_whenDeleteUser_thenShouldReturnTrue() {
        User user = createUser("John", "Doe");
        Long id = userDAO.save(user);
        boolean deleted = userDAO.delete(id);
        assertTrue(deleted);
        assertThat(userDAO.findById(id)).isNull();
    }


    private User createUser(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
