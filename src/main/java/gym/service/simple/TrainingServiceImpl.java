package gym.service.simple;

import gym.dao.DataAccessObject;
import gym.dao.db.TraineeDbDao;
import gym.dao.db.TrainerDbDao;
import gym.dao.db.TrainingTypeDbDao;
import gym.dao.db.TrainingDbDao;
import gym.entities.*;
import gym.service.AuthService;
import gym.service.Credentials;
import gym.service.TrainingService;
import gym.service.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.Map;


@Service
@Profile("memory")
public class TrainingServiceImpl implements TrainingService {
    protected DataAccessObject<Trainee> traineeDAO;
    protected DataAccessObject<Trainer> trainerDAO;
    protected DataAccessObject<TrainingType> trainingTypeDAO;
    protected DataAccessObject<Training> trainingDAO;

    private AuthService authService;

    @Autowired
    public void setTraineeDAO(TraineeDbDao traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setTrainerDAO(TrainerDbDao trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setTrainingTypeDAO(TrainingTypeDbDao trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    @Autowired
    public void setTrainingDAO(TrainingDbDao trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Autowired
    public void setAuthService(AuthService authService){
        this.authService = authService;
    }

    @Override
    public Training create(Credentials credentials, Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration) throws AuthenticationException {
        ValidationUtil.validateNotNull(Map.of("traineeId",traineeId, "trainerId",trainerId, "trainingName",trainingName,
                "trainingTypeId", trainingTypeId, "trainingDate",trainingDate));
        User user = trainerDAO.findById(traineeId).getUser();
        authService.authenticationFlow(credentials,user);
        Training training = new Training();
        training.setTrainee(traineeDAO.findById(traineeId));
        training.setTrainer(trainerDAO.findById(trainerId));
        training.setTrainingName(trainingName);
        training.setTrainingType(trainingTypeDAO.findById(trainingTypeId));
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(trainingDuration);
        return trainingDAO.save(training);
    }

    @Override
    public Training select(Long id){
        ValidationUtil.validateNotNull(Map.of("id",id));
        return trainingDAO.findById(id);
    }

}
