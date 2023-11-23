package gym.dao;

import gym.entities.*;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

import java.util.*;
public class TrainerDAOTest extends DAOTest<Trainer>{
    ///FindById
    @Override
    protected DataAccessObject<Trainer> getInstance() {
        return new TrainerDAO();
    }

    @Override
    protected void configureOwnMap(Map<Long, Trainer> mockMap) {
        when(storage.getTrainers()).thenReturn(mockMap);
    }

    @Override
    protected Trainer entityWithDependencies(Long id) {
        User user = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        return new Trainer(id,trainingType,user);
    }
    @Override
    protected Trainer entityWithNullValues(Long id) {
        return new Trainer(id, null,null);
    }

    @Test
    public void givenUserMapWithTrainerId1_whenFindById1_thenReturnCorrectTrainer() {
        // arrange
        Map<Long, Trainer> mapWithTrainer = new HashMap<>();
        User user = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainer expectedTrainer = new Trainer(1L,trainingType,user);
        mapWithTrainer.put(1L, expectedTrainer);
        configureOwnMap(mapWithTrainer);
        // act
        Trainer actualTrainer = dao.findById(1L);

        // assert
        assertThat(actualTrainer).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("specialization", trainingType)
                .hasFieldOrPropertyWithValue("user", user);
    }

    //FindAll
    @Test
    public void givenTrainersMapWithSomeTrainers_whenFindAll_thenReturnListWithTheSameTrainers() {
        //arrange
        Trainer trainer1 = new Trainer(1L, null, null);
        Trainer trainer2 = new Trainer(2L, null, null);
        Trainer trainer3 = new Trainer(3L, null, null);
        configureOwnMap(new HashMap<>(Map.of(1L, trainer1, 2L, trainer2, 3L, trainer3)));
        //act
        List<Trainer> trainers = dao.findAll();
        //assert
        assertThat(trainers).isNotNull().hasSize(3);
        assertThat(trainers.get(0)).usingRecursiveComparison().isEqualTo(trainer1);
        assertThat(trainers.get(1)).usingRecursiveComparison().isEqualTo(trainer2);
        assertThat(trainers.get(2)).usingRecursiveComparison().isEqualTo(trainer3);
    }
    //Save
    @Test
    public void givenTrainerWithoutIdAndWithForeignKeysNotSaved_whenSaveTraining_thenThrowException(){
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        TrainingType trainingType = new TrainingType(1L, "Hip Trust");
        Trainer trainer = new Trainer(1L, trainingType, masterUser);

        //act
        Throwable thrown = catchThrowable(() -> dao.save(trainer));
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
        configureOwnMap(new HashMap<>());


        //act
        Trainer actualTrainer = dao.save(trainer);
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
        configureOwnMap(new HashMap<>(Map.of(1L,trainerBefore)));

        //act
        assertThat(dao.findById(1L)).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("user", user1)
                .hasFieldOrPropertyWithValue("specialization", trainingType);

        Trainer actualTrainer = dao.save(trainerAfter);
        //assert
        assertThat(actualTrainer).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("user", user2)
                .hasFieldOrPropertyWithValue("specialization", trainingType);
    }
}
