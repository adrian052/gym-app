package gym.service.simple;

import gym.dao.DataAccessObject;
import gym.dao.db.TrainerDbDao;
import gym.dao.db.TrainingTypeDbDao;
import gym.dao.db.UserDbDao;
import gym.entities.Trainer;
import gym.entities.User;
import gym.entities.TrainingType;
import gym.service.AuthService;
import gym.service.Credentials;
import gym.service.TrainerService;
import gym.service.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.xml.crypto.Data;
import java.util.Map;

@Service
@Profile("memory")
public class TrainerServiceImpl implements TrainerService {
    protected DataAccessObject<Trainer> trainerDAO;
    protected DataAccessObject<TrainingType> trainingTypeDAO;
    protected DataAccessObject<User> userDAO;

    protected AuthService authService;

    protected static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    public void setTrainerDAO(TrainerDbDao trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setTrainingTypeDAO(TrainingTypeDbDao trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    @Autowired
    public void setUserDAO(UserDbDao userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Trainer create(String firstName, String lastName, boolean isActive, Long specialization) {
        ValidationUtil.validateNotNull(Map.of("firstName",firstName,"lastName",lastName, "specialization",specialization));
        TrainingType trainingType = trainingTypeDAO.findById(specialization);
        return trainerDAO.save(new Trainer(null, trainingType,
                UserCreationService.createUser(firstName, lastName, isActive, userDAO)));
    }

    @Override
    public Trainer update(Credentials credentials, Long id, String firstName, String lastName, boolean isActive, Long specialization) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("credentials",credentials,"id",id, "firstName",firstName, "lastName",lastName));
        Trainer trainer = trainerDAO.findById(id);
        if (trainer != null) {
            User user = trainer.getUser();
            authService.authenticationFlow(credentials, user);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setActive(isActive);
            userDAO.save(user);
            TrainingType trainingType = trainingTypeDAO.findById(specialization);
            trainer.setSpecialization(trainingType);
            return trainerDAO.save(trainer);
        }else{
            logger.error("Unable to update the trainee, entity not found");
            throw new EntityNotFoundException("Unable to update the trainee, entity not found");
        }
    }
    @Override
    public Trainer select(Long id) {
        ValidationUtil.validateNotNull(Map.of("id",id));
        return trainerDAO.findById(id);
    }

}