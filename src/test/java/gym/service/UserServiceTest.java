package gym.service;

import gym.dao.UserDAO;
import gym.entities.User;
import gym.storage.GymStorage;
import gym.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private GymService<User> userService;

    @Before
    public void setUp() {
        userService = new UserService();
        UserDAO userDAO = new UserDAO();

        GymStorage storage = mock(GymStorage.class);
        ReflectionTestUtils.setField(userDAO, "storage", storage);

        ReflectionTestUtils.setField(userService, "userDAO", userDAO);

        when(storage.getUsers()).thenReturn(createUserMap());
    }

    @Test
    public void selectAll_ShouldReturnAllUsers() {
        List<User> users = userService.selectAll();
        assertThat(users).hasSize(3); // Adjust the expected size as needed
    }

    @Test
    public void select_ShouldReturnUserById() {
        User user = userService.select(1L);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    public void create_ShouldCreateUser() {
        User user = createUser(4L, "User4", "Last4", "user4", "password4");
        Long userId = userService.create(user);

        assertThat(userId).isEqualTo(4L);
    }

    @Test
    public void update_ShouldUpdateUser() {
        User user = createUser(1L, "User1", "Last1", "user1", "password1");
        user.setFirstName("UpdatedUser");
        userService.update(user);

        User updatedUser = userService.select(1L);

        assertThat(updatedUser.getFirstName()).isEqualTo("UpdatedUser");
    }

    @Test
    public void delete_ShouldDeleteUser() {
        userService.delete(2L);
        User deletedUser = userService.select(2L);

        assertThat(deletedUser).isNull();
    }

    @Test
    public void generateUsername_ShouldGenerateUniqueUsername() {
        User user = createUser(4L, "User4", "Last4", "user4", "password4");
        String username = ((UserService)userService).generateUsername(user);

        assertThat(username).isNotEmpty().isEqualTo("User4.Last4");
        ; // Adjust the expected format as needed
    }

    @Test
    public void existUsername_ShouldReturnTrueForExistingUsername() {
        boolean exists = ((UserService)userService).existUsername("user1");
        assertThat(exists).isTrue();
    }

    @Test
    public void existUsername_ShouldReturnFalseForNonExistingUsername() {
        boolean exists = ((UserService)userService).existUsername("nonexistinguser");
        assertThat(exists).isFalse();
    }

    @Test
    public void generateRandomPassword_ShouldGenerateRandomPassword() {
        int passwordLength = 8;
        String password = ((UserService)userService).generateRandomPassword(passwordLength);
        assertThat(password).hasSize(passwordLength).isNotNull();
    }



    private Map<Long, User> createUserMap() {
        Map<Long, User> users = new HashMap<>();
        users.put(1L, createUser(1L, "User1", "Last1", "user1", "password1"));
        users.put(2L, createUser(2L, "User2", "Last2", "user2", "password2"));
        users.put(3L, createUser(3L, "User3", "Last3", "user3", "password3"));
        return users;
    }
    private User createUser(Long id, String name, String lastName, String username, String password) {
        User user = new User();
        user.setId(id);
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
