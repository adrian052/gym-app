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

    private GenericDAO<Trainee> traineeDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        traineeDAO = new TraineeDAO();
        ((TraineeDAO) traineeDAO).setStorage(storage);
    }

    @Test
    public void givenTraineeDAOWithInitializedData_whenSaveTrainee_thenTraineeShouldBeAddedToStorage() {
        Trainee trainee = createTrainee(1L, "Trainee1");
        Long newId = traineeDAO.save(trainee);
        assertThat(traineeDAO.findById(newId)).isEqualTo(trainee);
    }

    @Test
    public void givenTraineeDAOWithInitializedData_whenFindTraineeById_thenShouldReturnTrainee() {
        Trainee trainee = createTrainee(1L, "Trainee1");
        Long newId = traineeDAO.save(trainee);
        Trainee result = traineeDAO.findById(newId);
        assertThat(result).isEqualTo(trainee);
    }

    @Test
    public void givenTraineeDAOWithInitializedData_whenFindAllTrainees_thenShouldReturnAllTrainees() {
        Trainee trainee1 = createTrainee(1L, "Trainee1");
        Trainee trainee2 = createTrainee(2L, "Trainee2");
        traineeDAO.save(trainee1);
        traineeDAO.save(trainee2);
        List<Trainee> result = traineeDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(trainee1, trainee2);
    }

    @Test
    public void givenTraineeDAOWithInitializedData_whenDeleteTrainee_thenTraineeShouldBeRemovedFromStorage() {
        Trainee trainee = createTrainee(1L, "Trainee1");
        traineeDAO.save(trainee);
        traineeDAO.delete(1L);
        assertThat(traineeDAO.findById(1L)).isNull();
    }

    @Test
    public void givenTraineeInStorage_whenSaveTraineeWithoutId_thenTraineeShouldBeAddedWithNewId() {
        Trainee trainee1 = createTrainee(null, "Trainee1");
        traineeDAO.save(trainee1);
        assertThat(trainee1.getId()).isNotNull();
        Trainee result = traineeDAO.findById(trainee1.getId());
        assertThat(result).isEqualTo(trainee1);
    }

    @Test
    public void givenTraineeInStorage_whenDeleteNonExistentTrainee_thenShouldReturnFalse() {
        boolean deleted = traineeDAO.delete(123L);
        assertThat(deleted).isFalse();
    }

    private Trainee createTrainee(Long id, String name) {
        Trainee trainee = new Trainee();
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
