package gym.facade;

import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
import gym.service.Credentials;
import java.util.Date;

public interface FacadeGym {

    Trainee selectTrainee(Long id);


    Credentials createTrainee(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    Trainee updateTrainee(Credentials credentials, Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    boolean deleteTrainee(Credentials credentials, Long id);


    Trainer selectTrainer(Long id);

    Credentials createTrainer( String firstName, String lastName, boolean isActive, Long specializationId);

    Trainer updateTrainer(Credentials credentials, Long id, String firstName, String lastName, boolean isActive,Long specializationId);


    Training selectTraining(Long id);

    Training createTraining(Credentials trainerCredentials,  Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration);


}