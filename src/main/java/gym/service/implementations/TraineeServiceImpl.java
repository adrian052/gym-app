package gym.service.implementations;

import gym.dao.GenericDAO;
import gym.entities.Trainee;
import gym.entities.User;
import gym.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TraineeServiceImpl implements TraineeService {
    private GenericDAO<Trainee> traineeDAO;
    private GenericDAO<User> userDAO;

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    public void setTraineeDAO(GenericDAO<Trainee> traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setUserDAO(GenericDAO<User> userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Long create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        if (firstName == null || lastName == null) {
            logger.error("Failed to create Trainee: the following parameters cannot be null (firstName, lastName)");
            return null;
        }

        User user = UserCreationService.createUser(firstName, lastName, isActive, userDAO);
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
        if (id == null || firstName == null || lastName == null) {
            logger.error("Failed to update Trainee: the following parameters cannot be null (id, firstName, lastName)");
            return false;
        }

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

        logger.warn("Failed to update Trainee with ID: {} - Trainee not found.", id);

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if(id==null){
            logger.error("Failed to delete Trainee: the following parameters cannot be null (id)");
            return false;
        }
        Trainee trainee = traineeDAO.findById(id);

        if (trainee != null) {
            Long userId = trainee.getUser().getId();
            //Data consistency is assured, because every time we create a trainee
            //we have to associate it with a User.
            userDAO.delete(userId);
            return traineeDAO.delete(id);
        }

        logger.warn("Failed to delete Trainee with ID: {} - Trainee not found.", id);

        return false;
    }

    @Override
    public Trainee select(Long id) {
        if(id==null){
            logger.error("Failed to select Trainee: the following parameters cannot be null (id)");
            return null;
        }
        return traineeDAO.findById(id);
    }

}
