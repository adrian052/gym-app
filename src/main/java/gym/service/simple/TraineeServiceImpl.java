package gym.service.simple;

import gym.dao.TraineeDao;
import gym.dao.UserDao;
import gym.entities.Trainee;
import gym.entities.User;
import gym.service.AuthService;
import gym.service.Credentials;
import gym.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.Map;

@Service("TraineeServiceImpl")
public class TraineeServiceImpl implements TraineeService {
    private TraineeDao traineeDAO;
    private UserDao userDAO;
    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    public void setTraineeDAO(TraineeDao traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setUserDAO(UserDao userDAO) {
        this.userDAO = userDAO;
    }


    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Trainee create(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) {
        ValidationUtil.validateNotNull(Map.of("firstName",firstName,"lastName",lastName));
        return traineeDAO.save(new Trainee(null, dateOfBirth, address,
                UserCreationService.createUser(firstName, lastName, isActive,userDAO)));
    }

    @Override
    public Trainee update(Credentials credentials,  Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("id",id,"firstName",firstName,"lastName",lastName));
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
        ValidationUtil.validateNotNull(Map.of("id",id));
        authService.authenticationFlow(credentials,userDAO.findById(id));
        return traineeDAO.delete(id);
    }

    @Override
    public boolean deleteByUsername(Credentials credentials, String username) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("credentials",credentials,"username",username));
        User user = userDAO.findByUsername(username);
        authService.authenticationFlow(credentials,user);
        return traineeDAO.delete(user.getId());
    }


    @Override
    public Trainee select(Long id) {
        ValidationUtil.validateNotNull(Map.of("id",id));
        return traineeDAO.findById(id);
    }

    @Override
    public Trainee selectByUsername(String username) {
        ValidationUtil.validateNotNull(Map.of("username", username));
        return traineeDAO.findByUsername(username);
    }

    @Override
    public Trainee updatePassword(Credentials credentials, Long id, String newPassword) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("credentials",credentials,"id",id,"newPassword",newPassword));
        Trainee trainee = traineeDAO.findById(id);
        User traineeUser = trainee.getUser();
        authService.authenticationFlow(credentials,traineeUser);
        traineeUser.setPassword(newPassword);
        traineeDAO.save(trainee);
        return trainee;
    }

    @Override
    public Trainee updateStatus(Credentials credentials, Long id, boolean newStatus) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("id",id,"credentials",credentials));
        Trainee trainee = traineeDAO.findById(id);
        User user = trainee.getUser();
        authService.authenticationFlow(credentials,user);
        if (user.isActive() == newStatus) {
            logger.info("No change in status. Trainer ID: {}, Current Status: {}", id, newStatus);
        } else {
            user.setActive(newStatus);
            userDAO.save(user);
            logger.info("Status updated successfully. Trainer ID: {}, New Status: {}", id, newStatus);
        }
        return trainee;
    }
}
