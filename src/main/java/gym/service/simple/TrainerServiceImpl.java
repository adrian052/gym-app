package gym.service.simple;

import gym.dao.TrainerDao;
import gym.dao.TrainingTypeDao;
import gym.dao.UserDao;
import gym.entities.Trainer;
import gym.entities.User;
import gym.entities.TrainingType;
import gym.service.AuthService;
import gym.service.Credentials;
import gym.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Map;

@Service
public class TrainerServiceImpl implements TrainerService {
    private TrainerDao trainerDAO;
    private TrainingTypeDao trainingTypeDAO;
    private UserDao userDAO;

    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    public void setTrainerDAO(TrainerDao trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setTrainingTypeDAO(TrainingTypeDao trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
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

    @Override
    public Trainer selectByUsername(String username) {
        ValidationUtil.validateNotNull(Map.of("username",username));
        return trainerDAO.findByUsername(username);
    }

    @Override
    public Trainer updatePassword(Credentials credentials, Long id, String newPassword) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("credentials",credentials,"id",id, "newPassword",newPassword));
        Trainer trainer = trainerDAO.findById(id);
        User user = trainer.getUser();
        authService.authenticationFlow(credentials, user);
        user.setPassword(newPassword);
        trainerDAO.save(trainer);
        return trainer;
    }

    @Override
    public Trainer updateStatus(Credentials credentials, Long id, boolean newStatus) throws AuthenticationException {
            ValidationUtil.validateNotNull(Map.of("credentials",credentials,"id",id));
            Trainer trainer = trainerDAO.findById(id);
            User user = trainer.getUser();
            authService.authenticationFlow(credentials,user);
            if (user.isActive() == newStatus) {
                logger.info("No change in status. Trainer ID: {}, Current Status: {}", id, newStatus);
            } else {
                user.setActive(newStatus);
                userDAO.save(user);
                logger.info("Status updated successfully. Trainer ID: {}, New Status: {}", id, newStatus);
            }
            return trainer;
    }
}
