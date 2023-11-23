package gym.service;

import gym.entities.Training;

import java.util.Date;

public interface TrainingService {
    Training create(Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, int trainingDuration);
    Training select(Long id);
}
