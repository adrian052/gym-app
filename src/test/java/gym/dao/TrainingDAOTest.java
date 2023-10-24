package gym.dao;

import gym.dao.DataAccessObject;
import gym.entities.*;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainingDAOTest {

    private GenericDAO<Training> trainingDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        trainingDAO = new TrainingDAO();
        ((TrainingDAO) trainingDAO).setStorage(storage);
    }

    @Test
    public void givenTrainingDAOWithInitializedData_whenSaveTraining_thenTrainingShouldBeAddedToStorage() {
        Training training = createTraining(1L, "Training1");
        training.setId(null);
        Long newId = trainingDAO.save(training);
        assertThat(trainingDAO.findById(newId)).isEqualTo(training);
    }

    @Test
    public void givenTrainingDAOWithInitializedData_whenFindTrainingById_thenShouldReturnTraining() {
        Training training = createTraining(1L, "Training1");
        trainingDAO.save(training);
        Training result = trainingDAO.findById(1L);
        assertThat(result).isEqualTo(training);
    }

    @Test
    public void givenTrainingDAOWithInitializedData_whenFindAllTrainings_thenShouldReturnAllTrainings() {
        Training training1 = createTraining(1L, "Training1");
        Training training2 = createTraining(2L, "Training2");
        trainingDAO.save(training1);
        trainingDAO.save(training2);
        List<Training> result = trainingDAO.findAll();
        assertThat(result).containsExactlyInAnyOrder(training1, training2);
    }

    @Test
    public void givenTrainingDAOWithInitializedData_whenDeleteTraining_thenTrainingShouldBeRemovedFromStorage() {
        Training training = createTraining(1L, "Training1");
        trainingDAO.save(training);
        trainingDAO.delete(1L);
        assertThat(trainingDAO.findById(1L)).isNull();
    }
    @Test
    public void givenTrainingInStorage_whenSaveTrainingWithoutId_thenTrainingValuesShouldBeUpdated() {
        Training training1 = createTraining(1L, "Training1");
        Long id = trainingDAO.save(training1);
        Training training2 = trainingDAO.findById(id);
        training2.setTrainingName("UpdatedTraining1");

        trainingDAO.save(training2);

        assertThat(training1.getTrainingName()).isEqualTo("UpdatedTraining1");
    }

    @Test
    public void givenNonExistentTraining_whenDeleteNonExistentTraining_thenShouldReturnFalse() {
        boolean deleted = trainingDAO.delete(123L); // Assuming 123L doesn't exist
        assertThat(deleted).isFalse();
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
