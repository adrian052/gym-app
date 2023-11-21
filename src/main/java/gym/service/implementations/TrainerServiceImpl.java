package gym.service.implementations;

import gym.dao.DataAccessObject;
import gym.entities.Trainer;
import gym.entities.User;
import gym.entities.TrainingType;
import gym.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
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
    public Trainer create(String firstName, String lastName, boolean isActive, Long specialization) {
        if (firstName == null || lastName == null) {
            logger.error("(firstName, lastName, specialization) are not allowed to be null");
            throw new IllegalArgumentException("(firstName, lastName, specialization) are not allowed to be null");
        }
        TrainingType trainingType = trainingTypeDAO.findById(specialization);
        return trainerDAO.save(new Trainer(null, trainingType,
                UserCreationService.createUser(firstName, lastName, isActive, userDAO)));
    }

    @Override
    public Trainer update(Long id, String firstName, String lastName, boolean isActive, Long specialization) {
        if(id==null || firstName==null || lastName==null){
            logger.error("(id, firstName, lastName, specialization) are not allowed to be null");
            throw new IllegalArgumentException("(id, firstName, lastName, specialization) are not allowed to be null");
        }
        Trainer trainer = trainerDAO.findById(id);
        if (trainer != null) {
            User user = trainer.getUser();
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
        if (id == null) {
            logger.error("(id) is not allowed to be null");
            throw new IllegalArgumentException("(id) is not allowed to be null");
        }
        return trainerDAO.findById(id);
    }

}
