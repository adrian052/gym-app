package gym.service.simple;

import gym.dao.*;
import gym.entities.Training;
import gym.entities.User;
import gym.service.AuthService;
import gym.service.Credentials;
import gym.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {
    private TraineeDao traineeDAO;
    private TrainerDao trainerDAO;
    private TrainingTypeDao trainingTypeDAO;
    private TrainingDao trainingDAO;

    private AuthService authService;

    @Autowired
    public void setTraineeDAO(TraineeDao traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setTrainerDAO(TrainerDao trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setTrainingTypeDAO(TrainingTypeDao trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    @Autowired
    public void setTrainingDAO(TrainingDao trainingDAO) {
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

    @Override
    public List<Training> getTraineeTrainingsByTrainingName(String username, String trainingName) {
        ValidationUtil.validateNotNull(Map.of("username",username,"trainingName",trainingName));
        return trainingDAO.getTraineeTrainingsByUsername(username).stream()
                .filter(training -> training.getTrainingName().equalsIgnoreCase(trainingName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTraineeTrainingsByDuration(String username, int minDuration, int maxDuration) {
        ValidationUtil.validateNotNull(Map.of("username",username));
        return trainingDAO.getTraineeTrainingsByUsername(username).stream()
                .filter(training -> training.getTrainingDuration() >= minDuration && training.getTrainingDuration() <= maxDuration)
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainerTrainingsByTrainingName(String username, String trainingName) {
        ValidationUtil.validateNotNull(Map.of("username",username,"trainingName",trainingName));
        return trainingDAO.getTrainerTrainingsByUsername(username).stream()
                .filter(training -> training.getTrainingName().equalsIgnoreCase(trainingName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainerTrainingsByDuration(String username, int minDuration, int maxDuration) {
        ValidationUtil.validateNotNull(Map.of("username",username));
        return trainingDAO.getTrainerTrainingsByUsername(username).stream()
                .filter(training -> training.getTrainingDuration() >= minDuration && training.getTrainingDuration() <= maxDuration)
                .collect(Collectors.toList());
    }
}
