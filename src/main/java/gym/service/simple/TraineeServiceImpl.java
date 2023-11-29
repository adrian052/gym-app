package gym.service.simple;

import gym.dao.DataAccessObject;
import gym.dao.db.beans.TrainingDbDaoImpl;
import gym.entities.Trainee;
import gym.entities.Training;
import gym.entities.User;
import gym.service.AuthService;
import gym.service.Credentials;
import gym.service.TraineeService;
import gym.service.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.naming.AuthenticationException;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("TraineeServiceImpl")
@Profile("memory")
public class TraineeServiceImpl implements TraineeService {
    protected DataAccessObject<Trainee> traineeDAO;

    protected DataAccessObject<Training> trainingDao;
    protected DataAccessObject<User> userDAO;
    protected AuthService authService;

    protected static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    public void setTraineeDAO(DataAccessObject<Trainee> traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setUserDAO(DataAccessObject<User> userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setTrainingDAO(DataAccessObject<Training> trainingDao) {
        this.trainingDao = trainingDao;
    }


    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Credentials create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        ValidationUtil.validateNotNull("firstName",firstName,"lastName",lastName);
        User user = UserCreationService.createUser(firstName, lastName, isActive,userDAO);
        traineeDAO.save(new Trainee(null, dateOfBirth, address,user));
        return new Credentials(user.getUsername(), user.getPassword());
    }

    @Override
    public Trainee update(Credentials credentials,  Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) throws AuthenticationException {
        ValidationUtil.validateNotNull("id",id,"firstName",firstName,"lastName",lastName);
        Trainee trainee = traineeDAO.findById(id);
        if (trainee != null) {
            User user = trainee.getUser();
            authService.authenticationFlow(credentials, user);
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
    public boolean delete(Credentials credentials, Long id) throws AuthenticationException {
        ValidationUtil.validateNotNull("id",id);
        authService.authenticationFlow(credentials,traineeDAO.findById(id).getUser());
        return traineeDAO.delete(id);
    }

    @Override
    public Trainee select(Long id) {
        ValidationUtil.validateNotNull("id",id);
        return traineeDAO.findById(id);
    }
}
