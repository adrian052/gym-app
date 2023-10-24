package gym.service;

import gym.dao.TraineeDAO;
import gym.entities.Trainee;
import gym.entities.User;
import gym.dao.DataAccessObject;
import gym.dao.UserDAO;
import gym.service.implementations.TraineeServiceImpl;
import gym.storage.InMemoryGymStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TraineeServiceImplTest {

    private TraineeService traineeService;
    private DataAccessObject<Trainee> traineeDAO;
    private DataAccessObject<User> userDAO;

    @Before
    public void setUp() {
        InMemoryGymStorage storage = new InMemoryGymStorage();
        traineeDAO = new TraineeDAO();
        ((TraineeDAO)traineeDAO).setStorage(storage);
        userDAO = new UserDAO();
        ((UserDAO)userDAO).setStorage(storage);

        traineeService = new TraineeServiceImpl();
        ((TraineeServiceImpl)traineeService).setTraineeDAO(traineeDAO);
        ((TraineeServiceImpl)traineeService).setUserDAO(userDAO);
    }

    @Test
    public void givenValidRequest_whenCreateNewTrainee_thenShouldBeCreated() {

        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;
        Date dateOfBirth = new Date();
        String address = "Address";

        Long traineeId = traineeService.create(firstName, lastName, isActive, dateOfBirth, address);

        assertNotNull(traineeId);
    }

    @Test
    public void givenExistingTrainee_whenUpdateTrainee_thenShouldBeUpdated() {

        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;
        Date dateOfBirth = new Date();
        String address = "Address";
        Long traineeId = traineeService.create(firstName, lastName, isActive, dateOfBirth, address);

        boolean isUpdated = traineeService.update(traineeId, "Jane", "Smith", true, new Date(), "New Address");

        assertTrue(isUpdated);
    }

    @Test
    public void givenExistingTrainee_whenDeleteTrainee_thenShouldBeDeleted() {
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;
        Date dateOfBirth = new Date();
        String address = "Address";
        Long traineeId = traineeService.create(firstName, lastName, isActive, dateOfBirth, address);

        boolean isDeleted = traineeService.delete(traineeId);

        assertTrue(isDeleted);
    }

    @Test
    public void givenExistingTrainee_whenSelectTrainee_thenShouldReturnTrainee() {
        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;
        Date dateOfBirth = new Date();
        String address = "Address";
        Long traineeId = traineeService.create(firstName, lastName, isActive, dateOfBirth, address);

        Trainee selectedTrainee = traineeService.select(traineeId);

        assertNotNull(selectedTrainee);
    }

    @Test
    public void givenRepeatedNameAndLastName_whenCreateNewTrainee_thenUsernamesShouldBeDifferent() {

        String firstName = "John";
        String lastName = "Doe";
        boolean isActive = true;
        Date dateOfBirth = new Date();
        String address = "Address";

        Long traineeId1 = traineeService.create(firstName, lastName, isActive, dateOfBirth, address);
        Long traineeId2 = traineeService.create(firstName, lastName, isActive, dateOfBirth, address);

        Trainee trainee1 = traineeService.select(traineeId1);
        Trainee trainee2 = traineeService.select(traineeId2);

        assertNotNull(traineeId1);
        assertNotNull(traineeId2);
        assertNotEquals(trainee1.getUser().getUsername(), trainee2.getUser().getUsername());
    }
    @Test
    public void givenNonExistentTrainee_whenUpdateTrainee_thenShouldReturnFalse() {
        Long nonExistentTraineeId = 12345L;
        boolean isUpdated = traineeService.update(nonExistentTraineeId, "Jane", "Smith", true, new Date(), "New Address");
        assertFalse(isUpdated);
    }
    @Test
    public void givenNonExistentTrainee_whenDeleteTrainee_thenShouldReturnFalse() {
        Long nonExistentTraineeId = 12345L;
        boolean isDeleted = traineeService.delete(nonExistentTraineeId);
        assertFalse(isDeleted);
    }


}
