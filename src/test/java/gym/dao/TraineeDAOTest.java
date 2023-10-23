package gym.dao;

import gym.entities.Trainee;
import gym.entities.User;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

public class TraineeDAOTest {

    private DataAccessObject<Trainee> traineeDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        traineeDAO = new TraineeDAO();
        ((TraineeDAO) traineeDAO).setStorage(storage);
    }

    @Test
    public void save_ShouldAddTraineeToStorage() {
        Trainee trainee = createTrainee(1L, "Trainee1");
        traineeDAO.save(trainee);
        assertThat(traineeDAO.findById(1L)).isEqualTo(trainee);
    }

    @Test
    public void findById_ShouldReturnTraineeById() {
        Trainee trainee = createTrainee(1L, "Trainee1");
        traineeDAO.save(trainee);
        Trainee result = traineeDAO.findById(1L);
        assertThat(result).isEqualTo(trainee);
    }

    @Test
    public void findAll_ShouldReturnAllTrainees() {
        Trainee trainee1 = createTrainee(1L, "Trainee1");
        Trainee trainee2 = createTrainee(2L, "Trainee2");
        traineeDAO.save(trainee1);
        traineeDAO.save(trainee2);
        List<Trainee> result = traineeDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(trainee1, trainee2);
    }

    @Test
    public void delete_ShouldRemoveTraineeFromStorage() {
        Trainee trainee = createTrainee(1L, "Trainee1");
        traineeDAO.save(trainee);
        traineeDAO.delete(1L);
        assertThat(traineeDAO.findById(1L)).isNull();
    }

    private Trainee createTrainee(Long id, String name) {
        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setUser(createUser(id, name));
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("Address for " + name);
        return trainee;
    }

    private User createUser(Long id, String name) {
        User user = new User();
        user.setId(id);
        user.setFirstName(name);
        user.setLastName("LastName");
        return user;
    }
}
