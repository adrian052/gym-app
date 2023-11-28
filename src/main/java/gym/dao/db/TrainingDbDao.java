package gym.dao.db;

import gym.dao.DataAccessObject;
import gym.entities.Training;
import java.util.List;

public interface TrainingDbDao extends DataAccessObject<Training> {
    List<Training> getTraineeTrainingsByUsername(String traineeUsername);
    List<Training> getTrainerTrainingsByUsername(String traineeUsername);
}
