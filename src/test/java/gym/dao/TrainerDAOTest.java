package gym.dao;


import gym.entities.*;
import gym.storage.GymStorage;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import javax.xml.crypto.Data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TrainerDAOTest {

    @InjectMocks
    private DataAccessObject<Trainer> trainerDAO = new TrainerDAO();

    @Mock
    private GymStorage storage;

    ///FindById
    @Test
    public void givenNullId_whenFindById_thenThrowsExceptionWithCorrectMessage() {
        //arrange

        //act
        Throwable thrown = catchThrowable(() -> trainerDAO.findById(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to findById: Entity must not be null");
    }

    @Test
    public void givenEmptyTrainerMap_whenFindByIdWithNonexistentId_thenReturnsNull() {
        //arrange
        when(storage.getTrainers()).thenReturn(Collections.emptyMap());
        //act
        Trainer actualTrainer = trainerDAO.findById(0L);
        //assert
        assertThat(actualTrainer).isNull();
    }

    @Test
    public void givenUserMapWithTrainerId1_whenFindById1_thenReturnCorrectTrainer() {
        // arrange
        Map<Long, Trainer> mapWithTrainer = new HashMap<>();
        User user = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainer expectedTrainer = new Trainer(1L,trainingType,user);
        mapWithTrainer.put(1L, expectedTrainer);
        when(storage.getTrainers()).thenReturn(mapWithTrainer);

        // act
        Trainer actualTrainer = trainerDAO.findById(1L);

        // assert
        assertThat(actualTrainer).isNotNull();
        assertThat(actualTrainer)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("specialization", trainingType)
                .hasFieldOrPropertyWithValue("user", user);
    }

    //FindAll
    @Test
    public void givenEmptyTrainerMap_whenFindAll_thenReturnEmptyTrainerList() {
        //arrange
        when(storage.getTrainers()).thenReturn(Collections.emptyMap());
        //act
        List<Trainer> trainers = trainerDAO.findAll();
        //assert
        assertThat(trainers).isNotNull().isEmpty();
    }

    @Test
    public void givenTrainersMapWithSomeTrainers_whenFindAll_thenReturnListWithTheSameTrainers() {
        //arrange
        Map<Long, Trainer> trainerMap = new HashMap<>();
        Trainer trainer1 = new Trainer(1L, null, null);
        Trainer trainer2 = new Trainer(2L, null, null);
        Trainer trainer3 = new Trainer(3L, null, null);
        trainerMap.put(1L, trainer1);
        trainerMap.put(2L, trainer2);
        trainerMap.put(3L, trainer3);
        when(storage.getTrainers()).thenReturn(trainerMap);
        //act
        List<Trainer> trainers = trainerDAO.findAll();
        //assert
        assertThat(trainers).isNotNull().hasSize(3);
        assertThat(trainers.get(0)).usingRecursiveComparison().isEqualTo(trainer1);
        assertThat(trainers.get(1)).usingRecursiveComparison().isEqualTo(trainer2);
        assertThat(trainers.get(2)).usingRecursiveComparison().isEqualTo(trainer3);
    }

    //Delete
    @Test
    public void givenNullId_whenDelete_thenThrowsExceptionWithCorrectMessage(){
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> trainerDAO.delete(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to delete entity with null id");
    }
    @Test
    public void givenEmptyTrainerMap_whenDelete_thenReturnFalse(){
        //arrange
        when(storage.getTrainers()).thenReturn(Collections.emptyMap());
        //act
        boolean result = trainerDAO.delete(1L);
        //assert
        assertThat(result).isFalse();
    }
    @Test
    public void givenTrainerMapWithElementId1_whenDeleteId1_thenReturnTrue(){
        //arrange
        Map<Long, Trainer> trainerMap = new HashMap<>();
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainer trainer = new Trainer(1L, trainingType, masterUser);

        trainerMap.put(1L,trainer);
        when(storage.getTrainers()).thenReturn(trainerMap);
        //act
        assertThat(trainerDAO.findById(1L)).isNotNull();
        boolean result = trainerDAO.delete(1L);
        //assert
        assertThat(result).isTrue();
        assertThat(trainerDAO.findById(1L)).isNull();
    }

    //Save
    @Test
    public void givenNullTrainer_whenSave_thenThrowIllegalArgumentException(){
        //arrage
        //act
        Throwable thrown = catchThrowable(() -> trainerDAO.save(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Entity must not be null");
    }

    @Test
    public void givenTrainerWithNullFields_whenSaveTrainer_thenThrowIllegalArgumentException(){
        //arrange
        Trainer trainer = new Trainer(1L,null, null);
        //act
        Throwable thrown = catchThrowable(() -> trainerDAO.save(trainer));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Null values are not allowed for certain attributes");
    }

    @Test
    public void givenTrainerWithoutIdAndWithForeignKeysNotSaved_whenSaveTraining_thenThrowException(){
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainer trainer = new Trainer(1L, trainingType, masterUser);

        //act
        Throwable thrown = catchThrowable(() -> trainerDAO.save(trainer));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Failed to save: Data integrity violation while creating/updating the entity.");
    }

    @Test
    public void givenTrainerWithoutId_whenSaveUser_thenReturnValidTrainer(){
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainer trainer = new Trainer(null, trainingType, masterUser);

        when(storage.getNextId(Trainer.class)).thenReturn(1L);
        when(storage.getUsers()).thenReturn(new HashMap<>(Map.of(1L,masterUser)));
        when(storage.getTrainers()).thenReturn(new HashMap<>());


        //act
        Trainer actualTrainer = trainerDAO.save(trainer);
        //assert
        assertThat(actualTrainer).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("specialization", trainingType)
                .hasFieldOrPropertyWithValue("user", masterUser);
    }

    @Test
    public void givenValidTrainerCreatedBefore_whenSaveUser_thenReturnAValidTrainerUpdated() {
        //arrange
        User user1 = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        User user2 = new User(2L, "Fernando", "Robles", "Fernando.Robles", "password", true);
        Trainer trainerBefore = new Trainer(1L, trainingType, user1);
        Trainer trainerAfter = new Trainer(1L, trainingType, user2);

        when(storage.getUsers()).thenReturn(new HashMap<>(Map.of(1L,user1, 2L,user2)));
        when(storage.getTrainers()).thenReturn(new HashMap<>(Map.of(1L,trainerBefore)));

        //act
        assertThat(trainerDAO.findById(1L)).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("user", user1)
                .hasFieldOrPropertyWithValue("specialization", trainingType);

        Trainer actualTrainer = trainerDAO.save(trainerAfter);
        //assert
        assertThat(actualTrainer).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("user", user2)
                .hasFieldOrPropertyWithValue("specialization", trainingType);
    }

}
