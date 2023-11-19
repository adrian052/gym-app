package gym.service.implementations;

import gym.dao.DataAccessObject;
import gym.entities.Trainer;
import gym.entities.User;
import gym.entities.TrainingType;
import gym.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TrainerServiceImpl implements TrainerService {
    private DataAccessObject<Trainer> trainerDAO;
    private DataAccessObject<TrainingType> trainingTypeDAO;
    private DataAccessObject<User> userDAO;


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
    public Long create(String firstName, String lastName, boolean isActive, Long specialization) {
        if (firstName == null || lastName == null) {
            logger.error("Failed to create Trainer: the following parameters cannot be null (firstName, lastName)");
            return null;
        }
        User user = UserCreationService.createUser(firstName, lastName, isActive, userDAO);
        TrainingType trainingType = trainingTypeDAO.findById(specialization);
        if (trainingType == null) {
            return null;
        }
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setSpecialization(trainingType);

        Long trainerId = trainerDAO.save(trainer).getId();
        logger.info("Created a new Trainer with ID: {}", trainerId);

        return trainerId;
    }

    @Override
    public boolean update(Long id, String firstName, String lastName, boolean isActive, Long specialization) {
        if(id==null || firstName==null || lastName==null){
            logger.error("Failed to create Trainer: the following parameters cannot be null (id, firstName, lastName)");
            return false;
        }
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
            } else {
                logger.error("Failed to update Trainer with ID: {}", id);
            }

            return isUpdated;
        }

        logger.warn("Failed to update Trainer with ID: {} - Trainer not found.", id);

        return false;
    }


    @Override
    public Trainer select(Long id) {
        if (id == null) {
            logger.error("Failed to select Trainer: the following parameters cannot be null (id)");
            return null;
        }
        return trainerDAO.findById(id);
    }

}
