package gym.service;

import gym.entities.Training;
import javax.naming.AuthenticationException;
import java.util.Date;

public interface TrainingService {
    Training create(Credentials trainerCredentials, Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration) throws AuthenticationException;
    Training select(Long id);
}
