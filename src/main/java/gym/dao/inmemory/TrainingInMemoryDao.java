package gym.dao.inmemory;

import gym.dao.DataAccessObject;
import gym.entities.Training;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
@Profile("memory")
public class TrainingInMemoryDao extends InMemoryDao<Training> implements DataAccessObject<Training> {
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
}