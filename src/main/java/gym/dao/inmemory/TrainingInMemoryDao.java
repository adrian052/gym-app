package gym.dao.inmemory;

import gym.service.simple.ValidationUtil;
import gym.dao.TrainingDao;
import gym.entities.Training;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
@Profile("memory")
public class TrainingInMemoryDao extends InMemoryDao<Training> implements TrainingDao {
    @Override
    protected Map<Long, Training> getEntityMap() {
        return storage.getTrainings();
    }

    @Override
    protected boolean hasReferentialIntegrity(Training training) {
        return isEntityIdInMap(training.getTrainee(), storage.getTrainees())
                && isEntityIdInMap(training.getTrainer(), storage.getTrainers())
                && isEntityIdInMap(training.getTrainingType(), storage.getTrainingTypes());
    }

    @Override
    protected boolean validateNotNull(Training training) {
        return training.getTrainee()!=null && training.getTrainer()!=null && training.getTrainingName()!=null
                && training.getTrainingType()!=null && training.getTrainingDate()!=null;
    }


    @Override
    public List<Training> getTraineeTrainingsByUsername(String traineeUsername) {
        ValidationUtil.validateNotNull(Map.of("traineeUsername",traineeUsername));
        return getEntityMap().values().stream()
                .filter(training -> training.getTrainee() != null && training.getTrainee().getUser().getUsername().equals(traineeUsername)).toList();
    }

    @Override
    public List<Training> getTrainerTrainingsByUsername(String trainerUsername) {
        ValidationUtil.validateNotNull(Map.of("trainerUsername",trainerUsername));
        return getEntityMap().values().stream()
                .filter(training -> training.getTrainer() != null && training.getTrainer().getUser().getUsername().equals(trainerUsername)).toList();
    }
}