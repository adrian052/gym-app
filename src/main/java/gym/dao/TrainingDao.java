package gym.dao;

import gym.entities.Training;
import java.util.List;

public interface TrainingDao extends CRUD<Training>{
    List<Training> getTraineeTrainingsByUsername(String traineeUsername);
    List<Training> getTrainerTrainingsByUsername(String traineeUsername);
}
