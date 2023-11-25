package gym.service.implementations;

import gym.dao.DataAccessObject;

import gym.entities.Trainee;
import gym.entities.User;
import gym.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TraineeServiceImpl implements TraineeService {
    private DataAccessObject<Trainee> traineeDAO;
    private DataAccessObject<User> userDAO;
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private static final String ID_MESSAGE = "(id) is not allowed to be null";
    @Autowired
    public void setTraineeDAO(DataAccessObject<Trainee> traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setUserDAO(DataAccessObject<User> userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Trainee create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        if (firstName == null || lastName == null) {
            logger.error("(firstName, lastName) are not allowed to be null");
            throw new IllegalArgumentException("(firstName, lastName) are not allowed to be null");
        }
        return traineeDAO.save(new Trainee(null, dateOfBirth, address,
                UserCreationService.createUser(firstName, lastName, isActive,userDAO)));
    }

    @Override
    public Trainee update(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        if (id == null || firstName == null || lastName == null) {
            logger.error("(id, firstName, lastName) are not allowed to be null");
            throw new IllegalArgumentException("(id, firstName, lastName) are not allowed to be null");
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
            return traineeDAO.save(trainee);
        }else {
            logger.error("Unable to update the trainee, entity not found");
            throw new EntityNotFoundException("Unable to update the trainee, entity not found");
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if(id==null){
            logger.error(ID_MESSAGE);
            throw new IllegalArgumentException(ID_MESSAGE);
        }
        return traineeDAO.delete(id);
    }

    @Override
    public Trainee select(Long id) {
        if(id==null){
            logger.error(ID_MESSAGE);
            throw new IllegalArgumentException(ID_MESSAGE);
        }
        return traineeDAO.findById(id);
    }
}
