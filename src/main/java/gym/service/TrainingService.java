package gym.service;

import gym.entities.Training;
import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;

public interface TrainingService {
    Training create(Credentials trainerCredentials, Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration) throws AuthenticationException;
    Training select(Long id);
    List<Training> getTraineeTrainingsByTrainingName(String username,String trainingName);
    List<Training> getTraineeTrainingsByDuration(String username,int minDuration, int maxDuration);
    List<Training> getTrainerTrainingsByTrainingName(String username,String trainingName);
    List<Training> getTrainerTrainingsByDuration(String username,int minDuration, int maxDuration);
}
