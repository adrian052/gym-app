package gym.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import gym.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component("storage")
@PropertySource("context.properties")
public class InMemoryGymStorage implements GymStorage {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryGymStorage.class);

    @Value("${data.json.file.path}")
    private String jsonFilePath;
    private final Map<Long, Trainer> trainers;
    private final Map<Long, Trainee> trainees;
    private final Map<Long, Training> trainings;
    private final Map<Long, TrainingType> trainingTypes;
    private final Map<Long, User> users;
    private long nextTrainerId;
    private long nextTraineeId;
    private long nextTrainingId;
    private long nextTrainingTypeId;
    private long nextUserId;

    public InMemoryGymStorage() {
        this.trainers = new HashMap<>();
        this.trainees = new HashMap<>();
        this.trainings = new HashMap<>();
        this.trainingTypes = new HashMap<>();
        this.users = new HashMap<>();
        this.nextTrainerId = 0L;
        this.nextTraineeId = 0L;
        this.nextTrainingId = 0L;
        this.nextTrainingTypeId = 0L;
        this.nextUserId = 0L;
    }

    @Override
    public void setJsonFilePath(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    @Override
    public String getJsonFilePath() {
        return this.jsonFilePath;
    }

    @Override
    public long getNextId(Class<?> entityType) {
        if (entityType.equals(Trainer.class)) {
            return nextTrainerId++;
        } else if (entityType.equals(Trainee.class)) {
            return nextTraineeId++;
        } else if (entityType.equals(Training.class)) {
            return nextTrainingId++;
        } else if (entityType.equals(TrainingType.class)) {
            return nextTrainingTypeId++;
        } else if (entityType.equals(User.class)) {
            return nextUserId++;
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + entityType.getName());
        }
    }

    @Override
    public Map<Long, Trainer> getTrainers() {
        return trainers;
    }

    @Override
    public Map<Long, Trainee> getTrainees() {
        return trainees;
    }

    @Override
    public Map<Long, Training> getTrainings() {
        return trainings;
    }

    @Override
    public Map<Long, TrainingType> getTrainingTypes() {
        return trainingTypes;
    }

    @Override
    public Map<Long, User> getUsers() {
        return users;
    }

    @Override
    @PostConstruct
    public void initializeData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(jsonFilePath);
            GymDataLoader gymDataLoader = objectMapper.readValue(jsonFile, GymDataLoader.class);

            gymDataLoader.getUsers().forEach(user -> users.put(user.getId(), user));

            logger.info("Initialized Users");

            gymDataLoader.getTrainers().forEach(trainer -> {
                trainer.setId(getNextId(Trainer.class));
                trainers.put(trainer.getId(), trainer);
            });

            logger.info("Initialized Trainers");

            gymDataLoader.getTrainees().forEach(trainee -> {
                trainee.setId(getNextId(Trainee.class));
                trainees.put(trainee.getId(), trainee);
            });

            logger.info("Initialized Trainees");

            gymDataLoader.getTrainings().forEach(training -> {
                training.setId(getNextId(TrainingType.class));
                trainings.put(training.getId(), training);
            });

            logger.info("Initialized Trainings");

            gymDataLoader.getTrainingTypes().forEach(trainingType -> {
                trainingType.setId(getNextId(TrainingType.class));
                trainingTypes.put(trainingType.getId(), trainingType);
            });

            logger.info("Initialized Training Types");

        } catch (Exception e) {
            logger.error("Failed to initialize data", e);
        }
    }
}
