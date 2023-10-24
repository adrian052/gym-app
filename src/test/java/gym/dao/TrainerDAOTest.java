package gym.dao;

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
    public void givenTrainerDAOWithInitializedData_whenSaveTrainer_thenTrainerShouldBeAddedToStorage() {
        Trainer trainer = createTrainer(1L, "Trainer1");
        trainerDAO.save(trainer);
        assertThat(trainerDAO.findById(1L)).isEqualTo(trainer);
    }

    @Test
    public void givenTrainerDAOWithInitializedData_whenFindTrainerById_thenShouldReturnTrainer() {
        Trainer trainer = createTrainer(1L, "Trainer1");
        trainerDAO.save(trainer);
        Trainer result = trainerDAO.findById(1L);
        assertThat(result).isEqualTo(trainer);
    }

    @Test
    public void givenTrainerDAOWithInitializedData_whenFindAllTrainers_thenShouldReturnAllTrainers() {
        Trainer trainer1 = createTrainer(1L, "Trainer1");
        Trainer trainer2 = createTrainer(2L, "Trainer2");
        trainerDAO.save(trainer1);
        trainerDAO.save(trainer2);
        List<Trainer> result = trainerDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(trainer1, trainer2);
    }

    @Test
    public void givenTrainerDAOWithInitializedData_whenDeleteTrainer_thenTrainerShouldBeRemovedFromStorage() {
        Trainer trainer = createTrainer(1L, "Trainer1");
        trainerDAO.save(trainer);
        trainerDAO.delete(1L);
        assertThat(trainerDAO.findById(1L)).isNull();
    }

    @Test
    public void givenTrainerInStorage_whenSaveTrainerWithoutId_thenTrainerShouldBeAddedWithNewId() {
        Trainer trainer1 = createTrainer(null, "Trainer1");
        trainerDAO.save(trainer1);
        assertThat(trainer1.getId()).isNotNull();
        Trainer result = trainerDAO.findById(trainer1.getId());
        assertThat(result).isEqualTo(trainer1);
    }

    @Test
    public void givenNonExistentTrainer_whenDeleteNonExistentTrainer_thenShouldReturnFalse() {
        boolean deleted = trainerDAO.delete(123L); // Assuming 123L doesn't exist
        assertThat(deleted).isFalse();
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
