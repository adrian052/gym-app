package gym.dao;
import gym.entities.*;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import javax.xml.crypto.Data;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrainingDAOTest {

    @InjectMocks
    private DataAccessObject<Training> trainingDAO = new TrainingDAO();
    @Mock
    private GymStorage storage;

    //FindById
    @Test
    public void givenNullId_whenFindById_thenThrowsExceptionWithCorrectMessage(){
        //arrange

        //act
        Throwable thrown = catchThrowable(() -> trainingDAO.findById(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to findById: Entity must not be null");
    }

    @Test
    public void givenEmptyUserMap_whenFindByIdWithNonexistentId_thenReturnsNull() {
        //arrange
        //act
        Training actualUser = trainingDAO.findById(0L);
        //assert
        assertThat(actualUser).isNull();
    }


    @Test
    public void givenTrainingMapWithTrainingId1_whenFindById1_thenReturnCorrectTraining() {
        //arrange
        Map<Long, Training> mapWithTraining = new HashMap<>();
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainee trainee = new Trainee(1L, null, "5 de Mayo", masterUser);
        Trainer trainer = new Trainer(1L, trainingType, masterUser);
        Training expectedTraining = new Training(1L, trainee, trainer, "Training #1", trainingType, new Date(2023,Calendar.OCTOBER,1), 60);
        mapWithTraining.put(1L, expectedTraining);
        when(storage.getTrainings()).thenReturn(mapWithTraining);
        //act
        Training actualTraining = trainingDAO.findById(1L);
        //assert
        assertThat(actualTraining).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainee", trainee)
                .hasFieldOrPropertyWithValue("trainer", trainer)
                .hasFieldOrPropertyWithValue("trainingName", "Training #1")
                .hasFieldOrPropertyWithValue("trainingType", trainingType)
                .hasFieldOrPropertyWithValue("trainingDate", new Date(2023,Calendar.OCTOBER,1))
                .hasFieldOrPropertyWithValue("trainingDuration", 60);
    }

    //FindAll
    @Test
    public void givenEmptyTrainingMap_whenFindAll_thenReturnEmptyTrainingList() {
        //arrange
        when(storage.getTrainings()).thenReturn(Collections.emptyMap());
        //act
        List<Training> trainings = trainingDAO.findAll();
        //assert
        assertThat(trainings).isNotNull().isEmpty();
    }

    @Test
    public void givenTrainingMapWithSomeTrainings_whenFindAll_thenReturnListWithTheSameTrainings() {
        //arrange
        Map<Long, Training> trainingMap = new HashMap<>();
        Training training1 = new Training(1L, null, null, "Training #1", null, new Date(2023,Calendar.OCTOBER,1), 60);
        Training training2 = new Training(2L, null, null, "Training #2", null, new Date(2023,Calendar.OCTOBER,2), 60);
        Training training3 = new Training(3L, null, null, "Training #3", null, new Date(2023,Calendar.OCTOBER,3), 60);
        trainingMap.put(1L, training1);
        trainingMap.put(2L, training2);
        trainingMap.put(3L, training3);
        when(storage.getTrainings()).thenReturn(trainingMap);
        //act
        List<Training> trainings = trainingDAO.findAll();
        //assert
        assertThat(trainings).isNotNull().hasSize(3);
        assertThat(trainings.get(0)).usingRecursiveComparison().isEqualTo(training1);
        assertThat(trainings.get(1)).usingRecursiveComparison().isEqualTo(training2);
        assertThat(trainings.get(2)).usingRecursiveComparison().isEqualTo(training3);
    }
    //Delete
    @Test
    public void givenNullId_whenDelete_thenThrowsExceptionWithCorrectMessage(){
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> trainingDAO.delete(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to delete entity with null id");
    }

    @Test
    public void givenEmptyTrainingMap_whenDelete_thenReturnFalse(){
        //arrange
        when(storage.getTrainings()).thenReturn(Collections.emptyMap());
        //act
        boolean result = trainingDAO.delete(1L);
        //assert
        assertThat(result).isFalse();
    }

    @Test
    public void givenTrainingMapWithElementId1_whenDeleteId1_thenReturnTrue(){
        //arrange
        Map<Long, Training> trainingMap = new HashMap<>();
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainee trainee = new Trainee(1L, null, "5 de Mayo", masterUser);
        Trainer trainer = new Trainer(1L, trainingType, masterUser);
        Training expectedTraining = new Training(1L, trainee, trainer, "Training #1", trainingType, new Date(2023,Calendar.OCTOBER,1), 60);

        trainingMap.put(1L,expectedTraining);
        when(storage.getTrainings()).thenReturn(trainingMap);
        //act
        assertThat(trainingDAO.findById(1L)).isNotNull();
        boolean result = trainingDAO.delete(1L);
        //assert
        assertThat(result).isTrue();
        assertThat(trainingDAO.findById(1L)).isNull();
    }
    //Save
    @Test
    public void givenNullTraining_whenSave_thenThrowIllegalArgumentException(){
        //arrage
        //act
        Throwable thrown = catchThrowable(() -> trainingDAO.save(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Entity must not be null");
    }

    @Test
    public void givenTrainingWithNullFields_whenSaveTraining_thenThrowIllegalArgumentException(){
        //arrange
        Training training = new Training(1L,null, null, null,null,null,60);
        //act
        Throwable thrown = catchThrowable(() -> trainingDAO.save(training));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Null values are not allowed for certain attributes");
    }

    @Test
    public void givenTrainingWithoutIdAndWithForeignKeysNotSaved_whenSaveTraining_thenThrowException(){
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainee trainee = new Trainee(1L, null, "5 de Mayo", masterUser);
        Trainer trainer = new Trainer(1L, trainingType, masterUser);
        Training training = new Training(null, trainee, trainer, "Training #1", trainingType, new Date(2023,Calendar.OCTOBER,1), 60);
        when(storage.getTrainees()).thenReturn(new HashMap<>());

        //act
        Throwable thrown = catchThrowable(() -> trainingDAO.save(training));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Failed to save: Data integrity violation while creating/updating the entity.");
    }


    @Test
    public void givenTrainingWithoutId_whenSaveUser_thenReturnValidTraining(){
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainee trainee = new Trainee(1L, null, "5 de Mayo", masterUser);
        Trainer trainer = new Trainer(1L, trainingType, masterUser);
        Training training = new Training(null, trainee, trainer, "Training #1", trainingType, new Date(2023,Calendar.OCTOBER,1), 60);
        when(storage.getNextId(Training.class)).thenReturn(1L);
        when(storage.getTrainings()).thenReturn(new HashMap<>());
        when(storage.getTrainees()).thenReturn(new HashMap<>(Map.of(1L,trainee)));
        when(storage.getTrainers()).thenReturn(new HashMap<>(Map.of(1L,trainer)));
        when(storage.getTrainingTypes()).thenReturn(new HashMap<>(Map.of(1L,trainingType)));

        //act
        Training actualTraining = trainingDAO.save(training);
        //assert
        assertThat(actualTraining).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainee", trainee)
                .hasFieldOrPropertyWithValue("trainer", trainer)
                .hasFieldOrPropertyWithValue("trainingName", "Training #1")
                .hasFieldOrPropertyWithValue("trainingType", trainingType)
                .hasFieldOrPropertyWithValue("trainingDate", new Date(2023,Calendar.OCTOBER,1))
                .hasFieldOrPropertyWithValue("trainingDuration", 60);
    }

    @Test
    public void givenValidUserCreatedBefore_whenSaveUser_thenReturnAValidUserUpdated() {
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainee trainee = new Trainee(1L, null, "5 de Mayo", masterUser);
        Trainer trainer = new Trainer(1L, trainingType, masterUser);
        Training trainingBefore = new Training(1L, trainee, trainer, "Training #2", trainingType, new Date(2023,Calendar.OCTOBER,2), 61);
        Training trainingAfter = new Training(1L, trainee, trainer, "Training #1", trainingType, new Date(2023,Calendar.OCTOBER,1), 60);

        when(storage.getTrainings()).thenReturn(new HashMap<>(Map.of(1L,trainingBefore)));
        when(storage.getTrainees()).thenReturn(new HashMap<>(Map.of(1L,trainee)));
        when(storage.getTrainers()).thenReturn(new HashMap<>(Map.of(1L,trainer)));
        when(storage.getTrainingTypes()).thenReturn(new HashMap<>(Map.of(1L,trainingType)));

        //act
        assertThat(trainingDAO.findById(1L)).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainee", trainee)
                .hasFieldOrPropertyWithValue("trainer", trainer)
                .hasFieldOrPropertyWithValue("trainingName", "Training #2")
                .hasFieldOrPropertyWithValue("trainingType", trainingType)
                .hasFieldOrPropertyWithValue("trainingDate", new Date(2023,Calendar.OCTOBER,2))
                .hasFieldOrPropertyWithValue("trainingDuration", 61);

        Training actualTraining = trainingDAO.save(trainingAfter);
        //assert
        assertThat(actualTraining).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainee", trainee)
                .hasFieldOrPropertyWithValue("trainer", trainer)
                .hasFieldOrPropertyWithValue("trainingName", "Training #1")
                .hasFieldOrPropertyWithValue("trainingType", trainingType)
                .hasFieldOrPropertyWithValue("trainingDate", new Date(2023,Calendar.OCTOBER,1))
                .hasFieldOrPropertyWithValue("trainingDuration", 60);
    }
}
