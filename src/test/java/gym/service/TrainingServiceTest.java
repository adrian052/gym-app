package gym.service;

import gym.dao.DataAccessObject;
import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.entities.TrainingType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class TrainingServiceTest {

    @InjectMocks
    private TrainingService trainingService;

    @Mock
    private DataAccessObject<Training> trainingDAO;

    @Mock
    private DataAccessObject<Trainee> traineeDAO;

    @Mock
    private DataAccessObject<Trainer> trainerDAO;

    @Mock
    private DataAccessObject<TrainingType> trainingTypeDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSelectAll_ShouldReturnAllTrainings() {
        Training training1 = new Training();
        Training training2 = new Training();
        List<Training> trainings = Arrays.asList(training1, training2);

        Mockito.when(trainingDAO.findAll()).thenReturn(trainings);

        List<Training> result = trainingService.selectAll();

        assertThat(result).isEqualTo(trainings);
    }

    @Test
    public void testSelect_ShouldReturnTrainingById() {
        Training training = new Training();
        training.setId(1L);

        Mockito.when(trainingDAO.findById(1L)).thenReturn(training);

        Training result = trainingService.select(1L);

        assertThat(result).isEqualTo(training);
    }

    @Test
    public void testCreate_ShouldCreateTraining() {

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);

        Mockito.when(traineeDAO.findById(1L)).thenReturn(trainee);
        Mockito.when(trainerDAO.findById(1L)).thenReturn(trainer);
        Mockito.when(trainingTypeDAO.findById(1L)).thenReturn(trainingType);

        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setTrainingDate(new Date());
        training.setTrainingDuration(60);

        trainingDAO.save(any(Training.class));

        Long newTrainingId = trainingService.create(training);

        assertThat(newTrainingId).isEqualTo(1L);
    }

    @Test
    public void testCreate_ShouldThrowExceptionIfTraineeNotFound() {
        Mockito.when(traineeDAO.findById(1L)).thenReturn(null);

        Training training = new Training();
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        training.setTrainee(trainee);

        assertThatThrownBy(() -> trainingService.create(training))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Trainee with ID 1 does not exist.");
    }

    @Test
    public void testCreate_ShouldThrowExceptionIfTrainerNotFound() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        Mockito.when(traineeDAO.findById(1L)).thenReturn(trainee);

        Mockito.when(trainerDAO.findById(1L)).thenReturn(null);

        Training training = new Training();
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);

        assertThatThrownBy(() -> trainingService.create(training))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Trainer with ID 1 does not exist.");
    }

    @Test
    public void testCreate_ShouldThrowExceptionIfTrainingTypeNotFound() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        Mockito.when(traineeDAO.findById(1L)).thenReturn(trainee);

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        Mockito.when(trainerDAO.findById(1L)).thenReturn(trainer);

        Mockito.when(trainingTypeDAO.findById(1L)).thenReturn(null);

        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(new TrainingType());
        training.getTrainingType().setId(1L);

        assertThatThrownBy(() -> trainingService.create(training))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("TrainingType with ID 1 does not exist.");
    }
}
