package gym.dao.inmemory.storage;

import gym.entities.*;

import java.util.Map;

public interface GymStorage {
    Map<Long, Trainer> getTrainers();

    Map<Long, Trainee> getTrainees();

    Map<Long, Training> getTrainings();

    Map<Long, TrainingType> getTrainingTypes();

    long getNextId(Class<?> entityType);

    Map<Long, User> getUsers();
    void initializeData();

    void setJsonFilePath(String jsonFilePath);
    String getJsonFilePath();

}
