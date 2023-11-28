package gym.service.simple;

import gym.dao.DataAccessObject;
import gym.entities.Trainee;
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
import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.Map;

@Service("TraineeServiceImpl")
@Profile("memory")
public class TraineeServiceImpl implements TraineeService {
    protected DataAccessObject<Trainee> traineeDAO;
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
    public Trainee select(Long id) {
        ValidationUtil.validateNotNull(Map.of("id",id));
        return traineeDAO.findById(id);
    }


}
