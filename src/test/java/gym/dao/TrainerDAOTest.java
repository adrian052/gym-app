package gym.dao;

import gym.dao.DataAccessObject;
import gym.dao.TrainerDAO;
import gym.entities.Trainer;
import gym.entities.TrainingType;
import gym.entities.User;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

public class TrainerDAOTest {

    private DataAccessObject<Trainer> trainerDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        trainerDAO = new TrainerDAO();
        ((TrainerDAO) trainerDAO).setStorage(storage);
    }

    @Test
    public void save_ShouldAddTrainerToStorage() {
        Trainer trainer = createTrainer(1L, "Trainer1");
        trainerDAO.save(trainer);
        assertThat(trainerDAO.findById(1L)).isEqualTo(trainer);
    }

    @Test
    public void findById_ShouldReturnTrainerById() {
        Trainer trainer = createTrainer(1L, "Trainer1");
        trainerDAO.save(trainer);
        Trainer result = trainerDAO.findById(1L);
        assertThat(result).isEqualTo(trainer);
    }

    @Test
    public void findAll_ShouldReturnAllTrainers() {
        Trainer trainer1 = createTrainer(1L, "Trainer1");
        Trainer trainer2 = createTrainer(2L, "Trainer2");
        trainerDAO.save(trainer1);
        trainerDAO.save(trainer2);
        List<Trainer> result = trainerDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(trainer1, trainer2);
    }

    @Test
    public void delete_ShouldRemoveTrainerFromStorage() {
        Trainer trainer = createTrainer(1L, "Trainer1");
        trainerDAO.save(trainer);
        trainerDAO.delete(1L);
        assertThat(trainerDAO.findById(1L)).isNull();
    }

    private Trainer createTrainer(Long id, String name) {
        Trainer trainer = new Trainer();
        trainer.setId(id);
        trainer.setUser(createUser(id, name));
        trainer.setSpecialization(createTrainingType(id, "Specialization " + name));
        return trainer;
    }

    private User createUser(Long id, String name) {
        User user = new User();
        user.setId(id);
        user.setFirstName(name);
        user.setLastName("LastName");
        return user;
    }

    private TrainingType createTrainingType(Long id, String name) {
        TrainingType type = new TrainingType();
        type.setId(id);
        type.setTrainingTypeName(name);
        return type;
    }
}
