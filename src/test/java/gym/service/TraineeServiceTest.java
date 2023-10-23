package gym.service;

import gym.dao.DataAccessObject;
import gym.entities.Trainee;
import gym.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class TraineeServiceTest {

    @InjectMocks
    private TraineeService traineeService;

    @Mock
    private DataAccessObject<Trainee> traineeDAO;

    @Mock
    private DataAccessObject<User> userDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSelectAll_ShouldReturnAllTrainees() {
        Trainee trainee1 = new Trainee();
        Trainee trainee2 = new Trainee();
        List<Trainee> trainees = Arrays.asList(trainee1, trainee2);

        Mockito.when(traineeDAO.findAll()).thenReturn(trainees);

        List<Trainee> result = traineeService.selectAll();

        assertThat(result).isEqualTo(trainees);
    }

    @Test
    public void testSelect_ShouldReturnTraineeById() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        Mockito.when(traineeDAO.findById(1L)).thenReturn(trainee);

        Trainee result = traineeService.select(1L);

        assertThat(result).isEqualTo(trainee);
    }

    @Test
    public void testCreate_ShouldCreateTrainee() {
        User user = new User();
        user.setId(1L);
        Mockito.when(userDAO.findById(1L)).thenReturn(user);
        Trainee trainee = new Trainee();
        trainee.setUser(user);
        traineeDAO.save(any(Trainee.class));
        Long newTraineeId = traineeService.create(trainee);

        assertThat(newTraineeId).isEqualTo(1L);
    }

    @Test
    public void testCreate_ShouldThrowExceptionIfUserNotFound() {
        Mockito.when(userDAO.findById(1L)).thenReturn(null);

        Trainee trainee = new Trainee();
        User user = new User();
        user.setId(1L);
        trainee.setUser(user);

        assertThatThrownBy(() -> traineeService.create(trainee))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User with the specified ID does not exist.");
    }


    @Test
    public void testUpdate_ShouldThrowExceptionIfTraineeNotFound() {
        Mockito.when(traineeDAO.findById(1L)).thenReturn(null);

        Trainee trainee = new Trainee();
        trainee.setId(1L);

        assertThatThrownBy(() -> traineeService.update(trainee))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Trainee with the specified ID does not exist.");
    }

    @Test
    public void testDelete_ShouldDeleteTrainee() {
        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(1L);

        Mockito.when(traineeDAO.findById(1L)).thenReturn(existingTrainee);

        traineeService.delete(1L);

        Mockito.verify(traineeDAO).delete(1L);
    }

    @Test
    public void testDelete_ShouldThrowExceptionIfTraineeNotFound() {
        Mockito.when(traineeDAO.findById(1L)).thenReturn(null);

        assertThatThrownBy(() -> traineeService.delete(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Trainee with the specified ID does not exist.");
    }


}
