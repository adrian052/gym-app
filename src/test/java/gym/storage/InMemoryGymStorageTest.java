package gym.storage;

import gym.entities.*;
import gym.service.GymService;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryGymStorageTest {

    private GymStorage storage;

    @Before
    public void setUp() {
        String jsonFilePath = "data/data.json";
        storage = new InMemoryGymStorage();

        ((InMemoryGymStorage)storage).setJsonFilePath(jsonFilePath);
        ((InMemoryGymStorage)storage).initializeData();
    }

    @Test
    public void initializeData_ShouldLoadDataFromFile() {
        assertThat(storage.getTrainers()).isNotEmpty();
        assertThat(storage.getTrainees()).isNotEmpty();
        assertThat(storage.getTrainings()).isNotEmpty();
        assertThat(storage.getTrainingTypes()).isNotEmpty();
        assertThat(storage.getUsers()).isNotEmpty();
    }

    @Test
    public void setJsonFilePath_ShouldSetFilePath() {
        String newFilePath = "new/data.json";
        ((InMemoryGymStorage)storage).setJsonFilePath(newFilePath);
        assertThat(((InMemoryGymStorage)storage).getJsonFilePath()).isEqualTo(newFilePath);
    }

    @Test
    public void getTrainers_ShouldReturnAllTrainers() {
        Map<Long, Trainer> trainers = storage.getTrainers();
        assertThat(trainers).isNotEmpty();
    }

    @Test
    public void getTrainees_ShouldReturnAllTrainees() {
        Map<Long, Trainee> trainees = storage.getTrainees();
        assertThat(trainees).isNotEmpty();
    }

    @Test
    public void getTrainings_ShouldReturnAllTrainings() {
        Map<Long, Training> trainings = storage.getTrainings();
        assertThat(trainings).isNotEmpty();
    }

    @Test
    public void getTrainingTypes_ShouldReturnAllTrainingTypes() {
        Map<Long, TrainingType> trainingTypes = storage.getTrainingTypes();
        assertThat(trainingTypes).isNotEmpty();
    }

    @Test
    public void getUsers_ShouldReturnAllUsers() {
        Map<Long, User> users = storage.getUsers();
        assertThat(users).isNotEmpty();
    }

}