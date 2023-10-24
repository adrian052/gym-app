package gym.service;

import gym.dao.DataAccessObject;
import gym.dao.UserDAO;
import gym.entities.Trainer;
import gym.entities.User;
import gym.entities.TrainingType;
import gym.service.interfaces.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class TrainerServiceImpl implements TrainerService {
    private DataAccessObject<Trainer> trainerDAO;
    private DataAccessObject<TrainingType> trainingTypeDAO;
    private DataAccessObject<User> userDAO;

    private final Random random = new Random();

    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    public void setTrainerDAO(DataAccessObject<Trainer> trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setTrainingTypeDAO(DataAccessObject<TrainingType> trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    @Autowired
    public void setUserDAO(DataAccessObject<User> userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Long create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address, Long specialization) {
        String username = generateUniqueUsername(firstName, lastName);
        String password = generateRandomPassword();

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setActive(isActive);
        user.setUsername(username);
        user.setPassword(password);

        logger.info("Generated username for new Trainer: {}", username);
        logger.info("Generated password for new Trainer: {}", password);

        userDAO.save(user);

        TrainingType trainingType = trainingTypeDAO.findById(specialization);
        if (trainingType == null) {
            return null;
        }

        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setSpecialization(trainingType);

        logger.info("Created a new Trainer with ID: {}", trainerDAO.save(trainer));

        return trainerDAO.save(trainer);
    }

    @Override
    public boolean update(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address, Long specialization) {
        Trainer trainer = trainerDAO.findById(id);

        if (trainer != null) {
            User user = trainer.getUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setActive(isActive);

            userDAO.save(user);

            TrainingType trainingType = trainingTypeDAO.findById(specialization);
            if (trainingType == null) {
                return false;
            }

            trainer.setSpecialization(trainingType);

            boolean isUpdated = trainerDAO.save(trainer) != null;

            if (isUpdated) {
                logger.info("Updated Trainer with ID: {}", id);
                logger.info("Updated Trainer username: {}", user.getUsername());
                logger.info("Updated Trainer password: {}", user.getPassword());
            } else {
                logger.error("Failed to update Trainer with ID: {}", id);
            }

            return isUpdated;
        }

        logger.warn("Failed to update Trainer with ID: {} - Trainer not found.",id);

        return false;
    }

    @Override
    public Trainer select(Long id) {
        return trainerDAO.findById(id);
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
