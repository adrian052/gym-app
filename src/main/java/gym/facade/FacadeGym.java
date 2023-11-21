package gym.facade;


import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;

import java.util.Date;

public interface FacadeGym {

    Trainee selectTrainee(Long id);

    Trainee createTrainee(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    Trainee updateTrainee(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    boolean deleteTrainee(Long id);


    Trainer selectTrainer(Long id);

    Trainer createTrainer(String firstName, String lastName, boolean isActive, Long specializationId);

    Trainer updateTrainer(Long id, String firstName, String lastName, boolean isActive,Date birthDate,String address, Long specializationId);

    Training selectTraining(Long id);

    Training createTraining(Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration);
}