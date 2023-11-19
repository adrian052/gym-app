package gym.service;

import gym.dao.*;
import gym.entities.Trainer;
import gym.entities.TrainingType;

import gym.service.implementations.TrainerServiceImpl;
import gym.storage.GymStorage;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

public class TrainerServiceImplTest {
    private TrainerService trainerService;
    private DataAccessObject<TrainingType> trainingTypeDAO;

    @Before
    public void setUp() {
        GymStorage storage = new InMemoryGymStorage();
        TrainerDAO trainerDAO = new TrainerDAO();
        trainerDAO.setStorage(storage);
        trainingTypeDAO = new TrainingTypeDAO();
        ((TrainingTypeDAO)trainingTypeDAO).setStorage(storage);
        UserDAO userDAO = new UserDAO();
        userDAO.setStorage(storage);

        trainerService = new TrainerServiceImpl();

        ((TrainerServiceImpl)trainerService).setTrainerDAO(trainerDAO);
        ((TrainerServiceImpl)trainerService).setTrainingTypeDAO(trainingTypeDAO);
        ((TrainerServiceImpl)trainerService).setUserDAO(userDAO);
    }

    @Test
    public void givenNonExistentTrainingType_whenCreateNewTrainer_thenShouldReturnNull() {
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;
        Long specialization = 12345L;

        Long trainerId = trainerService.create(firstName, lastName, isActive,  specialization);

        assertNull(trainerId);
    }

    @Test
    public void givenExistentTrainingType_whenCreateNewTrainer_thenShouldReturnTrainerId() {
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;
        Date dateOfBirth = new Date();
        String address = "Address";


        TrainingType newTrainingType = new TrainingType();
        newTrainingType.setTrainingTypeName("Nuevo Tipo de Entrenamiento");
        Long specialization = trainingTypeDAO.save(newTrainingType).getId();

        Long trainerId = trainerService.create(firstName, lastName, isActive, specialization);
        System.out.println(trainerId);
        assertNotNull(trainerId);
    }

    @Test
    public void givenExistentTrainingType_whenUpdateTrainer_thenShouldReturnTrue() {
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;


        TrainingType newTrainingType = new TrainingType();
        newTrainingType.setTrainingTypeName("Nuevo Tipo de Entrenamiento");
        Long specialization = trainingTypeDAO.save(newTrainingType).getId();

        Long trainerId = trainerService.create(firstName, lastName, isActive,  specialization);

        String updatedFirstName = "UpdatedFirstName";
        String updatedLastName = "UpdatedLastName";
        boolean updatedIsActive = false;

        boolean isUpdated = trainerService.update(trainerId, updatedFirstName, updatedLastName, updatedIsActive, specialization);

        assertTrue(isUpdated);

        Trainer updatedTrainer = trainerService.select(trainerId);
        assertEquals(updatedFirstName, updatedTrainer.getUser().getFirstName());
        assertEquals(updatedLastName, updatedTrainer.getUser().getLastName());
        assertEquals(updatedIsActive, updatedTrainer.getUser().isActive());
    }

    @Test
    public void givenSameNameAndLastName_whenCreateNewTrainers_thenShouldHaveDifferentUsernames() {
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;

        TrainingType newTrainingType = new TrainingType();
        newTrainingType.setTrainingTypeName("Nuevo Tipo de Entrenamiento");
        Long specialization = trainingTypeDAO.save(newTrainingType).getId();

        Long trainerId1 = trainerService.create(firstName, lastName, isActive,  specialization);
        Long trainerId2 = trainerService.create(firstName, lastName, isActive,  specialization);

        assertNotNull(trainerId1);
        assertNotNull(trainerId2);

        Trainer trainer1 = trainerService.select(trainerId1);
        Trainer trainer2 = trainerService.select(trainerId2);

        assertEquals(firstName, trainer1.getUser().getFirstName());
        assertEquals(lastName, trainer1.getUser().getLastName());
        assertEquals(firstName, trainer2.getUser().getFirstName());
        assertEquals(lastName, trainer2.getUser().getLastName());

        assertNotEquals(trainer1.getUser().getUsername(), trainer2.getUser().getUsername());
    }


}
