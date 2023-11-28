package gym.facade;

import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.service.Credentials;
import java.util.Date;
import java.util.List;

public interface FacadeGym {

    Trainee selectTrainee(Long id);

    Trainee selectTraineeByUsername(String username);

    Trainee createTrainee(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    Trainee updateTrainee(Credentials credentials, Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    Trainee updateTraineePassword(Credentials credentials, Long id, String newPassword);

    Trainee activateTrainee(Credentials credentials, Long id);

    Trainee deactivateTrainee(Credentials credentials, Long id);

    boolean deleteTrainee(Credentials credentials, Long id);

    boolean deleteTraineeByUsername(Credentials credentials, String username);

    Trainer selectTrainer(Long id);

    Trainer selectTrainerByUsername(String username);

    Trainer createTrainer(Credentials credentials, String firstName, String lastName, boolean isActive, Long specializationId);

    Trainer updateTrainer(Credentials credentials, Long id, String firstName, String lastName, boolean isActive,Date birthDate,String address, Long specializationId);

    Trainer updateTrainerPassword(Credentials credentials, Long id, String newPassword);

    Trainer activateTrainer(Credentials credentials, Long id);

    Trainer deactivateTrainer(Credentials credentials, Long id);

    Training selectTraining(Long id);

    Training createTraining(Credentials trainerCredentials,  Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration);

    List<Training> getTraineeTrainingsByTrainingName(String username,String trainingName);

    List<Training> getTraineeTrainingsByDuration(String username, int minDuration, int maxDuration);

    List<Training> getTrainerTrainingsByTrainingName(String username,String trainingName);

    List<Training> getTrainerTrainingsByDuration(String username, int minDuration, int maxDuration);

}