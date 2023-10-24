package gym.service;

import gym.dao.*;
import gym.entities.Training;
import gym.entities.TrainingType;
import gym.entities.Trainer;
import gym.entities.Trainee;
import gym.service.implementations.TrainingServiceImpl;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TrainingServiceImplTest {
    private TrainingService trainingService;
    private GenericDAO<Trainee> traineeDAO;
    private GenericDAO<Trainer> trainerDAO;
    private GenericDAO<TrainingType> trainingTypeDAO;
    private GenericDAO<Training> trainingDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        traineeDAO = new TraineeDAO();
        trainerDAO = new TrainerDAO();
        trainingTypeDAO = new TrainingTypeDAO();
        trainingDAO = new TrainingDAO();

        ((TraineeDAO)traineeDAO).setStorage(storage);
        ((TrainerDAO)trainerDAO).setStorage(storage);
        ((TrainingTypeDAO)trainingTypeDAO).setStorage(storage);
        ((TrainingDAO)trainingDAO).setStorage(storage);

        trainingService = new TrainingServiceImpl();
        ((TrainingServiceImpl)trainingService).setTraineeDAO(traineeDAO);
        ((TrainingServiceImpl)trainingService).setTrainerDAO(trainerDAO);
        ((TrainingServiceImpl)trainingService).setTrainingTypeDAO(trainingTypeDAO);
        ((TrainingServiceImpl)trainingService).setTrainingDAO(trainingDAO);
    }

    @Test
    public void givenValidData_whenCreateNewTraining_thenShouldReturnTrainingId() {
        // Create and save a valid Trainee
        Trainee trainee = new Trainee();
        Long traineeId = traineeDAO.save(trainee);

        // Create and save a valid Trainer
        Trainer trainer = new Trainer();
        Long trainerId = trainerDAO.save(trainer);

        // Create and save a valid TrainingType
        TrainingType trainingType = new TrainingType();
        Long trainingTypeId = trainingTypeDAO.save(trainingType);

        String trainingName = "Test Training";
        Date trainingDate = new Date();
        int trainingDuration = 60;

        Long trainingId = trainingService.create(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
        assertNotNull(trainingId);
    }

    @Test
    public void givenInvalidData_whenCreateNewTraining_thenShouldReturnNull() {
        Long traineeId = 12345L; // Non-existent Trainee
        Long trainerId = 2L;
        String trainingName = "Test Training";
        Date trainingDate = new Date();
        int trainingDuration = 60;
        Long trainingTypeId = 3L;

        Long trainingId = trainingService.create(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
        assertNull(trainingId);
    }

    @Test
    public void givenValidTraining_whenSelectTraining_thenShouldReturnTraining() {
        // Create and save a valid Trainee
        Trainee trainee = new Trainee();
        Long traineeId = traineeDAO.save(trainee);

        // Create and save a valid Trainer
        Trainer trainer = new Trainer();
        Long trainerId = trainerDAO.save(trainer);

        // Create and save a valid TrainingType
        TrainingType trainingType = new TrainingType();
        Long trainingTypeId = trainingTypeDAO.save(trainingType);

        String trainingName = "Test Training";
        Date trainingDate = new Date();
        int trainingDuration = 60;

        Long trainingId = trainingService.create(traineeId, trainerId, trainingName, trainingTypeId, trainingDate, trainingDuration);
        assertNotNull(trainingId);

        Training selectedTraining = trainingService.select(trainingId);
        assertNotNull(selectedTraining);
    }

    @Test
    public void givenInvalidTrainingId_whenSelectTraining_thenShouldReturnNull() {
        Long invalidTrainingId = 12345L; // Non-existent Training
        Training selectedTraining = trainingService.select(invalidTrainingId);
        assertNull(selectedTraining);
    }
}
