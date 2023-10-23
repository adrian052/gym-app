package gym.dao;

import gym.dao.DataAccessObject;
import gym.dao.TrainingDAO;
import gym.entities.*;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainingDAOTest {

    private DataAccessObject<Training> trainingDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        trainingDAO = new TrainingDAO();
        ((TrainingDAO) trainingDAO).setStorage(storage);
    }

    @Test
    public void save_ShouldAddTrainingToStorage() {
        Training training = createTraining(1L, "Training1");
        trainingDAO.save(training);
        assertThat(trainingDAO.findById(1L)).isEqualTo(training);
    }

    @Test
    public void findById_ShouldReturnTrainingById() {
        Training training = createTraining(1L, "Training1");
        trainingDAO.save(training);
        Training result = trainingDAO.findById(1L);
        assertThat(result).isEqualTo(training);
    }

    @Test
    public void findAll_ShouldReturnAllTrainings() {
        Training training1 = createTraining(1L, "Training1");
        Training training2 = createTraining(2L, "Training2");
        trainingDAO.save(training1);
        trainingDAO.save(training2);
        List<Training> result = trainingDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(training1, training2);
    }

    @Test
    public void delete_ShouldRemoveTrainingFromStorage() {
        Training training = createTraining(1L, "Training1");
        trainingDAO.save(training);
        trainingDAO.delete(1L);
        assertThat(trainingDAO.findById(1L)).isNull();
    }

    private Training createTraining(Long id, String name) {
        Training training = new Training();
        training.setId(id);
        training.setTrainee(createTrainee(id, "Trainee " + name));
        training.setTrainer(createTrainer(id, "Trainer " + name));
        training.setTrainingName(name);
        training.setTrainingType(createTrainingType(id, "Type " + name));
        training.setTrainingDate(new Date());
        training.setTrainingDuration(60); // 60 minutes
        return training;
    }

    private Trainee createTrainee(Long id, String name) {
        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setUser(createUser(id, name));
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("Address for " + name);
        return trainee;
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
