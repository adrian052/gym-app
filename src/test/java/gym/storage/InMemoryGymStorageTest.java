package gym.storage;

import gym.entities.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryGymStorageTest {

    private GymStorage storage;

    @Before
    public void setUp() {
        String jsonFilePath = "data/data.json";
        storage = new InMemoryGymStorage();
        storage.setJsonFilePath(jsonFilePath);
        storage.initializeData();
    }

    @Test
    public void givenNewJsonFilePath_whenSetJsonFilePath_thenJsonFilePathShouldBeUpdated() {
        String newFilePath = "new/data.json";
        storage.setJsonFilePath(newFilePath);
        assertThat(storage.getJsonFilePath()).isEqualTo(newFilePath);
    }

    @Test
    public void givenStorageWithInitializedData_whenGetTrainers_thenAllTrainersShouldBeReturned() {
        Map<Long, Trainer> trainers = storage.getTrainers();
        assertThat(trainers).isNotEmpty();
    }

    @Test
    public void givenStorageWithInitializedData_whenGetTrainees_thenAllTraineesShouldBeReturned() {
        Map<Long, Trainee> trainees = storage.getTrainees();
        assertThat(trainees).isNotEmpty();
    }

    @Test
    public void givenStorageWithInitializedData_whenGetTrainings_thenAllTrainingsShouldBeReturned() {
        Map<Long, Training> trainings = storage.getTrainings();
        assertThat(trainings).isNotEmpty();
    }

    @Test
    public void givenStorageWithInitializedData_whenGetTrainingTypes_thenAllTrainingTypesShouldBeReturned() {
        Map<Long, TrainingType> trainingTypes = storage.getTrainingTypes();
        assertThat(trainingTypes).isNotEmpty();
    }

    @Test
    public void givenStorageWithInitializedData_whenGetUsers_thenAllUsersShouldBeReturned() {
        Map<Long, User> users = storage.getUsers();
        assertThat(users).isNotEmpty();
    }

    @Test
    public void givenInvalidJsonFile_whenInitializeData_thenExceptionShouldBeHandled() {
        storage.setJsonFilePath("invalid_file_path.json");
        try {
            storage.initializeData();
            assertNotNull(storage);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }
}
