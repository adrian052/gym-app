package gym.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import gym.entities.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface GymStorage {
    Map<Long, Trainer> getTrainers();

    Map<Long, Trainee> getTrainees();

    Map<Long, Training> getTrainings();

    Map<Long, TrainingType> getTrainingTypes();

    Map<Long, User> getUsers();

    void initializeData();
}
