package gym.dao;

import gym.entities.Trainee;

import gym.entities.User;
import gym.storage.GymStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TraineeDAOTest {
    @InjectMocks
    private DataAccessObject<Trainee> traineeDAO = new TraineeDAO();
    @Mock
    private GymStorage storage;

    ///FindById
    @Test
    public void givenNullId_whenFindById_thenThrowsExceptionWithCorrectMessage() {
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> traineeDAO.findById(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to findById: Entity must not be null");
    }

    @Test
    public void givenEmptyTraineeMap_whenFindByIdWithNonexistentId_thenReturnsNull() {
        //arrange
        when(storage.getTrainees()).thenReturn(Collections.emptyMap());
        //act
        Trainee actualTrainee = traineeDAO.findById(0L);
        //assert
        assertThat(actualTrainee).isNull();
    }

    @Test
    public void givenUserMapWithTraineeId1_whenFindById1_thenReturnCorrectTrainee() {
        // arrange
        Map<Long, Trainee> mapWithTrainee = new HashMap<>();
        User user = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        Trainee expectedTrainee = new Trainee(1L,null,null,user);
        mapWithTrainee.put(1L, expectedTrainee);
        when(storage.getTrainees()).thenReturn(mapWithTrainee);
        // act
        Trainee actualTrainee = traineeDAO.findById(1L);
        // assert
        assertThat(actualTrainee).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("dateOfBirth", null)
                .hasFieldOrPropertyWithValue("address", null)
                .hasFieldOrPropertyWithValue("user", user);
    }


    //FindAll
    @Test
    public void givenEmptyTraineeMap_whenFindAll_thenReturnEmptyTraineeList() {
        //arrange
        when(storage.getTrainees()).thenReturn(Collections.emptyMap());
        //act
        List<Trainee> trainees = traineeDAO.findAll();
        //assert
        assertThat(trainees).isNotNull().isEmpty();
    }

    @Test
    public void givenTraineeMapWithSomeTrainee_whenFindAll_thenReturnListWithTheSameTrainees() {
        //arrange
        Map<Long, Trainee> traineeMap = new HashMap<>();
        Trainee trainee1 = new Trainee(1L, DateUtil.date(2022,8,2), null,null);
        Trainee trainee2 = new Trainee(2L, null, "5 de Mayo",null);
        Trainee trainee3 = new Trainee(3L, DateUtil.date(2022,5,2), null, null);
        traineeMap.put(1L, trainee1);
        traineeMap.put(2L, trainee2);
        traineeMap.put(3L, trainee3);
        when(storage.getTrainees()).thenReturn(traineeMap);
        //act
        List<Trainee> trainees = traineeDAO.findAll();
        //assert
        assertThat(trainees).isNotNull().hasSize(3);
        assertThat(trainees.get(0)).usingRecursiveComparison().isEqualTo(trainee1);
        assertThat(trainees.get(1)).usingRecursiveComparison().isEqualTo(trainee2);
        assertThat(trainees.get(2)).usingRecursiveComparison().isEqualTo(trainee3);
    }

    //Delete
    @Test
    public void givenNullId_whenDelete_thenThrowsExceptionWithCorrectMessage(){
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> traineeDAO.delete(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to delete entity with null id");
    }
    @Test
    public void givenEmptyTraineeMap_whenDelete_thenReturnFalse(){
        //arrange
        when(storage.getTrainees()).thenReturn(Collections.emptyMap());
        //act
        boolean result = traineeDAO.delete(1L);
        //assert
        assertThat(result).isFalse();
    }
    @Test
    public void givenTraineeMapWithElementId1_whenDeleteId1_thenReturnTrue(){
        //arrange
        Map<Long, Trainee> traineeMap = new HashMap<>();
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        Trainee trainee = new Trainee(1L, null,null, masterUser);

        traineeMap.put(1L,trainee);
        when(storage.getTrainees()).thenReturn(traineeMap);
        //act
        assertThat(traineeDAO.findById(1L)).isNotNull();
        boolean result = traineeDAO.delete(1L);
        //assert
        assertThat(result).isTrue();
        assertThat(traineeDAO.findById(1L)).isNull();
    }

    //Save
    @Test
    public void givenNullTrainee_whenSave_thenThrowIllegalArgumentException(){
        //arrange
        //act
        Throwable thrown = catchThrowable(() -> traineeDAO.save(null));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Entity must not be null");
    }

    @Test
    public void givenTraineeWithNullFields_whenSaveTrainee_thenThrowIllegalArgumentException(){
        //arrange
        Trainee trainee = new Trainee(1L,new Date(), "5 de mayo",null);
        //act
        Throwable thrown = catchThrowable(() -> traineeDAO.save(trainee));
        //assert
        assertThatThrownBy(() ->{throw thrown;})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failed to save: Null values are not allowed for certain attributes");
    }
    @Test
    public void givenTraineeWithoutIdAndWithForeignKeysNotSaved_whenSaveTrainee_thenThrowException(){
        //arrange
        User masterUser = new User(1L, "Adrian", "Ibarra", "Adrian.Ibarra", "password", true);
        Trainee trainee = new Trainee(1L, null,null, masterUser);

        when(storage.getUsers()).thenReturn(new HashMap<>());
        //act
        Throwable thrown = catchThrowable(() -> traineeDAO.save(trainee));
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
        when(storage.getTrainees()).thenReturn(new HashMap<>());

        //act
        Trainee actualTrainee = traineeDAO.save(trainee);
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
        when(storage.getTrainees()).thenReturn(new HashMap<>(Map.of(1L,traineeBefore)));

        //act
        assertThat(traineeDAO.findById(1L)).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("user", user)
                .hasFieldOrPropertyWithValue("dateOfBirth", DateUtil.date(2022, 8, 2))
                .hasFieldOrPropertyWithValue("address", null);

        Trainee actualTrainee = traineeDAO.save(traineeAfter);
        //assert
        assertThat(actualTrainee).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("user", user)
                .hasFieldOrPropertyWithValue("dateOfBirth", DateUtil.date(2022, 8, 3))
                .hasFieldOrPropertyWithValue("address", null);
    }
}
