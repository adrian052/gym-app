package gym.dao.inmemory;

import gym.dao.DateUtil;
import gym.entities.Trainee;
import gym.entities.User;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

public class TraineeInMemoryDaoTest extends DAOTest<Trainee>{

    @Override
    protected InMemoryDao<Trainee> getInstance() {
        return new TraineeInMemoryDao();
    }

    @Override
    protected void configureOwnMap(Map<Long, Trainee> mockMap) {
        when(storage.getTrainees()).thenReturn(mockMap);
    }

    protected Trainee entityWithDependencies(Long id){
        User user = new User(id, "John"+id, "Doe"+id, "John.Doe"+id, "secret_password"+id, true);
        return new Trainee(id, DateUtil.date(2022, 8,2),"5 de Mayo", user);
    }

    @Override
    protected Trainee entityWithNullValues(Long id) {
        return new Trainee(1L,null,null,null);
    }

    ///FindById
    @Test
    public void givenUserMapWithTraineeId1_whenFindById1_thenReturnCorrectTrainee() {
        // arrange
        Map<Long, Trainee> mapWithTrainee = new HashMap<>();
        User user = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        Trainee expectedTrainee = new Trainee(1L,null,null,user);
        mapWithTrainee.put(1L, expectedTrainee);
        configureOwnMap(mapWithTrainee);
        // act
        Trainee actualTrainee = dao.findById(1L);
        // assert
        assertThat(actualTrainee).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("dateOfBirth", null)
                .hasFieldOrPropertyWithValue("address", null)
                .hasFieldOrPropertyWithValue("user", user);
    }

    @Test
    public void givenTraineeMapWithSomeTrainee_whenFindAll_thenReturnListWithTheSameTrainees() {
        //arrange
        Trainee trainee1 = new Trainee(1L, DateUtil.date(2022,8,2), null,null);
        Trainee trainee2 = new Trainee(2L, null, "5 de Mayo",null);
        Trainee trainee3 = new Trainee(3L, DateUtil.date(2022,5,2), null, null);
        configureOwnMap(new HashMap<>(Map.of(1L, trainee1, 2L, trainee2, 3L, trainee3)));
        //act
        List<Trainee> trainees = dao.findAll();
        //assert
        assertThat(trainees).isNotNull().hasSize(3);
        assertThat(trainees.get(0)).usingRecursiveComparison().isEqualTo(trainee1);
        assertThat(trainees.get(1)).usingRecursiveComparison().isEqualTo(trainee2);
        assertThat(trainees.get(2)).usingRecursiveComparison().isEqualTo(trainee3);
    }

    //Save
    @Test
    public void givenTraineeWithoutIdAndWithForeignKeysNotSaved_whenSaveTrainee_thenThrowException(){
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        Trainee trainee = new Trainee(1L, null,null, masterUser);

        when(storage.getUsers()).thenReturn(Collections.emptyMap());
        //act
        Throwable thrown = catchThrowable(() -> dao.save(trainee));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Failed to save: Data integrity violation while creating/updating the entity.");
    }

    @Test
    public void givenTraineeWithoutId_whenSaveUser_thenReturnValidTrainee(){
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        Trainee trainee = new Trainee(null, null, null, masterUser);

        when(storage.getNextId(Trainee.class)).thenReturn(1L);
        when(storage.getUsers()).thenReturn(new HashMap<>(Map.of(1L,masterUser)));
        configureOwnMap(new HashMap<>());

        //act
        Trainee actualTrainee = dao.save(trainee);
        //assert
        assertThat(actualTrainee).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("user", masterUser)
                .hasFieldOrPropertyWithValue("dateOfBirth", null)
                .hasFieldOrPropertyWithValue("address", null);
    }

    @Test
    public void givenValidTraineeCreatedBefore_whenSaveUser_thenReturnAValidTraineeUpdated() {
        //arrange

        User user = new User(1L, "Fernando", "Robles", "Fernando.Robles", "password", true);
        Trainee traineeBefore = new Trainee(1L,  DateUtil.date(2022, 8, 2),null,user);
        Trainee traineeAfter = new Trainee(1L, DateUtil.date(2022, 8, 3),null,user);

        when(storage.getUsers()).thenReturn(new HashMap<>(Map.of(1L,user)));
        configureOwnMap(new HashMap<>(Map.of(1L,traineeBefore)));

        //act
        assertThat(dao.findById(1L)).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("user", user)
                .hasFieldOrPropertyWithValue("dateOfBirth", DateUtil.date(2022, 8, 2))
                .hasFieldOrPropertyWithValue("address", null);

        Trainee actualTrainee = dao.save(traineeAfter);
        //assert
        assertThat(actualTrainee).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("user", user)
                .hasFieldOrPropertyWithValue("dateOfBirth", DateUtil.date(2022, 8, 3))
                .hasFieldOrPropertyWithValue("address", null);
    }
}
