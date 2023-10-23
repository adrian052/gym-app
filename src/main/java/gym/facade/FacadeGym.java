package gym.facade;


import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;

import java.util.Date;
import java.util.List;

public interface FacadeGym {
    List<Trainee> selectAllTrainees();

    Trainee selectTrainee(Long id);

    void createTrainee(String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    void updateTrainee(Long id, String firstName, String lastName, boolean isActive, Date dateOfBirth, String address);

    boolean deleteTrainee(Long id);

    List<Trainer> selectAllTrainers();

    Trainer selectTrainer(Long id);

    void createTrainer(Long specializationId, String firstName, String lastName, boolean isActive);

    void updateTrainer(Long id, Long specializationId, String firstName, String lastName, boolean isActive);

    List<Training> selectAllTrainings();

    Training selectTraining(Long id);

    void createTraining(Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration);
}