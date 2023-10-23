package gym.storage;

import gym.entities.*;

import java.util.Map;

public interface GymStorage {
    Map<Long, Trainer> getTrainers();

    Map<Long, Trainee> getTrainees();

    Map<Long, Training> getTrainings();

    Map<Long, TrainingType> getTrainingTypes();

    Map<Long, User> getUsers();

}
