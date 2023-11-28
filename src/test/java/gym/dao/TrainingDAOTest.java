package gym.dao;

import gym.dao.inmemory.InMemoryDao;
import gym.dao.inmemory.TrainingInMemoryDao;
import gym.entities.*;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

public class TrainingDAOTest extends DAOTest<Training>{/*
    @Override
    protected InMemoryDao<Training> getInstance() {
        return new TrainingInMemoryDao();
    }

    @Override
    protected void configureOwnMap(Map<Long, Training> mockMap) {
        when(storage.getTrainings()).thenReturn(mockMap);
    }

    @Override
    protected Training entityWithDependencies(Long id) {
        User masterUser = new User(id, "Adrian"+id, "Ibarra"+id, "Adrian.Ibarra"+id, "password"+id, true);
        TrainingType trainingType = new TrainingType(id, "Hip Trust"+id);
        Trainee trainee = new Trainee(id, null, "5 de Mayo"+id, masterUser);
        Trainer trainer = new Trainer(id, trainingType, masterUser);
        return new Training(id, trainee, trainer, "Training #"+id, trainingType, DateUtil.date(2023,8,1), 60);
    }

    @Override
    protected Training entityWithNullValues(Long id) {
        return new Training(id, null, null, null, null, null, 60);
    }

    @Test
    public void givenTrainingMapWithTrainingId1_whenFindById1_thenReturnCorrectTraining() {
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainee trainee = new Trainee(1L, null, "5 de Mayo", masterUser);
        Trainer trainer = new Trainer(1L, trainingType, masterUser);
        Training expectedTraining = new Training(1L, trainee, trainer, "Training #1", trainingType, DateUtil.date(2023,Calendar.OCTOBER,1), 60);
        configureOwnMap(new HashMap<>(Map.of(1L, expectedTraining)));
        //act
        Training actualTraining = dao.findById(1L);
        //assert
        assertThat(actualTraining).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainee", trainee)
                .hasFieldOrPropertyWithValue("trainer", trainer)
                .hasFieldOrPropertyWithValue("trainingName", "Training #1")
                .hasFieldOrPropertyWithValue("trainingType", trainingType)
                .hasFieldOrPropertyWithValue("trainingDate", DateUtil.date(2023,Calendar.OCTOBER,1))
                .hasFieldOrPropertyWithValue("trainingDuration", 60);
    }

    //FindAll
    @Test
    public void givenTrainingMapWithSomeTrainings_whenFindAll_thenReturnListWithTheSameTrainings() {
        //arrange
        Training training1 = new Training(1L, null, null, "Training #1", null, DateUtil.date(2023,Calendar.OCTOBER,1), 60);
        Training training2 = new Training(2L, null, null, "Training #2", null, DateUtil.date(2023,Calendar.OCTOBER,2), 60);
        Training training3 = new Training(3L, null, null, "Training #3", null, DateUtil.date(2023,Calendar.OCTOBER,3), 60);
        configureOwnMap(new HashMap<>(Map.of(1L, training1, 2L, training2, 3L, training3)));
        //act
        List<Training> trainings = dao.findAll();
        //assert
        assertThat(trainings).isNotNull().hasSize(3);
        assertThat(trainings.get(0)).usingRecursiveComparison().isEqualTo(training1);
        assertThat(trainings.get(1)).usingRecursiveComparison().isEqualTo(training2);
        assertThat(trainings.get(2)).usingRecursiveComparison().isEqualTo(training3);
    }
    
    //Save
    @Test
    public void givenTrainingWithoutIdAndWithForeignKeysNotSaved_whenSaveTraining_thenThrowException(){
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainee trainee = new Trainee(1L, null, "5 de Mayo", masterUser);
        Trainer trainer = new Trainer(1L, trainingType, masterUser);
        Training training = new Training(null, trainee, trainer, "Training #1", trainingType, DateUtil.date(2023,Calendar.OCTOBER,1), 60);
        configureOwnMap(new HashMap<>());

        //act
        Throwable thrown = catchThrowable(() -> dao.save(training));
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
        Training training = new Training(null, trainee, trainer, "Training #1", trainingType, DateUtil.date(2023,Calendar.OCTOBER,1), 60);
        when(storage.getNextId(Training.class)).thenReturn(1L);
        configureOwnMap(new HashMap<>());
        when(storage.getTrainees()).thenReturn(new HashMap<>(Map.of(1L,trainee)));
        when(storage.getTrainers()).thenReturn(new HashMap<>(Map.of(1L,trainer)));
        when(storage.getTrainingTypes()).thenReturn(new HashMap<>(Map.of(1L,trainingType)));

        //act
        Training actualTraining = dao.save(training);
        //assert
        assertThat(actualTraining).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainee", trainee)
                .hasFieldOrPropertyWithValue("trainer", trainer)
                .hasFieldOrPropertyWithValue("trainingName", "Training #1")
                .hasFieldOrPropertyWithValue("trainingType", trainingType)
                .hasFieldOrPropertyWithValue("trainingDate", DateUtil.date(2023,Calendar.OCTOBER,1))
                .hasFieldOrPropertyWithValue("trainingDuration", 60);
    }

    @Test
    public void givenValidUserCreatedBefore_whenSaveUser_thenReturnAValidUserUpdated() {
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainee trainee = new Trainee(1L, null, "5 de Mayo", masterUser);
        Trainer trainer = new Trainer(1L, trainingType, masterUser);
        Training trainingBefore = new Training(1L, trainee, trainer, "Training #2", trainingType, DateUtil.date(2023,Calendar.OCTOBER,2), 61);
        Training trainingAfter = new Training(1L, trainee, trainer, "Training #1", trainingType, DateUtil.date(2023,Calendar.OCTOBER,1), 60);

        configureOwnMap(new HashMap<>(Map.of(1L,trainingBefore)));
        when(storage.getTrainees()).thenReturn(new HashMap<>(Map.of(1L,trainee)));
        when(storage.getTrainers()).thenReturn(new HashMap<>(Map.of(1L,trainer)));
        when(storage.getTrainingTypes()).thenReturn(new HashMap<>(Map.of(1L,trainingType)));

        //act
        assertThat(dao.findById(1L)).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainee", trainee)
                .hasFieldOrPropertyWithValue("trainer", trainer)
                .hasFieldOrPropertyWithValue("trainingName", "Training #2")
                .hasFieldOrPropertyWithValue("trainingType", trainingType)
                .hasFieldOrPropertyWithValue("trainingDate", DateUtil.date(2023,Calendar.OCTOBER,2))
                .hasFieldOrPropertyWithValue("trainingDuration", 61);

        Training actualTraining = dao.save(trainingAfter);
        //assert
        assertThat(actualTraining).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("trainee", trainee)
                .hasFieldOrPropertyWithValue("trainer", trainer)
                .hasFieldOrPropertyWithValue("trainingName", "Training #1")
                .hasFieldOrPropertyWithValue("trainingType", trainingType)
                .hasFieldOrPropertyWithValue("trainingDate", DateUtil.date(2023,Calendar.OCTOBER,1))
                .hasFieldOrPropertyWithValue("trainingDuration", 60);
    }*/
}
