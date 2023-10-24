package gym.storage;

import gym.entities.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void givenStorageWithInitializedData_whenGetDataFromMaps_thenDataShouldBeLoadedFromFile() {
        assertThat(storage.getTrainers()).isNotEmpty();
        assertThat(storage.getTrainees()).isNotEmpty();
        assertThat(storage.getTrainings()).isNotEmpty();
        assertThat(storage.getTrainingTypes()).isNotEmpty();
        assertThat(storage.getUsers()).isNotEmpty();
    }

    @Test
    public void givenNewJsonFilePath_whenSetJsonFilePath_thenJsonFilePathShouldBeUpdated() {
        String newFilePath = "new/data.json";
        ((InMemoryGymStorage)storage).setJsonFilePath(newFilePath);
        assertThat(((InMemoryGymStorage)storage).getJsonFilePath()).isEqualTo(newFilePath);
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
        ((InMemoryGymStorage) storage).setJsonFilePath("invalid_file_path.json");
        try {
            ((InMemoryGymStorage) storage).initializeData();
            assertNotNull(storage);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void givenNewUser_whenGetNextUserId_thenUserIdShouldBeIncremented() {
        long initialUserId = ((InMemoryGymStorage) storage).getNextUserId();
        long expectedUserId = initialUserId + 1;
        long nextUserId = ((InMemoryGymStorage) storage).getNextUserId();
        assertEquals(expectedUserId, nextUserId);
    }
}