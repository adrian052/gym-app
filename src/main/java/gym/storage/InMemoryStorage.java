package gym.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import gym.entities.Trainee;
import gym.entities.Trainer;
import gym.entities.Training;
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
public class InMemoryStorage {
    @Value("${data.json.file.path}")
    private String jsonFilePath;
    private Map<Long, Trainer> trainers;
    private Map<Long, Trainee> trainees;
    private Map<Long, Training> trainings;

    public InMemoryStorage() {
        this.trainers = new HashMap<>();
        this.trainees = new HashMap<>();
        this.trainings = new HashMap<>();
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

    @PostConstruct
    public void initializeData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(jsonFilePath);

            Data data = objectMapper.readValue(jsonFile, Data.class);

            for (Trainer trainer : data.getTrainers()) {
                this.trainers.put(trainer.getId(), trainer);
            }
            for (Trainee trainee : data.getTrainees()) {
                this.trainees.put(trainee.getId(), trainee);
            }
            for (Training training : data.getTrainings()) {
                this.trainings.put(training.getId(), training);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}