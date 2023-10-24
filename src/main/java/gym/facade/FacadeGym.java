package gym.facade;


import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;

import java.util.Date;

public interface FacadeGym {

    Trainee selectTrainee(Long id);

    Long createTrainee(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    boolean updateTrainee(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    boolean deleteTrainee(Long id);


    Trainer selectTrainer(Long id);

    Long createTrainer(String firstName, String lastName, boolean isActive, Long specializationId);

    boolean updateTrainer(Long id, String firstName, String lastName, boolean isActive,Date birthDate,String address, Long specializationId);

    Training selectTraining(Long id);

    Long createTraining(Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration);
}