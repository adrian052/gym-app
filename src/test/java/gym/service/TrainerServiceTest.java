package gym.service;

import gym.dao.DataAccessObject;
import gym.entities.Trainer;
import gym.entities.TrainingType;
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

public class TrainerServiceTest {

    @InjectMocks
    private TrainerService trainerService;

    @Mock
    private DataAccessObject<Trainer> trainerDAO;

    @Mock
    private DataAccessObject<User> userDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSelectAll_ShouldReturnAllTrainers() {
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        List<Trainer> trainers = Arrays.asList(trainer1, trainer2);

        Mockito.when(trainerDAO.findAll()).thenReturn(trainers);

        List<Trainer> result = trainerService.selectAll();

        assertThat(result).isEqualTo(trainers);
    }

    @Test
    public void testSelect_ShouldReturnTrainerById() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);

        Mockito.when(trainerDAO.findById(1L)).thenReturn(trainer);

        Trainer result = trainerService.select(1L);

        assertThat(result).isEqualTo(trainer);
    }

    @Test
    public void testCreate_ShouldCreateTrainer() {

        User user = new User();
        user.setId(1L);
        Mockito.when(userDAO.findById(1L)).thenReturn(user);


        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Fitness");
        trainingType.setId(1L);

        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setSpecialization(trainingType);


        trainerDAO.save(any(Trainer.class));

        Long newTrainerId = trainerService.create(trainer);

        assertThat(newTrainerId).isEqualTo(1L);
    }

    @Test
    public void testCreate_ShouldThrowExceptionIfUserNotFound() {
        Mockito.when(userDAO.findById(1L)).thenReturn(null);

        Trainer trainer = new Trainer();
        User user = new User();
        user.setId(1L);
        trainer.setUser(user);

        assertThatThrownBy(() -> trainerService.create(trainer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User with the specified ID does not exist.");
    }

    @Test
    public void testCreate_ShouldThrowExceptionIfSpecializationIsNull() {
        User user = new User();
        user.setId(1L);
        Mockito.when(userDAO.findById(1L)).thenReturn(user);

        Trainer trainer = new Trainer();
        trainer.setUser(user);

        assertThatThrownBy(() -> trainerService.create(trainer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Specialization must not be null.");
    }

    @Test
    public void testUpdate_ShouldThrowExceptionIfTrainerNotFound() {
        Mockito.when(trainerDAO.findById(1L)).thenReturn(null);

        Trainer trainer = new Trainer();
        trainer.setId(1L);

        assertThatThrownBy(() -> trainerService.update(trainer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Trainer with the specified ID does not exist.");
    }
}

