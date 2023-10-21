package gym.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import gym.entities.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("storage")
@PropertySource("context.properties")
public class InMemoryGymStorage implements GymStorage{
    @Value("${data.json.file.path}")
    private String jsonFilePath;
    private Map<Long, Trainer> trainers;
    private Map<Long, Trainee> trainees;
    private Map<Long, Training> trainings;
    private Map<Long, TrainingType> trainingTypes;
    private Map<Long, User> users;

    public InMemoryGymStorage() {
        this.trainers = new HashMap<>();
        this.trainees = new HashMap<>();
        this.trainings = new HashMap<>();
        this.trainingTypes = new HashMap<>();
        this.users = new HashMap<>();
    }

    public Map<Long, Trainer> getTrainers() {
        return trainers;
    }

    public Map<Long, Trainee> getTrainees() {
        return trainees;
    }

    public Map<Long, Training> getTrainings() {
        return trainings;
    }

    public Map<Long, TrainingType> getTrainingTypes() {
        return trainingTypes;
    }

    public Map<Long, User> getUsers() {
        return users;
    }

    @PostConstruct
    public void initializeData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(jsonFilePath);
            Data data = objectMapper.readValue(jsonFile, Data.class);

            data.getUsers().forEach(user -> users.put(user.getId(), user));
            data.getTrainers().forEach(trainer -> trainers.put(trainer.getId(), trainer));
            data.getTrainees().forEach(trainee -> trainees.put(trainee.getId(), trainee));
            data.getTrainings().forEach(training -> trainings.put(training.getId(), training));
            data.getTrainingTypes().forEach(trainingType -> trainingTypes.put(trainingType.getId(), trainingType));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}