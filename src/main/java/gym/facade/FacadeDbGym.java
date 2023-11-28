package gym.facade;

import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.service.Credentials;
import java.util.List;

public interface FacadeDbGym extends FacadeGym{

    Trainee selectTraineeByUsername(String username);

    Trainee updateTraineePassword(Credentials credentials, Long id, String newPassword);

    Trainee activateTrainee(Credentials credentials, Long id);

    Trainee deactivateTrainee(Credentials credentials, Long id);

    boolean deleteTraineeByUsername(Credentials credentials, String username);

    Trainer selectTrainerByUsername(String username);

    Trainer updateTrainerPassword(Credentials credentials, Long id, String newPassword);

    Trainer activateTrainer(Credentials credentials, Long id);

    Trainer deactivateTrainer(Credentials credentials, Long id);

    List<Training> getTraineeTrainingsByTrainingName(String username, String trainingName);

    List<Training> getTraineeTrainingsByDuration(String username, int minDuration, int maxDuration);

    List<Training> getTrainerTrainingsByTrainingName(String username, String trainingName);

    List<Training> getTrainerTrainingsByDuration(String username, int minDuration, int maxDuration);
}