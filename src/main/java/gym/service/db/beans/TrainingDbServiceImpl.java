package gym.service.db.beans;

import gym.dao.db.TrainingDbDao;
import gym.entities.Training;
import gym.service.ValidationUtil;
import gym.service.db.TrainingDbService;
import gym.service.simple.TrainingServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@Profile("db")
public class TrainingDbServiceImpl extends TrainingServiceImpl implements TrainingDbService {
    @Override
    public List<Training> getTraineeTrainingsByTrainingName(String username, String trainingName) {
        ValidationUtil.validateNotNull("username",username,"trainingName",trainingName);
        return ((TrainingDbDao)trainingDAO).getTraineeTrainingsByUsername(username).stream()
                .filter(training -> training.getTrainingName().equalsIgnoreCase(trainingName))
                .toList();
    }

    @Override
    public List<Training> getTraineeTrainingsByDuration(String username, int minDuration, int maxDuration) {
        ValidationUtil.validateNotNull("username",username);
        return ((TrainingDbDao)trainingDAO).getTraineeTrainingsByUsername(username).stream()
                .filter(training -> training.getTrainingDuration() >= minDuration && training.getTrainingDuration() <= maxDuration)
                .toList();
    }

    @Override
    public List<Training> getTrainerTrainingsByTrainingName(String username, String trainingName) {
        ValidationUtil.validateNotNull("username",username,"trainingName",trainingName);
        return ((TrainingDbDao)trainingDAO).getTrainerTrainingsByUsername(username).stream()
                .filter(training -> training.getTrainingName().equalsIgnoreCase(trainingName))
                .toList();
    }

    @Override
    public List<Training> getTrainerTrainingsByDuration(String username, int minDuration, int maxDuration) {
        ValidationUtil.validateNotNull("username",username);
        return ((TrainingDbDao)trainingDAO).getTrainerTrainingsByUsername(username).stream()
                .filter(training -> training.getTrainingDuration() >= minDuration && training.getTrainingDuration() <= maxDuration)
                .toList();
    }
}
