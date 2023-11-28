package gym.service.db;

import gym.entities.Training;
import gym.service.TrainingService;
import java.util.List;

public interface TrainingDbService extends TrainingService {
    List<Training> getTraineeTrainingsByTrainingName(String username, String trainingName);

    List<Training> getTraineeTrainingsByDuration(String username, int minDuration, int maxDuration);

    List<Training> getTrainerTrainingsByTrainingName(String username, String trainingName);

    List<Training> getTrainerTrainingsByDuration(String username, int minDuration, int maxDuration);
}
