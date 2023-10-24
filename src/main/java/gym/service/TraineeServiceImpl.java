package gym.service;

import gym.dao.DataAccessObject;
import gym.dao.UserDAO;
import gym.entities.Trainee;
import gym.entities.User;
import gym.service.interfaces.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class TraineeServiceImpl implements TraineeService {
    private DataAccessObject<Trainee> traineeDAO;
    private DataAccessObject<User> userDAO;

    private final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    public void setTraineeDAO(DataAccessObject<Trainee> traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setUserDAO(DataAccessObject<User> userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Long create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        String username = generateUniqueUsername(firstName, lastName);
        String password = generateRandomPassword();

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setActive(isActive);
        user.setUsername(username);
        user.setPassword(password);

        logger.info("Generated username for new Trainee: {}", username);
        logger.info("Generated password for new Trainee: {}", password);

        userDAO.save(user);

        Trainee trainee = new Trainee();
        trainee.setUser(user);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);

        Long traineeId = traineeDAO.save(trainee);

        logger.info("Created a new Trainee with ID: {}", traineeId);

        return traineeId;
    }

    @Override
    public boolean update(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        Trainee trainee = traineeDAO.findById(id);

        if (trainee != null) {
            User user = trainee.getUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setActive(isActive);

            userDAO.save(user);

            trainee.setDateOfBirth(dateOfBirth);
            trainee.setAddress(address);

            boolean isUpdated = traineeDAO.save(trainee) != null;

            if (isUpdated) {
                logger.info("Updated Trainee with ID: {}", id);
            } else {
                logger.error("Failed to update Trainee with ID: {}", id);
            }

            return isUpdated;
        }

        logger.warn("Failed to update Trainee with ID: {} - Trainee not found.",id);

        return false;
    }

    @Override
    public boolean delete(Long id) {
        Trainee trainee = traineeDAO.findById(id);

        if (trainee != null) {
            Long userId = trainee.getUser().getId();

            if (userId != null) {
                boolean isUserDeleted = userDAO.delete(userId);

                if (isUserDeleted) {
                    logger.info("Deleted associated User with ID: {}", userId);
                } else {
                    logger.error("Failed to delete associated User with ID: {}", userId);
                }
            }

            boolean isTraineeDeleted = traineeDAO.delete(id);

            if (isTraineeDeleted) {
                logger.info("Deleted Trainee with ID: {}", id);
            } else {
                logger.error("Failed to delete Trainee with ID: {}", id);
            }

            return isTraineeDeleted;
        }

        logger.warn("Failed to delete Trainee with ID: {} - Trainee not found.", id);

        return false;
    }

    @Override
    public Trainee select(Long id) {
        return traineeDAO.findById(id);
    }

    private String generateUniqueUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String generatedUsername = baseUsername;

        while (((UserDAO) userDAO).isUsernameTaken(generatedUsername)) {
            generatedUsername = baseUsername + "." + generateRandomNumber();

        }

        return generatedUsername;
    }


    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();


        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }


    private String generateRandomNumber() {

        int randomInt = random.nextInt((999999 - 100000) + 1) + 100000;
        return String.format("%06d", randomInt);
    }
}
